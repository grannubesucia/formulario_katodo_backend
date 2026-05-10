package com.serpientem.FormularioK.service;

import org.springframework.stereotype.Service;

import com.serpientem.FormularioK.model.Pedido;
import com.serpientem.FormularioK.repository.PedidoRepository;

import java.util.List;

import lombok.NonNull;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final EmailService emailService;

    public PedidoService(PedidoRepository repository, EmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
    }

    public List<Pedido> listar() {
        return repository.findAll();
    }

    public Pedido actualizarEstado(@NonNull Long id, String nuevoEstado) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        pedido.setEstado(nuevoEstado);
        Pedido guardado = repository.save(pedido);
        emailService.enviarCambioEstado(guardado);
        return guardado;
    }

    public List<Pedido> buscarPorEmail(String email) {
        return repository.findByEmailClienteIgnoreCase(email);
    }

    public Pedido guardar(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser null");
        }
        // Generar código de 6 dígitos
        String codigo = String.format("%06d", (int) (Math.random() * 1000000));
        pedido.setCodigoSeguimiento(codigo);

        Pedido guardado = repository.save(pedido);
        emailService.enviarConfirmaciones(guardado);
        return guardado;
    }

    public List<Pedido> buscarPorEmailYCodigo(String email, String codigo) {
        return repository.findByEmailClienteIgnoreCaseAndCodigoSeguimientoIgnoreCase(email, codigo);
    }
}

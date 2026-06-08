package com.serpientem.FormularioK.service; // Paquete de servicios

import org.springframework.stereotype.Service; // Marca la clase como servicio de Spring

import com.serpientem.FormularioK.model.Pedido; // Entidad Pedido
import com.serpientem.FormularioK.repository.PedidoRepository; // Acceso a base de datos

import java.util.List; // Listas de pedidos

import lombok.NonNull; // Validación de null

@Service // Lógica de negocio
public class PedidoService {

    private final PedidoRepository repository; // Repositorio de pedidos
    private final EmailService emailService; // Servicio de emails

    public PedidoService(PedidoRepository repository, EmailService emailService) {
        this.repository = repository; // Inyección de dependencia
        this.emailService = emailService;
    }

    public List<Pedido> listar() {
        return repository.findAll(); // Devuelve todos los pedidos
    }

    public Pedido actualizarEstado(@NonNull Long id, String nuevoEstado) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado")); // Busca o lanza error

        pedido.setEstado(nuevoEstado); // Cambia el estado
        Pedido guardado = repository.save(pedido); // Guarda en BD

        emailService.enviarCambioEstado(guardado); // Notifica por email
        return guardado;
    }

    public List<Pedido> buscarPorEmail(String email) {
        return repository.findByEmailClienteIgnoreCase(email); // Busca por email
    }

    public Pedido guardar(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser null"); // Validación básica
        }

        // Genera código aleatorio de 6 dígitos
        String codigo = String.format("%06d", (int) (Math.random() * 1000000));
        pedido.setCodigoSeguimiento(codigo);

        Pedido guardado = repository.save(pedido); // Guarda pedido

        emailService.enviarConfirmaciones(guardado); // Envía email de confirmación
        return guardado;
    }

    public List<Pedido> buscarPorEmailYCodigo(String email, String codigo) {
        return repository.findByEmailClienteIgnoreCaseAndCodigoSeguimientoIgnoreCase(email, codigo); // Búsqueda combinada
        
    }
}
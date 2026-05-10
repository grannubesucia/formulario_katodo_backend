package com.serpientem.FormularioK.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.serpientem.FormularioK.model.Pedido;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByEmailClienteIgnoreCase(String emailCliente);
    List<Pedido> findByEmailClienteIgnoreCaseAndCodigoSeguimientoIgnoreCase(String emailCliente, String codigoSeguimiento);
}


package com.serpientem.FormularioK.repository; // Paquete del repositorio

import org.springframework.data.jpa.repository.JpaRepository; // Repositorio JPA base
import com.serpientem.FormularioK.model.Pedido; // Entidad Pedido

import java.util.List; // Lista de resultados

// Repositorio que gestiona la entidad Pedido
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Busca pedidos por email ignorando mayúsculas/minúsculas
    List<Pedido> findByEmailClienteIgnoreCase(String emailCliente);

    // Busca pedidos por email y código de seguimiento ignorando
    // mayúsculas/minúsculas
    List<Pedido> findByEmailClienteIgnoreCaseAndCodigoSeguimientoIgnoreCase(String emailCliente,
            String codigoSeguimiento);
}
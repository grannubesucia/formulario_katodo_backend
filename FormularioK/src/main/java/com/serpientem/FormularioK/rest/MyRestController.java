package com.serpientem.FormularioK.rest; // Paquete del controlador REST

import org.springframework.http.ResponseEntity; // Respuestas HTTP controladas
import org.springframework.web.bind.annotation.*; // Anotaciones REST
import com.serpientem.FormularioK.model.Pedido; // Entidad Pedido
import com.serpientem.FormularioK.service.PedidoService; // Lógica de negocio

import java.util.List; // Listas de pedidos

/*
Muy importante para iniciar sesión tanto para la ruta del móvil como en la del ordena
*/
@RestController // Controlador REST (API JSON)
@RequestMapping("/api/pedidos") // Ruta base de la API
@CrossOrigin(origins = "*") // Permite peticiones desde cualquier origen (CORS)

public class MyRestController {

    private final PedidoService service; // Servicio de pedidos

    public MyRestController(PedidoService service) {
        this.service = service; // Inyección del servicio
    }

    @PostMapping // Crear pedido
    public Pedido guardar(@RequestBody Pedido pedido) {
        return service.guardar(pedido);
    }

    @GetMapping // Listar pedidos
    public List<Pedido> listar() {
        return service.listar();
    }

    @PatchMapping("/{id}/estado") // Actualizar estado de un pedido
    public Pedido actualizarEstado(@PathVariable Long id, @RequestBody java.util.Map<String, String> body) {
        return service.actualizarEstado(id, body.get("estado"));
    }

    @PostMapping("/login") // Login simple del panel admin
    public org.springframework.http.ResponseEntity<?> login(@RequestBody java.util.Map<String, String> body) {
        String pass = body.get("password");

        if ("Katodo2026".equals(pass)) {
            return org.springframework.http.ResponseEntity.ok().body(java.util.Map.of("ok", true)); // Login correcto
        }

        return org.springframework.http.ResponseEntity.status(401).body(java.util.Map.of("ok", false)); // Login incorrecto
                                                                                                    
    }

    @GetMapping("/por-email/{email}") // Buscar por email
    public List<Pedido> porEmail(@PathVariable String email) {
        return service.buscarPorEmail(email);
    }

    @GetMapping("/por-email/{email}/{codigo}") // Buscar por email + código
    public ResponseEntity<?> porEmail(@PathVariable String email, @PathVariable String codigo) {
        List<Pedido> pedidos = service.buscarPorEmailYCodigo(email, codigo);

        if (pedidos.isEmpty()) {
            return ResponseEntity.status(404).body("No encontrado"); // Sin resultados
        }

        return ResponseEntity.ok(pedidos); // Devuelve pedidos
    }

}
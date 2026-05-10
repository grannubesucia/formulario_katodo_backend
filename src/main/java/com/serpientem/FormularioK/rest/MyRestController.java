package com.serpientem.FormularioK.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.serpientem.FormularioK.model.Pedido;
import com.serpientem.FormularioK.service.PedidoService;

import java.util.List;

/*
Muy importante para iniciar sesión tanto para la ruta del móvil como en la del ordena
*/
@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")


public class MyRestController {

    private final PedidoService service;

    public MyRestController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    public Pedido guardar(@RequestBody Pedido pedido) {
        return service.guardar(pedido);
    }

    @GetMapping
    public List<Pedido> listar() {
        return service.listar();
    }

    @PatchMapping("/{id}/estado")
    public Pedido actualizarEstado(@PathVariable Long id, @RequestBody java.util.Map<String, String> body) {
        return service.actualizarEstado(id, body.get("estado"));
    }

    @PostMapping("/login")
    public org.springframework.http.ResponseEntity<?> login(@RequestBody java.util.Map<String, String> body) {
        String pass = body.get("password");
        if ("Katodo2026".equals(pass)) {
            return org.springframework.http.ResponseEntity.ok().body(java.util.Map.of("ok", true));
        }
        return org.springframework.http.ResponseEntity.status(401).body(java.util.Map.of("ok", false));
    }

    @GetMapping("/por-email/{email}")
    public List<Pedido> porEmail(@PathVariable String email) {
        return service.buscarPorEmail(email);
    }

    @GetMapping("/por-email/{email}/{codigo}")
    public ResponseEntity<?> porEmail(@PathVariable String email, @PathVariable String codigo) {
        List<Pedido> pedidos = service.buscarPorEmailYCodigo(email, codigo);
        if (pedidos.isEmpty()) {
            return ResponseEntity.status(404).body("No encontrado");
        }
        return ResponseEntity.ok(pedidos);
    }

}
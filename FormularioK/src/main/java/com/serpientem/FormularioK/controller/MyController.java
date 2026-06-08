package com.serpientem.FormularioK.controller; // Paquete del controlador

import org.springframework.stereotype.Controller; // Marca la clase como controlador MVC
import org.springframework.web.bind.annotation.GetMapping; // Mapea peticiones GET

@Controller // Indica que es un controlador de Spring MVC
public class MyController {

    @GetMapping("/") // Ruta raíz de la aplicación
    public String index() {

        return "index"; // Devuelve la vista "index"
    }

}
package com.serpientem.FormularioK; // Paquete principal de la aplicación

import org.springframework.boot.SpringApplication; // Arranque de Spring Boot
import org.springframework.boot.autoconfigure.SpringBootApplication; // Configuración automática
import org.springframework.scheduling.annotation.EnableAsync; // Habilita ejecución asíncrona

@SpringBootApplication // Marca la clase como aplicación Spring Boot
@EnableAsync // Permite ejecutar tareas en segundo plano (async)
public class FormularioKApplication {

    public static void main(String[] args) {
        SpringApplication.run(FormularioKApplication.class, args); // Inicia la aplicación
    }
}
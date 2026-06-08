package com.serpientem.FormularioK; // Paquete de configuración

import org.springframework.context.annotation.Bean; // Define beans de Spring
import org.springframework.context.annotation.Configuration; // Clase de configuración
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // Configuración de seguridad HTTP
import org.springframework.security.web.SecurityFilterChain; // Filtro de seguridad principal

@Configuration // Indica que es una clase de configuración
public class SecurityConfig {

    @Bean // Registra el SecurityFilterChain como bean de Spring
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // Desactiva CSRF (útil en APIs REST)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()); // Permite acceso a todas las rutas sin autenticación

        return http.build(); // Construye la configuración de seguridad
    }
}
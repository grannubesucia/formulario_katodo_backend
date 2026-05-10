package com.serpientem.FormularioK;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FormularioKApplication {
    public static void main(String[] args) {
        SpringApplication.run(FormularioKApplication.class, args);
    }
}
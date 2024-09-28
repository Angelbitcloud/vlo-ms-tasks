package com.co.velaio.vlo_ms_tasks.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

	@Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")  // Aplica a todas las rutas
                        .allowedOrigins("*")  // Permitir cualquier origen
                        .allowedMethods("*")  // Permitir cualquier método
                        .allowedHeaders("*")   // Permitir cualquier encabezado
                        .allowCredentials(false); // Deshabilitar credenciales
            }
        };
    }
}
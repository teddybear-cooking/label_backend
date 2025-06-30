package com.example.labeling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class LabelingApplication {

    public static void main(String[] args) {
        SpringApplication.run(LabelingApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                String frontendUrls = System.getenv("FRONTEND_URL");
                if (frontendUrls == null || frontendUrls.isEmpty()) {
                    frontendUrls = "http://localhost:3000";
                }
                
                // Support multiple domains separated by commas
                String[] allowedOrigins = frontendUrls.split(",");
                
                registry.addMapping("/**")
                    .allowedOrigins(allowedOrigins)
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(false)
                    .maxAge(3600);
            }
        };
    }
} 
package com.example.Terriffic;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        // list of allowed sites from .env, seperated by commas
        Dotenv dotenv = DotenvSingleton.getInstance();
        String allowedSites = dotenv.get("CORS_ALLOWED_ORIGINS");
        if (allowedSites == null) {
            return new WebMvcConfigurer(){};
        }
        String[] allowedSitesArray = allowedSites.split(",");
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(allowedSitesArray)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }
}
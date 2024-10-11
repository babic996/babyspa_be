package com.baby.babyspa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // Omogućava CORS za sve URL-ove
				.allowedOrigins("http://localhost:5173") // Dodaj ovde svoj frontend URL
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Dozvoli metode
				.allowedHeaders("*") // Dozvoli sve zaglavlja
				.allowCredentials(true); // Ako trebaš podršku za kolačiće
	}

}

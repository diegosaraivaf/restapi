package com.projeto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Startup  {
	
	public static void main(String[] args) {
		SpringApplication.run(Startup.class, args);
	}
	
	
	/*
	 * @Override public void addCorsMappings(CorsRegistry registry) {
	 * registry.addMapping("/**").allowedMethods("GET","POST","PUT","DELETE",
	 * "OPTIONS"); }
	 */

}

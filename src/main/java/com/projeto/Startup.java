package com.projeto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
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

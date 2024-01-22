package com.projeto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;

import com.projeto.entity.Nfse;
import com.projeto.repository.NfseRepository;

@SpringBootApplication
@EnableScheduling
public class Startup  {
	
	public static void main(String[] args) {
		SpringApplication.run(Startup.class, args);
	}
	
//	@Autowired
//	private NfseRepository nfseRepository;
//	
//	@EventListener(ApplicationReadyEvent.class)
//	public void doSomethingAfterStartup() {
//	    List<Nfse> result =  nfseRepository.findTeste();
//	    System.out.println("teste");
//	    result.get(0).getPrestador();
//	}
}

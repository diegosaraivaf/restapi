package com.projeto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.service.EmailService;


@RestController
@RequestMapping("/email")
public class EmailController {
	@Autowired
	private EmailService emailService;
	
	@PostMapping("/enviar")
	public void salvar() {
		emailService.enviar(
				"diegosaraivaferreira@gmail.com",
				"mensagem de teste",
				"essa e uma mensagem adicionada para fazer o teste do envio de email");
	}
	
	

}

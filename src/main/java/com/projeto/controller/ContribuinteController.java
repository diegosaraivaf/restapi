package com.projeto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.entity.Contribuinte;
import com.projeto.exeption.NegocioExeption;
import com.projeto.service.ContribuinteService;

@RestController
@RequestMapping("/contribuintes")
public class ContribuinteController {
	
	@Autowired
	private ContribuinteService contribuinteService;
	
	@PostMapping("/")
	public ResponseEntity<?> salvar(@RequestBody Contribuinte constribuinte) {
		try {
			Contribuinte contribuinte = contribuinteService.salvar(constribuinte);
			return ResponseEntity.status(HttpStatus.OK).body(contribuinte);
		}
		catch (NegocioExeption e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Aconteceu um erro inesperado");
		}
	}

}

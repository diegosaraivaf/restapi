package com.projeto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.entity.Nfse;
import com.projeto.exeption.NegocioException;
import com.projeto.service.NfseService;

@RestController
@RequestMapping("/nfses")
public class NfseController {
	
	@Autowired
	NfseService nfseService;
	
	@PostMapping
	public Nfse salva(@RequestBody Nfse nfse) throws NegocioException{
		return nfseService.salvar(nfse);
	}
	
	public void deletar() {
		
	}
	
	public void alterar() {
		
	}
	
	@GetMapping
	public List<Nfse> listarTodos() {
		return nfseService.listarTodos();
	}
	
	@GetMapping("/{id}")
	public Nfse listarPorId(@PathVariable("id") Long id) throws NegocioException {
		Nfse nfse = nfseService.buscarPorId(id);
		
		if(nfse == null) {
			throw new NegocioException("Nao existe nota com o id :" +id);
		}
		return nfse;
	}
}

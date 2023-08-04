package com.projeto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.entity.ItemNfse;
import com.projeto.entity.Nfse;
import com.projeto.exeption.NegocioException;
import com.projeto.service.ItemNfseService;
import com.projeto.service.NfseService;

//@RestController
//@RequestMapping("/")
public class ItemNfseController {
	
	@Autowired
	ItemNfseService itemNfseService;
	
	@PostMapping("/nfses/{id}/item")
	public ItemNfse salva(@RequestBody ItemNfse item) throws NegocioException{
		return itemNfseService.salvar(item);
	}
	
	public void deletar() {
		
	}
	
	public void alterar() {
		
	}
	
	@GetMapping
	public List<Nfse> listarTodos() {
		return null;
	}
	
	public void listarPorId(Long id) {
		
	}
}

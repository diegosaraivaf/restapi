package com.projeto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.entity.Endereco;
import com.projeto.service.EnderecoService;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {
	
	@Autowired
	private EnderecoService enderecoService;
	
	@PostMapping
	public Endereco salvar(@RequestBody Endereco endereco) {
		return enderecoService.save(endereco);
	}
	
	@PutMapping("/{id}")
	public Endereco atualizar(@PathVariable Long id, @RequestBody Endereco endereco) {
		return enderecoService.atualizar(endereco);
	}
	
	@DeleteMapping("/{id}")
	public void deletetar(@PathVariable Long id) {
		enderecoService.deletarPorId(id);
	}
	
	@GetMapping("/{id}")
	public Endereco buscarPorId(@PathVariable Long id) {
		return enderecoService.buscarPorId(id);
	}
	
	@GetMapping
	public List<Endereco> buscarTudo() {
		return enderecoService.buscarTudo();
	}

}

package com.projeto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.entity.Endereco;
import com.projeto.repository.EnderecoRepository;

@Service
public class EnderecoService {
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Endereco save(Endereco endereco) {
		return enderecoRepository.save(endereco);
	}
	
	public List<Endereco> saveAll(List<Endereco> endereco) {
		return enderecoRepository.saveAll(endereco);
	}
	
	public Endereco atualizar(Endereco endereco) {
		return enderecoRepository.save(endereco);
	}
	
	public Endereco buscarPorId(Long id) {
		return enderecoRepository.findById(id).get();
	}
	
	public List<Endereco> buscarTudo(){
		return enderecoRepository.findAll();
	}
	
	public void deletarPorId(Long id) {
		enderecoRepository.deleteById(id);
	}
}

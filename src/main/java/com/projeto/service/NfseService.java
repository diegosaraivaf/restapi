package com.projeto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.entity.Nfse;
import com.projeto.repository.NfseRepository;

@Service
public class NfseService {
	@Autowired
	private NfseRepository nfseRepository;
	
	public Nfse salvar(Nfse nfse) {
		return nfseRepository.save(nfse);
	}
	
	public void atualizar() {
		
	}
	
	public void detelar() {
		
	}
	
	public void listarPorId() {
		
	}
	
	public List<Nfse> listarTodos() {
		return nfseRepository.findAll();
	}
}

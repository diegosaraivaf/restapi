package com.projeto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.entity.ItemNfse;
import com.projeto.entity.Nfse;
import com.projeto.repository.ItemNfseRepository;
import com.projeto.repository.NfseRepository;

@Service
public class ItemNfseService {
	@Autowired
	private ItemNfseRepository itemNfseRepository;
	
	public ItemNfse salvar(ItemNfse item) {
		return itemNfseRepository.save(item);
	}
	
	public void atualizar() {
		
	}
	
	public void detelar() {
		
	}
	
	public void listarPorId() {
		
	}
	
	public List<Nfse> listarTodos() {
		return null;
	}
}

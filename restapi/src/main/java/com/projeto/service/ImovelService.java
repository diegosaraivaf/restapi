package com.projeto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.entity.Imovel;
import com.projeto.repository.ImovelRepository;

@Service
public class ImovelService {
	
	@Autowired
	private ImovelRepository imovelRepository;
	
	public Imovel salvar(Imovel imovel) {
		return imovelRepository.save(imovel);
	}
	
	public List<Imovel> filtrar() {
		return imovelRepository.findAll();
	}
	
}

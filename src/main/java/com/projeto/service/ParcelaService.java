package com.projeto.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.projeto.entity.Lancamento;
import com.projeto.entity.Parcela;
import com.projeto.entity.Pessoa;
import com.projeto.repository.ParcelaRepository;

@Service
public class ParcelaService {
	
	@Autowired
	private ParcelaRepository parcelaRepository;
	
	@Transactional(rollbackOn = Throwable.class)
	public List<Parcela> salvar(List<Parcela> parcelas) {
		return parcelaRepository.saveAll(parcelas);
	}
	
	public void deletarParcelarDoLancamento(Lancamento lancamento){
		parcelaRepository.deletarParcelasDoLancamento(lancamento.getId());
	}
	
}

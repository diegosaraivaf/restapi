package com.projeto.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.entity.Lancamento;
import com.projeto.repository.LancamentoRepository;

@Service
public class LancamentoService {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	public List<Lancamento> findAll() {
		return lancamentoRepository.findAll();
	}
	
	public Lancamento salvar(Lancamento lancamento) {
		return lancamentoRepository.save(lancamento);
	}
	
	public Optional<Lancamento> buscarPorId(Long id) {
		return lancamentoRepository.findById(id);
	}
	
	public void deletar(Lancamento lancamento) {
		lancamentoRepository.delete(lancamento);
	}
	
	public List<Lancamento> filtrarLancamentos(Long id,String tipo,BigDecimal valor, Date dataEmissao){
		return lancamentoRepository.filtrarLancamentos(id,tipo,valor,dataEmissao);
	}
}

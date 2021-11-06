package com.projeto.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.entity.Lancamento;
import com.projeto.entity.Parcela;
import com.projeto.entity.TipoLancamento;
import com.projeto.repository.LancamentoRepository;

@Service
public class LancamentoService {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private ParcelaService parcelaService;
	
	public List<Lancamento> findAll() {
		return lancamentoRepository.findAll();
	}
	
	@Transactional
	public Lancamento salvar(Lancamento lancamento) {
		parcelaService.deletarParcelarDoLancamento(lancamento);
		List<Parcela> parcelas =  parcelaService.salvar(lancamento.getParcelas());
		lancamento.setParcelas(parcelas);
		
		return lancamentoRepository.save(lancamento);
	}
	
	public Optional<Lancamento> buscarPorId(Long id) {
		return lancamentoRepository.findById(id);
	}
	
	public void deletar(Lancamento lancamento) {
		lancamentoRepository.delete(lancamento);
	}
	
	public List<Lancamento> filtrarLancamentos(Long id,TipoLancamento tipoLancamento,BigDecimal valor, Date dataEmissao,
			String contribuinteNome, String contribuinteDocumento){
		return lancamentoRepository.filtrarLancamentos(id,tipoLancamento,valor, dataEmissao, contribuinteNome, contribuinteDocumento);
	}
}

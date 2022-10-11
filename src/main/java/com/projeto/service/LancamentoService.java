package com.projeto.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.projeto.entity.Lancamento;
import com.projeto.entity.Parcela;
import com.projeto.entity.TipoLancamento;
import com.projeto.exeption.NegocioException;
import com.projeto.repository.LancamentoRepository;

@Service
public class LancamentoService {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private ParcelaService parcelaService;
	
	/**find all com paginabel,classes do spring boot que ajudam com a paginacao,esta classe nao foi utilizada no filtro
	 * de lancamento porque usando find all eu perco os fetch join */
	public List<Lancamento> findAll(Pageable pageable) {
		return lancamentoRepository.findAll(pageable).getContent();
	}
	
	@Transactional
	public Lancamento salvar(Lancamento lancamento) {
		//adicionar validacoes quantidade maxima de parcela,valor minimo parcela.
		
		//pensar em realizar este processo altomaticamento com update cascade
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
			String contribuinteNome, String contribuinteDocumento, int pagina, int limite){
		return lancamentoRepository.filtrarLancamentos(id,tipoLancamento,valor, dataEmissao, contribuinteNome, contribuinteDocumento,
				pagina,limite);
	}
	
	@Transactional(rollbackOn = Throwable.class)
	public void testeTransaction() throws NegocioException {
		Lancamento l1 = new Lancamento();
		l1.setValor(new BigDecimal("1000"));
		lancamentoRepository.save(l1);
		
		if(0==0) {
			throw new NegocioException("Erro na transacao");
		}
		
		Lancamento l2 = new Lancamento();
		l2.setValor(new BigDecimal("1000"));
		lancamentoRepository.save(l2);
	}


}

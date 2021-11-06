package com.projeto.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.entity.Lancamento;
import com.projeto.entity.TipoLancamento;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{
	List<Lancamento> filtrarLancamentos(Long id,TipoLancamento tipoLancamento,BigDecimal valor, Date dataEmissao,
			String contribuinteNome, String contribuinteDocumento);
}

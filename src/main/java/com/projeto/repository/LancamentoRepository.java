package com.projeto.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.projeto.entity.Lancamento;
import com.projeto.entity.TipoLancamento;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{
	List<Lancamento> filtrarLancamentos(Long id,TipoLancamento tipoLancamento,BigDecimal valor, Date dataEmissao,
			String contribuinteNome, String contribuinteDocumento, int pagina, int limite);
	
	//Opcao de implementacao para parametros nao obrigatorios sem necessidade de implementar uma interface (nao testado)
	@Query(
		"SELECT l FROM Lancamento l "
		+ "WHERE (:tipo is null or l.tipoLancamento = :tipo )"
		+ "and (:contribuinteNome is null or l.contribuinte.nome = :contribuinteNome )"
	)
	List<Lancamento> filtrarLancamentos(TipoLancamento tipo, String contribuinteNome);
}

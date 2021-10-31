package com.projeto.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.projeto.entity.Lancamento;
import com.projeto.entity.TipoLancamento;

@Repository
public class LancamentoRepositoryImpl {
	
	@PersistenceContext
	private EntityManager manager;
	
	public List<Lancamento> filtrarLancamentos(Long id,TipoLancamento tipoLancamento,BigDecimal valor, Date dataEmissao) {
		StringBuilder jpql = new StringBuilder();
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		jpql.append("select distinct l from Lancamento l "
				+ "join fetch l.contribuinte c "
				+ "left join fetch l.parcelas p "
				+ "where 0=0 ");
		
		if(id != null) {
			jpql.append("and l.id = :id ");
			parametros.put("id", id);
		}
		if(tipoLancamento != null ) {
			jpql.append("and l.tipoLancamento = :tipoLancamento ");
			parametros.put("tipoLancamento", tipoLancamento);
		}
		
		jpql.append("order by l.id ");
		
		TypedQuery<Lancamento> query =  manager.createQuery(jpql.toString(),Lancamento.class);
		parametros.forEach((key,value) -> query.setParameter(key, value));
		
		return query.getResultList();
	}

}

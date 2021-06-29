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

@Repository
public class LancamentoRepositoryImpl {
	
	private String teste;
	
	@PersistenceContext
	private EntityManager manager;
	
	public List<Lancamento> filtrarLancamentos(Long id,String tipo,BigDecimal valor, Date dataEmissao) {
		StringBuilder jpql = new StringBuilder();
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		jpql.append("select l from Lancamento l where 0=0 ");
		
		if(id != null) {
			jpql.append("and l.id = :id ");
			parametros.put("id", id);
		}
		if(tipo != null && !tipo.isEmpty()) {
			jpql.append("and l.tipo = :tipo ");
			parametros.put("tipo", tipo);
		}
		
		TypedQuery<Lancamento> query =  manager.createQuery(jpql.toString(),Lancamento.class);
		parametros.forEach((key,value) -> query.setParameter(key, value));
		
		return query.getResultList();
	}

}

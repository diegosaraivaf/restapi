package com.projeto.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.projeto.entity.Contribuinte;
import com.projeto.entity.Lancamento;


@Repository
public class ContribuinteRepositoryImpl {
	
	@PersistenceContext
	private EntityManager manager;
	
	public List<Contribuinte> filtrar(String documento,String nome,String endereco) {
		StringBuilder jpql = new StringBuilder();
		Map<String,Object> parametros = new HashMap<String, Object>();
		
		jpql.append("select c from Contribuinte c where 1=1 ");
		
		if(documento != null ) {
			jpql.append("and c.documento like :documento ");
			parametros.put("documento", "%"+documento+"%");
		}
		
		if(nome != null ) {
			jpql.append("and c.nome like :nome ");
			parametros.put("nome", "%"+nome+"%");
		}
		
		if(endereco != null ) {
			jpql.append("and c.endereco like :endereco ");
			parametros.put("endereco", "%"+endereco+"%");
		}
		
		TypedQuery<Contribuinte> query =  manager.createQuery(jpql.toString(),Contribuinte.class);
		parametros.forEach((key,value) -> query.setParameter(key, value));
		
		return query.getResultList();
	}
}

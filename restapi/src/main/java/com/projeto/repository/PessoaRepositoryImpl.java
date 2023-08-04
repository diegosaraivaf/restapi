package com.projeto.repository;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.projeto.entity.Pessoa;

@Repository
public class PessoaRepositoryImpl {
	
	@PersistenceContext
	private EntityManager manager;
	
	//como o vinculo do nome do metodo e do nome da interface do spring jpa e fraco,pode ser criado uma interface compartilhada entra os dois
	public List<Pessoa> filtrarPessoas(Long id, String nome){
		StringBuilder jpql = new StringBuilder();
		jpql.append("Select p from Pessoa p where 0=0 ");
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		if(StringUtils.hasLength(nome)) {
			jpql.append("and p.nome = :nome ");
			parametros.put("nome", nome);
		}
		if(id != null) {
			jpql.append("and p.id = :id ");
			parametros.put("id", id);
		}
		jpql.append("order by p.id desc");
		
		
		TypedQuery<Pessoa> query =  manager.createQuery(jpql.toString(),Pessoa.class);
		parametros.forEach((chave,valor) -> query.setParameter(chave, valor));
	
		return query.getResultList();
	}

}

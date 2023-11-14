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
public class ContribuinteRepositoryImpl extends RepositoryGenericoImpl<Contribuinte,Long>{
	
	@PersistenceContext
	private EntityManager manager;
	
	public List<Contribuinte> filtrar(Long id,String documento,String nome,String rua,String bairro) {
		StringBuilder jpql = new StringBuilder();
		Map<String,Object> parametros = new HashMap<String, Object>();
		
		jpql.append("select distinct c from Contribuinte c left join Endereco e on e.contribuinte = c  where 1=1 ");
		
		if(id != null ) {
			jpql.append("and c.id = :id ");
			parametros.put("id", id);
		}
		
		if(documento != null ) {
			jpql.append("and c.documento like :documento ");
			parametros.put("documento", "%"+documento+"%");
		}
		
		if(nome != null ) {
			jpql.append("and UPPER(c.nome) like :nome ");
			parametros.put("nome", "%"+nome.toUpperCase()+"%");
		}
		
		if(rua != null ) {
			jpql.append("and e.rua like :rua ");
			parametros.put("rua", "%"+rua+"%");
		}
		
		if(bairro != null ) {
			jpql.append("and e.bairro like :bairro ");
			parametros.put("rua", "%"+bairro+"%");
		}
		jpql.append("order by c.id desc ");
		
		TypedQuery<Contribuinte> query =  manager.createQuery(jpql.toString(),Contribuinte.class);
		parametros.forEach((key,value) -> query.setParameter(key, value));
		
		return query.getResultList();
	}
	
	public Contribuinte salvar(Contribuinte contribuinte) {
		return manager.merge(contribuinte);
	}
}

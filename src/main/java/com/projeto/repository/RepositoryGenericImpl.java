package com.projeto.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class RepositoryGenericImpl {
	
	@PersistenceContext
	private EntityManager manager;
	
	public <T> T find(Class<T> classe,Object id){
		return manager.find(classe, id);
	}

}

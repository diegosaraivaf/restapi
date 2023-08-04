package com.projeto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.projeto.entity.Pessoa;

@Repository
public interface RepositoryGeneric extends JpaRepository<Pessoa, Long>{
	
	<T> T find(Class<T> classe,Object id);
	
}

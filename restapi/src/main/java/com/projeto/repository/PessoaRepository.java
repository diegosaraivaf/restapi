package com.projeto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.projeto.entity.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
	
	List<Pessoa> findByNomeContaining(String nome);
	
	@Query("SELECT p FROM Pessoa p WHERE p.nome = :nome AND p.id = :id")
	List<Pessoa> porNome(String nome,Long id);
	
	List<Pessoa> filtrarPessoas(Long id, String nome);

	
}

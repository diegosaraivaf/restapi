package com.projeto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.projeto.entity.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long>{

//	List<Endereco> findByContribuente(Contribuinte contribuinte);
	
	@Query(value = "SELECT e.* FROM endereco e WHERE e.contribuinte_id = :contribuinteId",nativeQuery = true)
	List<Endereco> porContribuinte(Long contribuinteId);
	
	@Modifying
	@Query(value = "delete FROM endereco e WHERE e.contribuinte_id = :contribuinteId",nativeQuery = true)
	void deletarPorContribuinte(Long contribuinteId);
}

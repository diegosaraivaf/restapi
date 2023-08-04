package com.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.projeto.entity.Parcela;

@Repository
public interface ParcelaRepository extends JpaRepository<Parcela, Long>{
	
	@Modifying
	@Query(value="delete from parcela where lancamento_id = :idLancamento",nativeQuery = true)
	void deletarParcelasDoLancamento(Long idLancamento);
}

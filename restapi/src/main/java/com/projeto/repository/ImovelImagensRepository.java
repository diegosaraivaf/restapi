package com.projeto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.projeto.entity.Endereco;
import com.projeto.entity.ImovelImagens;

@Repository
public interface ImovelImagensRepository extends JpaRepository<ImovelImagens, Long>{

//	List<Endereco> findByContribuente(Contribuinte contribuinte);
	
	@Query(value = "SELECT i.* FROM imovel_imagens i WHERE i.imovel_id = :imovelId",nativeQuery = true)
	List<ImovelImagens> porImovel(Long imovelId);
	
}

package com.projeto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.entity.Contribuinte;

@Repository
public interface ContribuinteRepository extends JpaRepository<Contribuinte, Long>{
	Contribuinte findByDocumento(String documento);
	List<Contribuinte> filtrar(String documento,String nome,String endereco);
	Contribuinte salvar(Contribuinte contribuinte);
	Contribuinte porId(Long id);
}

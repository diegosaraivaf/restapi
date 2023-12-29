package com.projeto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.entity.Contribuinte;

@Repository
public interface ContribuinteRepository extends JpaRepository<Contribuinte, Long>{
	Contribuinte findByDocumento(String documento);
	List<Contribuinte> filtrar(Long id,String documento,String nome,String rua,String bairro);
	Contribuinte salvar(Contribuinte contribuinte);
}

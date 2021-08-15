package com.projeto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.entity.Contribuinte;
import com.projeto.entity.Pessoa;

@Repository
public interface ContribuinteRepository extends JpaRepository<Contribuinte, Long>{
	Contribuinte findByDocumento(String documento);
}

package com.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.entity.Caracteristica;
import com.projeto.entity.OpcaoCaracteristica;


@Repository
public interface OpcaoCaracteristicaRepository extends JpaRepository<OpcaoCaracteristica, Long>{
}

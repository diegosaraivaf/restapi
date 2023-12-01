package com.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.entity.Caracteristica;


@Repository
public interface CaracteristicaRepository extends JpaRepository<Caracteristica, Long>{
}

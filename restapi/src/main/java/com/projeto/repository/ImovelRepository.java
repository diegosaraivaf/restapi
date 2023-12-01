package com.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.projeto.entity.Imovel;

@Repository
public interface ImovelRepository extends JpaRepository<Imovel, Long>{

}

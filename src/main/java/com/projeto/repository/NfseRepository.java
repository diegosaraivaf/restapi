package com.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.entity.Nfse;

@Repository
public interface NfseRepository extends JpaRepository<Nfse, Long>{

}

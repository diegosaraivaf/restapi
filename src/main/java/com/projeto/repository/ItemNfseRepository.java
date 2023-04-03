package com.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.entity.ItemNfse;
import com.projeto.entity.Nfse;

@Repository
public interface ItemNfseRepository extends JpaRepository<ItemNfse, Long>{

}

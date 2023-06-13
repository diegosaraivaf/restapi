package com.projeto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.projeto.entity.Nfse;

@Repository
public interface NfseRepository extends JpaRepository<Nfse, Long>{
	
	@Query(
	    "SELECT distinct n FROM Nfse n "
	  + "left join fetch n.prestador p "
	  + "left join fetch n.tomador p "
	  + "left join fetch n.itensNfse i "
	)
	public List<Nfse> findAll() ;

}

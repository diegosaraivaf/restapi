package com.projeto.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.projeto.entity.Nfse;
import com.projeto.entity.SituacaoNfse;

@Repository
public interface NfseRepository extends JpaRepository<Nfse, Long>{
	
	@Query(
	    "SELECT distinct n FROM Nfse n "+
	    "left join fetch n.prestador p "+
	    "left join fetch n.tomador t "+
	    "left join fetch n.itensNfse i "
	)
	public List<Nfse> findAll() ;
	
	//nao pode adicionar relacionamento OneToMane
	@Query(
	    value = "SELECT  n FROM Nfse n "+
	    "left join fetch n.prestador p "+
	    "left join fetch n.tomador t "+
	    "WHERE (:localPrestacao IS NULL OR n.localPrestacao LIKE %:localPrestacao%) " +
	    "AND (:valorServico IS NULL OR n.valorServico = :valorServico) "+
	    "AND (:situacaoNfse IS NULL OR n.situacaoNfse = :situacaoNfse ) "+
	    "AND (:documentoPrestador IS NULL OR p.documento = :documentoPrestador ) "+
	    "AND (:nomePrestador IS NULL OR p.nome like %:nomePrestador% ) ",
	    
	    countQuery = "SELECT  count(n.id) FROM Nfse n "+
	    "left join n.prestador p "+
	    "left join n.tomador t "+
	    "WHERE (:localPrestacao IS NULL OR n.localPrestacao LIKE %:localPrestacao%) " +
	    "AND (:valorServico IS NULL OR n.valorServico = :valorServico) "+
	    "AND (:situacaoNfse IS NULL OR n.situacaoNfse = :situacaoNfse ) "+
	    "AND (:documentoPrestador IS NULL OR p.documento = :documentoPrestador ) "+
	    "AND (:nomePrestador IS NULL OR p.nome like %:nomePrestador% ) "
	)
	Page<Nfse> findByFilters(
			String documentoPrestador, 
			String nomePrestador,
			String localPrestacao, 
			BigDecimal valorServico,
			SituacaoNfse situacaoNfse, 
			Pageable pageable
	);
	
	
	//solucao com fetch join no select, mas neste caso ele execulta a consulta sem limit, e faz o limit em memoria. 
//	@Query(
//	    value = "SELECT distinct n FROM Nfse n "+
//	    "left join fetch n.prestador p "+
//	    "left join fetch n.tomador t "+
//	    "left join fetch n.itensNfse i "+
//	    "WHERE (:localPrestacao IS NULL OR n.localPrestacao LIKE %:localPrestacao%) " +
//	    "AND (:valorServico IS NULL OR n.valorServico = :valorServico) " ,
//	    
//	    countQuery = "SELECT count(n.id) FROM Nfse n "+
//	    "left join n.prestador p "+
//	    "left join n.tomador t "+
//	    "left join n.itensNfse i "+
//	    "WHERE (:localPrestacao IS NULL OR n.localPrestacao LIKE %:localPrestacao%) " +
//	    "AND (:valorServico IS NULL OR n.valorServico = :valorServico) "
//	)
//	Page<Nfse> findByFilters(String localPrestacao, BigDecimal valorServico, Pageable pageable);
	
	
	@Modifying
    @Query("UPDATE Nfse n SET n.situacaoNfse = 'CANCELADA' WHERE n = :nfse")
	public void deletarLogicamente(Nfse nfse);
	
}

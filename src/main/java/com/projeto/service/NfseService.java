package com.projeto.service;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.projeto.entity.Nfse;
import com.projeto.repository.NfseRepository;

@Service
public class NfseService {
	@Autowired
	private NfseRepository nfseRepository;
	
	@Autowired
	private EntityManager manager;
	
	@Transactional(rollbackOn = Throwable.class)
	public Nfse save(Nfse nfse) {
		return manager.merge(nfse);
	}
	
	public Nfse atualizar(Nfse nfse) {
		return nfseRepository.save(nfse);
	}
	
	public void detelar() {
		
	}
	
	public Nfse findById(Long id) {
		return nfseRepository.findById(id).get();
	}
	
	public List<Nfse> listarTodos() {
		List<Nfse> result = nfseRepository.findAll();
		
		return result;
	}
	
	public Page<Nfse> findByFilter(String localPrestacao,BigDecimal valor,Pageable pageable) {    
		Page<Nfse> result = nfseRepository.findByFilters(null,null, pageable);
		return result;
	}
}

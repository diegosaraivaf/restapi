package com.projeto.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.projeto.entity.Contribuinte;
import com.projeto.exeption.NegocioException;
import com.projeto.repository.ContribuinteRepository;

@Service
public class ContribuinteService {
	
	@Autowired
	private ContribuinteRepository contribuinteRepository;
	
	@Transactional
	public Contribuinte salvar(Contribuinte contribuinte) throws NegocioException {
		validarContribuinte(contribuinte);
		
		return contribuinteRepository.salvar(contribuinte);
	}
	
	public List<Contribuinte> filtrar(String documento,String nome,String endereco) {
		///teste
		Contribuinte c = contribuinteRepository.porId(1L);
		//fim teste
		
		return contribuinteRepository.filtrar(documento, nome, endereco);
	}
	
	public void deletar(Contribuinte contribuinte) throws NegocioException {
		try {
			contribuinteRepository.delete(contribuinte);
		}
		catch (DataIntegrityViolationException e) {
			throw new NegocioException("Este contribuinte esta sendo utilizado em outras partes do sistema e nao pode ser excluido.");
		}
	}
	
	public Optional<Contribuinte> porId(Long id) {
		return contribuinteRepository.findById(id);
	}
	
	public Contribuinte buscarPorDocumento(String documento) {
		return contribuinteRepository.findByDocumento(documento);
	}

	private void validarContribuinte(Contribuinte contribuinte) throws NegocioException {
		if(contribuinte.getId() == null) {
			Contribuinte contrib = contribuinteRepository.findByDocumento(contribuinte.getDocumento());
			if(contrib != null) {
				throw new NegocioException("Documento ja esta sento utilizado");
			}
		}
	}
}

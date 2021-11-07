package com.projeto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.projeto.entity.Contribuinte;
import com.projeto.exeption.NegocioExeption;
import com.projeto.repository.ContribuinteRepository;

@Service
public class ContribuinteService {
	
	@Autowired
	private ContribuinteRepository contribuinteRepository;

	public Contribuinte salvar(Contribuinte contribuinte) throws NegocioExeption {
		validarContribuinte(contribuinte);
		
		return contribuinteRepository.save(contribuinte);
	}
	
	public List<Contribuinte> pesquisar(){
		return contribuinteRepository.findAll();
	}
	
	public void deletar(Contribuinte contribuinte) throws NegocioExeption {
		try {
			contribuinteRepository.delete(contribuinte);
		}
		catch (DataIntegrityViolationException e) {
			throw new NegocioExeption("Este contribuinte esta sendo utilizado em outras partes do sistema e nao pode ser excluido.");
		}
	}
	
	public Optional<Contribuinte> porId(Long id) {
		return contribuinteRepository.findById(id);
	}
	
	public Contribuinte buscarPorDocumento(String documento) {
		return contribuinteRepository.findByDocumento(documento);
	}

	private void validarContribuinte(Contribuinte contribuinte) throws NegocioExeption {
		if(contribuinte.getId() == null) {
			Contribuinte contrib = contribuinteRepository.findByDocumento(contribuinte.getDocumento());
			if(contrib != null) {
				throw new NegocioExeption("Documento ja esta sento utilizado");
			}
		}
	}
}

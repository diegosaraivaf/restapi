package com.projeto.service;

import org.springframework.beans.factory.annotation.Autowired;
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

	private void validarContribuinte(Contribuinte contribuinte) throws NegocioExeption {
		if(contribuinte.getId() == null) {
			Contribuinte contrib = contribuinteRepository.findByDocumento(contribuinte.getDocumento());
			if(contrib != null) {
				throw new NegocioExeption("Documento ja esta sento utilizado");
			}
		}
	}
}

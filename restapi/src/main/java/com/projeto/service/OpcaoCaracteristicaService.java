package com.projeto.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.projeto.entity.OpcaoCaracteristica;
import com.projeto.exeption.NegocioException;
import com.projeto.repository.OpcaoCaracteristicaRepository;


@Service
public class OpcaoCaracteristicaService {
	
	@Autowired
	private OpcaoCaracteristicaRepository opcaoCaracteristicaRepository;
	
	@Transactional
	public OpcaoCaracteristica salvar(OpcaoCaracteristica opcaoCaracteristica) throws NegocioException {
		validarOpcaoCaracteristica(opcaoCaracteristica);
		
		return opcaoCaracteristicaRepository.save(opcaoCaracteristica);
	}
	
	public List<OpcaoCaracteristica> filtrar() {
		return opcaoCaracteristicaRepository.findAll();
	}
	
	public void deletar(OpcaoCaracteristica opcaoCaracteristica) throws NegocioException {
		try {
			opcaoCaracteristicaRepository.deleteById(opcaoCaracteristica.getId());
		}
		catch (DataIntegrityViolationException e) {
			throw new NegocioException("Este contribuinte esta sendo utilizado em outras partes do sistema e nao pode ser excluido.");
		}
	}
	
	public Optional<OpcaoCaracteristica> porId(Long id) {
		return opcaoCaracteristicaRepository.findById(id);
	}

	private void validarOpcaoCaracteristica(OpcaoCaracteristica OpcaoCaracteristica) throws NegocioException {
//		if(contribuinte.getId() == null) {
//			Contribuinte contrib = contribuinteRepository.findByDocumento(contribuinte.getDocumento());
//			if(contrib != null) {
//				throw new NegocioException("Documento ja esta sento utilizado");
//			}
//		}
	}
}

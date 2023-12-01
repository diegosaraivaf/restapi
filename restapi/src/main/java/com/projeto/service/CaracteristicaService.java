package com.projeto.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.projeto.entity.Caracteristica;
import com.projeto.exeption.NegocioException;
import com.projeto.repository.CaracteristicaRepository;


@Service
public class CaracteristicaService {
	
	@Autowired
	private CaracteristicaRepository caracteristicaRepository;
	
	@Transactional
	public Caracteristica salvar(Caracteristica caracteristica) throws NegocioException {
		validarCaracteristica(caracteristica);
		
		return caracteristicaRepository.save(caracteristica);
	}
	
	public List<Caracteristica> filtrar() {
		return caracteristicaRepository.findAll();
	}
	
	public void deletar(Caracteristica caracteristica) throws NegocioException {
		try {
			caracteristicaRepository.deleteById(caracteristica.getId());
		}
		catch (DataIntegrityViolationException e) {
			throw new NegocioException("Este contribuinte esta sendo utilizado em outras partes do sistema e nao pode ser excluido.");
		}
	}
	
	public Optional<Caracteristica> porId(Long id) {
		return caracteristicaRepository.findById(id);
	}

	private void validarCaracteristica(Caracteristica caracteristica) throws NegocioException {
//		if(contribuinte.getId() == null) {
//			Contribuinte contrib = contribuinteRepository.findByDocumento(contribuinte.getDocumento());
//			if(contrib != null) {
//				throw new NegocioException("Documento ja esta sento utilizado");
//			}
//		}
	}
}

package com.projeto.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.projeto.entity.Contribuinte;
import com.projeto.entity.ImovelImagens;
import com.projeto.exeption.NegocioException;
import com.projeto.repository.ContribuinteRepository;
import com.projeto.repository.ImovelImagensRepository;

@Service
public class ImovelImagensService {
	
	@Autowired
	private ImovelImagensRepository imovelImagensRepository;
	
	@Transactional
	public ImovelImagens salvar(ImovelImagens imovelImagens)  {
		return imovelImagensRepository.save(imovelImagens);
	}
	
	public void deletar(ImovelImagens imovelImagens) throws NegocioException {
		imovelImagensRepository.delete(imovelImagens);
	}
	
	public List<ImovelImagens> porImovel(Long id) {
		try {
			List<ImovelImagens> imagens =  imovelImagensRepository.porImovel(id);
			
			for (ImovelImagens i : imagens) {
				InputStream in = new FileInputStream("c:/img/"+i.getIdentificador());
				i.setArquivo(IOUtils.toByteArray(in) );
			}
			
			return imagens;
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<ImovelImagens> findAll() {
		List<ImovelImagens> imagens = imovelImagensRepository.findAll();
//		
		return imovelImagensRepository.findAll();
	}
	
	
}

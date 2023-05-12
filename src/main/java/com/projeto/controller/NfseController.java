package com.projeto.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.dto.NfseDTO;
import com.projeto.entity.Lancamento;
import com.projeto.entity.Nfse;
import com.projeto.exeption.NegocioException;
import com.projeto.service.NfseService;
import com.projeto.util.PathUtil;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/nfses")
public class NfseController {
	
	@Autowired
	NfseService nfseService;
	
	@Autowired
	private PathUtil pathUtil; 
	
	//para exibir apenas os ids da referencia parece ser necessario criar um dto :/
	@ApiOperation(value = "Salva nfse")
	@PostMapping
	public Nfse salva(@Valid @RequestBody Nfse nfse) throws NegocioException{
		return nfseService.save(nfse);
	}
	
	public void deletar() {
		
	}
	
	@PutMapping("/{id}")
	public void alterar(@PathVariable("id") Long id, @RequestBody NfseDTO nfse) {
		Nfse n = new Nfse(nfse);
		n.setId(id);
		
		nfseService.save(n);
	}
	
	@GetMapping
	public List<NfseDTO> listarTodos() {
		nfseService.listarTodos();
		return null;
	}
	
	@GetMapping("/{id}")
	public Nfse listarPorId(@PathVariable("id") Long id) throws NegocioException {
		Nfse nfse = nfseService.findById(id);
		
		if(nfse == null) {
			throw new NegocioException("Nao existe nota com o id :" +id);
		}
		return nfse;
	}
	
	
	
	@PatchMapping("/{id}")
	public Nfse updatePerson(@PathVariable(value = "id") Long id, @RequestBody  @Valid Map<String, Object> atributos) throws InstantiationException, IllegalAccessException {
		Nfse p = pathUtil.parseToObject(atributos,Nfse.class);

	    return nfseService.save(p);
	}


}

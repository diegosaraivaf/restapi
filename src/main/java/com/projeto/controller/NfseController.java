package com.projeto.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.dto.NfseDTORequest;
import com.projeto.dto.NfseDTOResponse;
import com.projeto.entity.Contribuinte;
import com.projeto.entity.Nfse;
import com.projeto.exeption.NegocioException;
import com.projeto.service.NfseService;
import com.projeto.util.HibernateUtil;
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
	public Nfse salva(@Valid @RequestBody NfseDTORequest nfse) throws NegocioException{
		Nfse n = new Nfse(nfse);
		nfseService.save(n);
		return n;
	}
	
	public void deletar() {
		
	}
	
	@PutMapping("/{id}")
	public void alterar(@PathVariable("id") Long id, @RequestBody NfseDTORequest nfse) {
		Nfse n = new Nfse(nfse);
		n.setId(id);
		
		nfseService.save(n);
	}
	
	@GetMapping
	public Page<NfseDTOResponse> filtrar(
		@RequestParam(required = false) String localPrestacao,
		@RequestParam(required = false) BigDecimal valor,
		Pageable pageable
	) {
		//.findProductsByFilters(name, category, price);
		//pageable = PageRequest.of(0, 2);
		Page<NfseDTOResponse> nfses =  nfseService.findByFilter(null,null,pageable).map(n -> new NfseDTOResponse(n));
		return nfses;
	}
	
	@GetMapping("/{id}")
	public Nfse listarPorId(@PathVariable("id") Long id) throws NegocioException {
		Nfse nfse = nfseService.findById(id);
		
		if(nfse == null) {
			throw new NegocioException("Nao existe nota com o id :" +id);
		}
		nfse.setPrestador(HibernateUtil.unwrapProxy(nfse.getPrestador())); 
		nfse.setTomador(HibernateUtil.unwrapProxy(nfse.getTomador())); 
		
		return nfse;
	}
	
	
	
	
	
	@PatchMapping("/{id}")
	public Nfse updatePerson(@PathVariable(value = "id") Long id, @RequestBody  @Valid Map<String, Object> atributos) throws InstantiationException, IllegalAccessException {
		Nfse p = pathUtil.parseToObject(atributos,Nfse.class);

	    return nfseService.save(p);
	}


}

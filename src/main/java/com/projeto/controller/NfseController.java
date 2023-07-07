package com.projeto.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;


import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.projeto.entity.Nfse;
import com.projeto.entity.SituacaoNfse;
import com.projeto.exeption.NegocioException;
import com.projeto.service.NfseService;
import com.projeto.util.HibernateUtil;
import com.projeto.util.PathUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Nfse", description = "Acoes relacionadas a emissao de notas ")
@RestController
@RequestMapping("/nfses")
public class NfseController {
	
	@Autowired
	NfseService nfseService;
	
	@Autowired
	private PathUtil pathUtil; 
	
	ModelMapper mapper = new ModelMapper();
	
	@PostMapping
	public Nfse salva(@Valid @RequestBody NfseDTORequest nfse) throws NegocioException{
		Nfse n = new Nfse();
		n.convertFromDTO(nfse);
		n.setDataEmissao(LocalDate.now());
		n.setSituacaoNfse(SituacaoNfse.EMITIDA);
		
		nfseService.save(n);
		return n;
	}
	
	@PutMapping("/{id}")
	public void alterar(@PathVariable("id") Long id, @RequestBody NfseDTORequest nfse) throws NegocioException {
		Nfse n = nfseService.findById(id);
		
		if(n == null) {
			throw new NegocioException("Nfse com id "+id+" nao encontrada");
		}

		n.convertFromDTO(nfse);
		n.setId(id);

		nfseService.save(n);
	}
	
	@GetMapping
	@Operation(summary = "Consulta as notas a partir de um filtro")
	@Parameters({
	    @Parameter(name = "page", description = "numero da pagiana", in = ParameterIn.QUERY),
	    @Parameter(name = "size", description = "quantidade de registros por pagina", in = ParameterIn.QUERY),
	    @Parameter(name = "sort", description = "campo que a consulta via ser ordenada", in = ParameterIn.QUERY)
	})
	public Page<NfseDTOResponse> filtrar(
		@RequestParam(required = false) String localPrestacao,
		@RequestParam(required = false) BigDecimal valor,
		@RequestParam(required = false) SituacaoNfse situacaoNfse,
		@Parameter(hidden = true) Pageable pageable
	) {
		Page<NfseDTOResponse> nfses =  nfseService.findByFilter(localPrestacao,valor,situacaoNfse, pageable).map(n -> new NfseDTOResponse(n));
		return nfses;
	}
	
	@GetMapping("/{id}")
	public Nfse listarPorId(@PathVariable("id") Long id) throws NegocioException {
		Nfse nfse = nfseService.findById(id);
		
		if(nfse == null) {
			throw new NegocioException("Nao existe nota com o id :" +id);
		}
		return nfse;
	}
	
	@DeleteMapping("/{id}")
	public void deletar(@PathVariable Long id) throws NegocioException {
		Nfse nfse = nfseService.findById(id);
		
		if(nfse == null) {
			throw new NegocioException("Nao existe nota com o id :" +id);
		}
		
		nfseService.detelarLongicamente(nfse);
	}
	
	@PatchMapping("/{id}")
	public Nfse updatePerson(@PathVariable(value = "id") Long id, @RequestBody  @Valid Map<String, Object> atributos) throws InstantiationException, IllegalAccessException {
		Nfse p = pathUtil.parseToObject(atributos,Nfse.class);

	    return nfseService.save(p);
	}

}

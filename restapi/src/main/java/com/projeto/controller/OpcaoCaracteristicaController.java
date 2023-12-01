package com.projeto.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.entity.OpcaoCaracteristica;
import com.projeto.exeption.NegocioException;
import com.projeto.service.OpcaoCaracteristicaService;


import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "OpcaoCaracteristicas", description = "Acoes relacionadas a imoveis ")
@RestController
@RequestMapping("/opcoesCaracteristicas")
public class OpcaoCaracteristicaController {
	private final Logger logger = LoggerFactory.getLogger(OpcaoCaracteristicaController.class);
	
	@Autowired
	private OpcaoCaracteristicaService opcaoCaracteristicaService;
	
	
	@PostMapping()
	public OpcaoCaracteristica salvar(@RequestBody OpcaoCaracteristica opcaoCaracteristica) throws NegocioException  {
		logger.info("salvando novo contribuinte");
		
		opcaoCaracteristica = opcaoCaracteristicaService.salvar(opcaoCaracteristica);
		
		return opcaoCaracteristica;
	}
	
	@PutMapping("/{id}")
	public OpcaoCaracteristica update(@PathVariable Long id, @RequestBody OpcaoCaracteristica opcaoCaracteristica) throws NegocioException {
		logger.info("atualizando contribuinte");
		
		OpcaoCaracteristica opcaoCaracteristicaAtual = opcaoCaracteristicaService.porId(id).get();
		if(opcaoCaracteristicaAtual == null) {
			throw new NegocioException("Nao existe contribuinte com o id :"+id+".");
		}
//		provavelmente tenha que ser implementado um metodo para excluir os enderecos de contribuinte antes de salvar todos.
		//enderecoService.saveAll(contribuinte.getEnderecos());
		opcaoCaracteristicaAtual.setNome(opcaoCaracteristica.getNome());
		
		return opcaoCaracteristicaService.salvar(opcaoCaracteristica);
	}
	
	@GetMapping
	public List<OpcaoCaracteristica> filtrar(
			@RequestParam(value = "id", required=false) Long id,
			@RequestParam(value = "documento", required=false) String documento,
			@RequestParam(value = "nome", required=false) String nome,
			@RequestParam(value = "rua", required=false) String rua,
			@RequestParam(value = "bairro", required=false) String bairro
			){
		logger.info("filtrando OpcaoCaracteristica");
		
		List<OpcaoCaracteristica> OpcaoCaracteristicas =  opcaoCaracteristicaService.filtrar();
		return OpcaoCaracteristicas;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable("id") Long id) {
		try {
			logger.info("deletando OpcaoCaracteristica de id {}",id);
			
			OpcaoCaracteristica opcaoCaracteristica = opcaoCaracteristicaService.porId(id)
					.orElseThrow(() -> new NegocioException("Nao existe OpcaoCaracteristica com o id passado",NegocioException.BADREQUEST));
			
			opcaoCaracteristicaService.deletar(opcaoCaracteristica);
			
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}catch (NegocioException e) {
			return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Charset", "UTF-8");

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body("erro inesperado");
		}
	}
	
	@GetMapping("/{idOpcaoCaracteristica}")
	public OpcaoCaracteristica buscarPorId(@PathVariable("idOpcaoCaracteristica") Long idOpcaoCaracteristica) throws NegocioException {
		logger.info("busca de OpcaoCaracteristica por id {}",idOpcaoCaracteristica);
		
		OpcaoCaracteristica  OpcaoCaracteristica  = opcaoCaracteristicaService.porId(idOpcaoCaracteristica).get();
		if(OpcaoCaracteristica == null) {
			throw new NegocioException("OpcaoCaracteristica com o id passado nao existe");
		}
		
		return OpcaoCaracteristica;
	}
	
}

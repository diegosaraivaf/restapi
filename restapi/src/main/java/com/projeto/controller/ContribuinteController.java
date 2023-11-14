package com.projeto.controller;

import java.nio.charset.StandardCharsets;
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

import com.projeto.entity.Contribuinte;
import com.projeto.entity.Endereco;
import com.projeto.exeption.NegocioException;
import com.projeto.service.ContribuinteService;
import com.projeto.service.EnderecoService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Contribuinte", description = "Acoes relacionadas a contribuintes ")
@RestController
@RequestMapping("/contribuintes")
public class ContribuinteController {
	private final Logger logger = LoggerFactory.getLogger(ContribuinteController.class);
	
	@Autowired
	private ContribuinteService contribuinteService;
	
	@Autowired
	private EnderecoService enderecoService;
	
	@PostMapping()
	public ResponseEntity<?> salvar(@RequestBody Contribuinte contribuinte) {
		try {
			logger.info("salvando novo contribuinte");
			
			//enderecoService.saveAll(contribuinte.getEnderecos());
			contribuinte = contribuinteService.salvar(contribuinte);
			
			return ResponseEntity.status(HttpStatus.OK).body(contribuinte);
		}
		catch (NegocioException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Aconteceu um erro inesperado");
		}
	}
	
	@PutMapping("/{id}")
	public Contribuinte update(@PathVariable Long id, @RequestBody Contribuinte contribuinte) throws NegocioException {
		logger.info("atualizando contribuinte");
		
		Contribuinte contribuinteAtual = contribuinteService.porId(id).get();
		if(contribuinteAtual == null) {
			throw new NegocioException("Nao existe contribuinte com o id :"+id+".");
		}
//		provavelmente tenha que ser implementado um metodo para excluir os enderecos de contribuinte antes de salvar todos.
		//enderecoService.saveAll(contribuinte.getEnderecos());
		contribuinteAtual.setDocumento(contribuinte.getDocumento());
		contribuinteAtual.setNome(contribuinte.getNome());
		
		return contribuinteService.salvar(contribuinteAtual);
	}
	
	@GetMapping
	public List<Contribuinte> filtrar(
			@RequestParam(value = "id", required=false) Long id,
			@RequestParam(value = "documento", required=false) String documento,
			@RequestParam(value = "nome", required=false) String nome,
			@RequestParam(value = "rua", required=false) String rua,
			@RequestParam(value = "bairro", required=false) String bairro
			){
		logger.info("filtrando contribuinte");
		
		List<Contribuinte> contribuintes =  contribuinteService.filtrar(id,documento, nome, rua,bairro);
		return contribuintes;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable("id") Long id) {
		try {
			logger.info("deletando contribuinte de id {}",id);
			
			Contribuinte contribuinte = contribuinteService.porId(id)
					.orElseThrow(() -> new NegocioException("Nao existe contribuinte com o id passado",NegocioException.BADREQUEST));
			
			contribuinteService.deletar(contribuinte);
			
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
	
	@GetMapping("/documento/{documento}")
	public ResponseEntity<?> porDocumento(@PathVariable("documento") String documento) {
		logger.info("busca de contribuinte por documento : {}",documento);
		
		Contribuinte contribuinte = contribuinteService.buscarPorDocumento(documento);
		return ResponseEntity.status(HttpStatus.OK).body(contribuinte);
	}
	
	@GetMapping("/{idContribuinte}")
	public Contribuinte buscarPorId(@PathVariable("idContribuinte") Long idContribuinte) throws NegocioException {
		logger.info("busca de contribuinte por id {}",idContribuinte);
		
		Contribuinte  contribuinte  = contribuinteService.porId(idContribuinte).get();
		if(contribuinte == null) {
			throw new NegocioException("Contribuinte com o id passado nao existe");
		}
		
		return contribuinte;
	}
	
	@GetMapping("/{idContribuinte}/enderecos")
	public List<Endereco> porContribuinte(@PathVariable("idContribuinte") Long idContribuinte){
		logger.info("buscando endereco por contribuinte {}",idContribuinte);
		
		return enderecoService.porContribuinte(idContribuinte);
	}
	
	@PostMapping("/{idContribuinte}/enderecos")
	public List<Endereco> enderecoporContribuinte(@PathVariable("idContribuinte") Long idContribuinte,@RequestBody List<Endereco> enderecos){
		logger.info("adicionando enderecos ao contribuinte ");
		
		enderecoService.deletarPorContribuinte(idContribuinte);
		
		//setar contribuinte ao endereco
		Contribuinte cont = new Contribuinte();
		cont.setId(idContribuinte);
		for(Endereco e : enderecos) {
			e.setContribuinte(cont);
			e = enderecoService.save(e);
		}
		return enderecos;
	}
}

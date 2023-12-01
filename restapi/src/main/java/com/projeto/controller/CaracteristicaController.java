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

import com.projeto.entity.Caracteristica;
import com.projeto.entity.Contribuinte;
import com.projeto.entity.Endereco;
import com.projeto.entity.Imovel;
import com.projeto.exeption.NegocioException;
import com.projeto.service.CaracteristicaService;
import com.projeto.service.ContribuinteService;
import com.projeto.service.EnderecoService;
import com.projeto.service.ImovelService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Caracteristicas", description = "Acoes relacionadas a imoveis ")
@RestController
@RequestMapping("/caracteristicas")
public class CaracteristicaController {
	private final Logger logger = LoggerFactory.getLogger(CaracteristicaController.class);
	
	@Autowired
	private CaracteristicaService caracteristicaService;
	
	
	@PostMapping()
	public Caracteristica salvar(@RequestBody Caracteristica caracteristica) throws NegocioException  {
		logger.info("salvando novo contribuinte");
		
		caracteristica = caracteristicaService.salvar(caracteristica);
		
		return caracteristica;
	}
	
	@PutMapping("/{id}")
	public Caracteristica update(@PathVariable Long id, @RequestBody Caracteristica caracteristica) throws NegocioException {
		logger.info("atualizando contribuinte");
		
		Caracteristica caracteristicaAtual = caracteristicaService.porId(id).get();
		if(caracteristicaAtual == null) {
			throw new NegocioException("Nao existe contribuinte com o id :"+id+".");
		}
//		provavelmente tenha que ser implementado um metodo para excluir os enderecos de contribuinte antes de salvar todos.
		//enderecoService.saveAll(contribuinte.getEnderecos());
		caracteristicaAtual.setNome(caracteristica.getNome());
		
		return caracteristicaService.salvar(caracteristica);
	}
	
	@GetMapping
	public List<Caracteristica> filtrar(
			@RequestParam(value = "id", required=false) Long id,
			@RequestParam(value = "documento", required=false) String documento,
			@RequestParam(value = "nome", required=false) String nome,
			@RequestParam(value = "rua", required=false) String rua,
			@RequestParam(value = "bairro", required=false) String bairro
			){
		logger.info("filtrando caracteristica");
		
		List<Caracteristica> caracteristicas =  caracteristicaService.filtrar();
		return caracteristicas;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable("id") Long id) {
		try {
			logger.info("deletando caracteristica de id {}",id);
			
			Caracteristica caracteristica = caracteristicaService.porId(id)
					.orElseThrow(() -> new NegocioException("Nao existe caracteristica com o id passado",NegocioException.BADREQUEST));
			
			caracteristicaService.deletar(caracteristica);
			
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
	
	@GetMapping("/{idCaracteristica}")
	public Caracteristica buscarPorId(@PathVariable("idCaracteristica") Long idCaracteristica) throws NegocioException {
		logger.info("busca de caracteristica por id {}",idCaracteristica);
		
		Caracteristica  caracteristica  = caracteristicaService.porId(idCaracteristica).get();
		if(caracteristica == null) {
			throw new NegocioException("caracteristica com o id passado nao existe");
		}
		
		return caracteristica;
	}
	
}

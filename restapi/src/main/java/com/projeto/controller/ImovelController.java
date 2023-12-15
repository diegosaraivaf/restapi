package com.projeto.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.multipart.MultipartFile;

import com.projeto.entity.Contribuinte;
import com.projeto.entity.Endereco;
import com.projeto.entity.Imovel;
import com.projeto.entity.ImovelImagens;
import com.projeto.exeption.NegocioException;
import com.projeto.service.ContribuinteService;
import com.projeto.service.EnderecoService;
import com.projeto.service.ImovelImagensService;
import com.projeto.service.ImovelService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Imoveis", description = "Acoes relacionadas a imoveis ")
@RestController
@RequestMapping("/imoveis")
public class ImovelController {
	private final Logger logger = LoggerFactory.getLogger(ImovelController.class);
	
	@Autowired
	private ImovelService imovelService;
	
	@Autowired
	private ImovelImagensService imovelImagensService;
	

	
	@PostMapping()
	public Imovel salvar(@RequestBody Imovel imovel) {
		logger.info("salvando novo contribuinte");
		
		imovel = imovelService.salvar(imovel);
		
		return imovel;
	}
	
	@PutMapping("/{id}")
	public Imovel update(@PathVariable Long id, @RequestBody Imovel imovel) throws NegocioException {
		logger.info("atualizando contribuinte");
		
		Imovel imovelAtual = imovelService.findById(id);
		if(imovelAtual == null) {
			throw new NegocioException("Nao existe imovel com o id :"+id+".");
		}
//		provavelmente tenha que ser implementado um metodo para excluir os enderecos de contribuinte antes de salvar todos.
		//enderecoService.saveAll(contribuinte.getEnderecos());
		imovelAtual.setProprietario(imovel.getProprietario());

		
		return imovelService.salvar(imovelAtual);
	}
	
	@GetMapping
	public List<Imovel> filtrar(
			@RequestParam(value = "id", required=false) Long id,
			@RequestParam(value = "documento", required=false) String documento,
			@RequestParam(value = "nome", required=false) String nome,
			@RequestParam(value = "rua", required=false) String rua,
			@RequestParam(value = "bairro", required=false) String bairro
			){
		logger.info("filtrando contribuinte");
		
		List<Imovel> imoveis =  imovelService.filtrar();
		return imoveis;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable("id") Long id) {
		try {
			logger.info("deletando contribuinte de id {}",id);
			
			Imovel imovel = imovelService.findById(id);
			if(imovel == null) { 
				throw new NegocioException("Nao existe imovel com o id passado",NegocioException.BADREQUEST);
			}
			
			imovelService.delete(imovel);
			
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
		catch (NegocioException e) {
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
	

	
	@GetMapping("/{idImovel}")
	public Imovel buscarPorId(@PathVariable("idImovel") Long idImovel) throws NegocioException {
		logger.info("busca de imovel por id {}",idImovel);
		
		Imovel  imovel  = imovelService.findById(idImovel);
		if(imovel == null) {
			throw new NegocioException("imovel com o id passado nao existe");
		}
		
		return imovel;
	}
	
	@GetMapping("/{idImovel}/imagens")
	public List<ImovelImagens> porimovel(@PathVariable("idImovel") Long idImovel){
		logger.info("buscando endereco por imovel {}",idImovel);
		
		return imovelImagensService.porImovel(idImovel);
	}
	
	@PostMapping("/{idImovel}/imagens")
	public ImovelImagens salvarImovelImagens(@PathVariable("idImovel") Long idImovel, @RequestParam MultipartFile file){
		logger.info("adicionando imagens ao imovel ");
		
		try {
			byte[] bytes = file.getBytes();
			String nomeImagem = String.valueOf(file.getOriginalFilename());
			String identificador = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
			Path caminho = Paths.get("c:/img/"+identificador);
			Files.write(caminho,bytes);
			
			Imovel imovel = new Imovel();
			imovel.setId(idImovel);
			
			ImovelImagens imagens = new ImovelImagens();
			imagens.setImovel(imovel);
			imagens.setNomeArquivo(nomeImagem);
			imagens.setIdentificador(identificador);
	
			return imovelImagensService.salvar(imagens);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}

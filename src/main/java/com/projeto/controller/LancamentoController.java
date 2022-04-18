package com.projeto.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
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

import com.projeto.dto.LancamentoDTO;
import com.projeto.entity.Lancamento;
import com.projeto.entity.TipoLancamento;
import com.projeto.exeption.NegocioExeption;
import com.projeto.service.LancamentoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

//@Api(value="Endpoint Lancamentos",description="Descricao sobre esse endpoint",tags= {"Endpoint Lancamentos"})
@Api(tags= {"Endpoint Lancamentos"})
@RestController
@RequestMapping("/lancamentos")
public class LancamentoController {
	
	//tirar
	@PostConstruct
	public void posConstruct() {
		System.out.println("construido");
	}
	
	@PreDestroy
	public void preDestroy() {
		System.out.println("destruido");
	}
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	//@GetMapping
	public List<Lancamento> listar() {
		Pageable pageable = PageRequest.of(1, 12, Sort.by(Direction.DESC,"id"));
		
		return lancamentoService.findAll(pageable);
	}
	
	@ApiOperation(value = "Filtra lancamentos")
	@GetMapping
	public List<Lancamento> filtrarLancamentos(
			@RequestParam(value = "id", required=false) Long id,
			@RequestParam(value = "tipoLancamento", required=false) TipoLancamento tipoLancamento,
			@RequestParam(value = "valor", required=false) BigDecimal valor,
			@RequestParam(value = "contribuinteNome", required=false) String contribuinteNome,
			@RequestParam(value = "contribuinteDocumento", required=false) String contribuinteDocumento,
			@RequestParam(value = "dataEmissao", required=false) LocalDate dataEmissao,
			@RequestParam(value = "pagina",defaultValue = "0") int pagina,
			@RequestParam(value = "limite",defaultValue = "12") int limite) {
		return lancamentoService.filtrarLancamentos(id, tipoLancamento, valor, null,contribuinteNome, contribuinteDocumento, pagina,limite);
	}
	
	@ApiOperation(value = "Salva lancamento")
	@PostMapping
	public Lancamento salvar(@RequestBody @Valid LancamentoDTO lancamentoDTO) throws NegocioExeption {
		if(lancamentoDTO.getTipoLancamento()== null) {
			throw new NegocioExeption("Campo 'tipo' nao pode ser nulo. ", NegocioExeption.BADREQUEST);
		}
		
		Lancamento lancamento = modelMapper.map(lancamentoDTO, Lancamento.class);
		return lancamentoService.salvar(lancamento);
	}
	
	@ApiOperation(value = "Atualiza lancamento por id")
	@PutMapping("/{id}")
	public Lancamento atualizar(@PathVariable("id")Long id, @RequestBody Lancamento lancamento) throws NegocioExeption {
		
		//mudar este codigo para path id nao pode ser nulo 
		if(lancamento.getId() == null ) {
			throw new NegocioExeption("O 'id' do lancamento nao pode ser vazio",NegocioExeption.BADREQUEST);
		}
		//adicionar consulta por id, verifica se o lancamento existe, se nao existe, retornar 404 
		
		//pensar na possibilidade : toda vez que vo atualizar um registro, tenho que preenche todos os dados dele ? inclusive as dependencias dele ? 
		
		return lancamentoService.salvar(lancamento);
	}
	
	@ApiOperation(value = "Remove lancamento por id")
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		//mudar esse optional depois
		Optional<Lancamento> lancamento = lancamentoService.buscarPorId(id);
		lancamentoService.deletar(lancamento.get());
	}
	
	@ApiOperation(value = "Busca lancamento por id")
	@GetMapping("/{id}")
	public ResponseEntity<?>  buscarPorId(@PathVariable("id") Long id) {
		//retirar Optional
		Lancamento lancamento = lancamentoService.buscarPorId(id).get();
		return ResponseEntity.status(HttpStatus.OK).body(lancamento) ;
	}

}

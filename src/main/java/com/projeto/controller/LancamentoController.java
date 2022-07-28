package com.projeto.controller;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.hibernate.mapping.OneToMany;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projeto.dto.LancamentoDTO;
import com.projeto.entity.Lancamento;
import com.projeto.entity.Pessoa;
import com.projeto.entity.TipoLancamento;
import com.projeto.exeption.NegocioException;
import com.projeto.repository.RepositoryGeneric;
import com.projeto.repository.RepositoryGenericoImpl;
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
	
	RepositoryGenericoImpl<Lancamento, Long> repositoryGeneric;
	
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
	public Lancamento salvar(@RequestBody @Valid LancamentoDTO lancamentoDTO) throws NegocioException {
		if(lancamentoDTO.getTipoLancamento()== null) {
			throw new NegocioException("Campo 'tipo lancamento' nao pode ser nulo. ", NegocioException.BADREQUEST);
		}
		
		Lancamento lancamento = modelMapper.map(lancamentoDTO, Lancamento.class);
		return lancamentoService.salvar(lancamento);
	}
	
	@ApiOperation(value = "Atualiza lancamento por id")
	@PutMapping("/{id}")
	public Lancamento atualizar(@PathVariable("id")Long id, @RequestBody Lancamento lancamento) throws NegocioException {
		
		//mudar este codigo para path id nao pode ser nulo 
		if(lancamento.getId() == null ) {
			throw new NegocioException("O 'id' do lancamento nao pode ser vazio",NegocioException.BADREQUEST);
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
	
	/**
	 * TRYING CREATE A PATH MATHOD GENERIC 
	 * @param id
	 * @param atributos
	 */
	@PatchMapping("/{id}")
	public void atualizarParcialmenteTeste(@PathVariable("id") Long id, @RequestBody Map<String,Object> atributos) {
		try {
			Lancamento p = parseToObject(atributos,Lancamento.class);
			System.out.println(p);
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//atualiza pessoa destino ;
		
	}

	public <T> T parseToObject(Map<String, Object> atributos,Class<T> classe) throws InstantiationException, IllegalAccessException {
		//busca pessoa por id se nao achar retorna codigo 403
//		Class<T> objetoDestino = classe.getClass().newInstance();
		T objetoAtual = (T) repositoryGeneric.porId(1L);
		
		//seta os valores dos atributaos passados,e passado uma lista porques existe a possibilidade do cliente querer setar null em um atributo
		ObjectMapper objectMapper = new ObjectMapper();
		//object mapper foi utilizadeo porque existem varias conversoes que seriam necessario fazer manualmente como de int para bigdevimal ....
		T objetoNovo = objectMapper.convertValue(atributos, classe);
		
		atributos.forEach((nomePropriedade,valorPropriedade) ->{
			//tornar esse trecho generico 
			Field field = ReflectionUtils.findField(classe, nomePropriedade);
			//field.getDeclaredAnnotations()
			
			//caso seja um relacao atualizar o objeto da relacao com o atributo passado 
			if(field.isAnnotationPresent(javax.persistence.OneToMany.class)) {
				try {
					Map<String,Object> map = new HashMap<String, Object>();
//					(ArrayList<LinkedHashMap)valorPropriedade
					ArrayList<LinkedHashMap> map2 =  ((ArrayList<LinkedHashMap>)valorPropriedade);
//					map2.stream().forEach(m -> 
//						m.entrySet().stream().forEach(m2 -> 
//							map.put(m2.getKey().toString(), m2.get(m2.keySet().toString()))
//						)
//							
//					);
					
					
					parseToObject(map, field.getClass());
				} catch (InstantiationException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			field.setAccessible(true);
			
			Object novoValor =ReflectionUtils.getField(field, objetoNovo);
			
			ReflectionUtils.setField(field, objetoAtual, novoValor);
		});
		
		
		//adicionar regra se o campo tiver anotacao @ManyToMany ManyToOne OneToMany OneToOne 
		//repetir o processo de atualizacao de objeto 
		
		return objetoAtual;
	}

}

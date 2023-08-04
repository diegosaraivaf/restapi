package com.projeto.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.PatchMapping;
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
import com.projeto.exeption.NegocioException;
import com.projeto.service.LancamentoService;
import com.projeto.util.PathUtil;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name =  "Lancamento")
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
	
	private PathUtil pathUtil;
	
	//@GetMapping
	public List<Lancamento> listar() {
		Pageable pageable = PageRequest.of(1, 12, Sort.by(Direction.DESC,"id"));
		
		return lancamentoService.findAll(pageable);
	}
	
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
	
	@GetMapping("transacao")
	public void testarTransacao() throws NegocioException {
		lancamentoService.testeTransaction();
	}
	
	@PostMapping
	public Lancamento salvar(@RequestBody @Valid LancamentoDTO lancamentoDTO) throws NegocioException {
		if(lancamentoDTO.getTipoLancamento()== null) {
			throw new NegocioException("Campo 'tipo lancamento' nao pode ser nulo. ", NegocioException.BADREQUEST);
		}
		if(lancamentoDTO.getParcelas() == null) {
			throw new NegocioException("Campo 'parcelas' nao pode ser nulo. 2 ", NegocioException.BADREQUEST);
		}
		
		Lancamento lancamento = modelMapper.map(lancamentoDTO, Lancamento.class);
		return lancamentoService.salvar(lancamento);
	}
	
	@PutMapping("/{id}")
	public Lancamento atualizar(@PathVariable("id")Long id, @RequestBody Lancamento lancamento) throws NegocioException {
		
		//mudar este codigo para path id nao pode ser nulo 
		if(id== null ) {
			throw new NegocioException("O 'id' do lancamento nao pode ser vazio",NegocioException.BADREQUEST);
		}
		if(lancamentoService.buscarPorId(id).get() == null) {
			throw new NegocioException("O id passado nao existe");
		}
		
		//pensar na possibilidade : toda vez que vo atualizar um registro, tenho que preenche todos os dados dele ? inclusive as dependencias dele ? (aparentemente as dependencias devem ser salvas 
		//em outros endpoints o que deve ser passada para esta instancia sao os ids de referencia.)
		lancamento.setId(id);
		return lancamentoService.salvar(lancamento);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		//mudar esse optional depois
		Optional<Lancamento> lancamento = lancamentoService.buscarPorId(id);
		lancamentoService.deletar(lancamento.get());
	}
	
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
			Lancamento p = pathUtil.parseToObject(atributos,Lancamento.class);
			System.out.println(p);
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//atualiza pessoa destino ;
		
	}

}

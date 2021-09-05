package com.projeto.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.entity.Lancamento;
import com.projeto.entity.TipoLancamento;
import com.projeto.exeption.NegocioExeption;
import com.projeto.service.LancamentoService;

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
	
	//@GetMapping
	public List<Lancamento> listar() {
		return lancamentoService.findAll();
	}
	
	@GetMapping
	public List<Lancamento> filtrarLancamentos(
			@RequestParam(value = "id", required=false) Long id,
			@RequestParam(value = "tipoLancamento", required=false) TipoLancamento tipoLancamento,
			@RequestParam(value = "valor", required=false) BigDecimal valor) {
		return lancamentoService.filtrarLancamentos(id, tipoLancamento, valor, null);
	}
	
	@PostMapping
	public Lancamento salvar(@RequestBody Lancamento lancamento) throws NegocioExeption {
		if(lancamento.getTipoLancamento()== null) {
			throw new NegocioExeption("Campo 'tipo' nao pode ser nulo. ", NegocioExeption.BADREQUEST);
		}
		return lancamentoService.salvar(lancamento);
	}
	
	@PutMapping("/{id}")
	public Lancamento atualizar(@PathVariable("id")Long id, @RequestBody Lancamento lancamento) throws NegocioExeption {
		if(lancamento.getId() == null ) {
			throw new NegocioExeption("O 'id' do lancamento nao pode ser vazio",NegocioExeption.BADREQUEST);
		}
		return lancamentoService.salvar(lancamento);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		//mudar esse optional depois
		Optional<Lancamento> lancamento = lancamentoService.buscarPorId(id);
		lancamentoService.deletar(lancamento.get());
	}
	
	@GetMapping("/{id}")
	public Lancamento buscarPorId(@PathVariable("id") Long id) {
		//retirar Optional
		return lancamentoService.buscarPorId(id).get();
	}

}

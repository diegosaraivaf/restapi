package com.projeto.controller;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.dto.LancamentoDTO;
import com.projeto.entity.Lancamento;

@RestController
@RequestMapping("testes")
public class TesteController {
	
	/**
	 * Teste de validacao com bean validator
	 * @Valid para ativar a execulcao da validacao e 
	 * @NotNull para adicionar restrincoes na entidade  
	 * */
	@PostMapping
	public Lancamento salvar(@RequestBody @Valid LancamentoDTO lancamentoDTO) {
		return null;
	}
	
	/**
	 * exercoes 
	 * @Controlleradvice
	 * class apexeptionHandler
	 *  
	 * */

}

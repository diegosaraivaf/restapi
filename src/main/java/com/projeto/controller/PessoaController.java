package com.projeto.controller;


import java.lang.reflect.Field;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projeto.entity.Pessoa;
import com.projeto.exeption.NegocioException;
import com.projeto.repository.PessoaRepository;
import com.projeto.repository.RepositoryGeneric;
import com.projeto.repository.RepositoryGenericImpl;
import com.projeto.service.PessoaService;

@RestController
@RequestMapping(value = "/api/pessoas")
public class PessoaController {
	
	//existe um problema ao atualizar uma objecto com uma lista de atributos a qual a lista nao e carregada na tela 
	//(por causa de jsonIgnore por exemplo) ao passar o objeto por parametro sem a lista o jpa exclui a lista 
	
	@Autowired
	private PessoaService pessoaService;

	@GetMapping("/{id}")
	public Pessoa porId(@PathVariable(value = "id") Long id) throws NegocioException {
		if(0==0) {
			throw new NegocioException("Pessoa nao encontrada", NegocioException.BADREQUEST);
		}
		Pessoa p = new Pessoa();
		p.setNome("diego");
		p.setEndereco("endereco@endereco");
		
		return p;
	} 
	
	@RequestMapping(value="/greeting",method = RequestMethod.GET)
	public String greeting(@RequestParam(value = "nome",defaultValue = "teste")String nome) {
//		String x = null;
//		x.length();
		return "voce envio o valor =>"+nome;
	} 
	
	@RequestMapping("/greeting2/{nome}")
	public String greeting2(@PathVariable(value = "nome")String nome) {
		return "voce envio o valor =>"+nome;
	}
	
	@GetMapping("/teste/{nome}")
	public void  testePorNomr(@PathVariable(value="nome") String nome ) {
		
	}
	
	@PostMapping("/pessoa")
	public ResponseEntity<Pessoa> salvar(@RequestBody Pessoa pessoa) {
		try {
//			String x = null;
//			x.length();
			//metodo de salvar
			//Pessoa p = new Pessoa();
			pessoa = pessoaService.salvar(pessoa);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);
		}catch (Exception e) {
			//o Exceptoin tem quer ser mais especifico para que seja possivel escolher o httpStatus mais adequado
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@PutMapping("/pessoa")
	public void atualizar(@PathVariable("id") Long id, @RequestBody Pessoa pessoa) {
		//necessario verificar se o id existe caso nao retornar codigo 403
		
		//e possivel tbm passar o id de pessoa por parametro com o @PathVariable
	}
	@PatchMapping("/pessoa/{id}")
	public void atualizarParcialmente(@PathVariable("id") Long id, @RequestBody Map<String,Object> atributos) {
		//busca pessoa por id se nao achar retorna codigo 403
		Pessoa pessoaDestino = new Pessoa();
		 
		//seta os valores dos atributaos passados,e passado uma lista porques existe a possibilidade do cliente querer setar null em um atributo
		ObjectMapper objectMapper = new ObjectMapper();
		//object mapper foi utilizadeo porque existem varias conversoes que seriam necessario fazer manualmente como de int para bigdevimal ....
		Pessoa pessoaOrigem = objectMapper.convertValue(atributos, Pessoa.class);
		
		atributos.forEach((nomePropriedade,valorPropriedade) ->{
			//tornar esse trecho generico 
			Field field = ReflectionUtils.findField(Pessoa.class, nomePropriedade);
			field.setAccessible(true);
			
			Object novoValor =ReflectionUtils.getField(field, pessoaOrigem);
			
			ReflectionUtils.setField(field, pessoaDestino, novoValor);
		});
		
		
		//atualiza pessoa destino ;
	}
	
	
	
}

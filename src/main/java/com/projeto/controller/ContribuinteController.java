package com.projeto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.entity.Contribuinte;
import com.projeto.exeption.NegocioExeption;
import com.projeto.service.ContribuinteService;

@RestController
@RequestMapping("/contribuintes")
public class ContribuinteController {
	
	@Autowired
	private ContribuinteService contribuinteService;
	
	@PostMapping("/")
	public ResponseEntity<?> salvar(@RequestBody Contribuinte constribuinte) {
		try {
			Contribuinte contribuinte = contribuinteService.salvar(constribuinte);
			return ResponseEntity.status(HttpStatus.OK).body(contribuinte);
		}
		catch (NegocioExeption e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Aconteceu um erro inesperado");
		}
	}
	
	@GetMapping("/")
	public ResponseEntity<?> pesquisar(){
		List<Contribuinte> contribuintes =  contribuinteService.pesquisar();
		return ResponseEntity.status(HttpStatus.OK).body(contribuintes);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable("id") Long id) {
		try {
			Contribuinte contribuinte = contribuinteService.porId(id)
					.orElseThrow(() -> new NegocioExeption("Nao existe contribuinte com o id passado",NegocioExeption.BADREQUEST));
			
			contribuinteService.deletar(contribuinte);
			
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}catch (NegocioExeption e) {
			return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("erro inesperado");
		}
	}
	
	@GetMapping("/documento/{documento}")
	public ResponseEntity<?> porDocumento(@PathVariable("documento") String documento) {
		Contribuinte contribuinte = contribuinteService.buscarPorDocumento(documento);
		return ResponseEntity.status(HttpStatus.OK).body(contribuinte);
	}
	
	@GetMapping("/{idContribuinte}")
	public Contribuinte buscarPorId(@PathVariable("idContribuinte") Long idContribuinte) throws NegocioExeption {
		Contribuinte  contribuinte  = contribuinteService.porId(idContribuinte).get();
		if(contribuinte == null) {
			throw new NegocioExeption("Contribuinte com o id passado nao existe");
		}
		
		return contribuinte;
	}
	

}

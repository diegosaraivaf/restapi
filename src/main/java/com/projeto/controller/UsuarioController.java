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
import org.springframework.web.bind.annotation.RestController;

import com.projeto.entity.Usuario;
import com.projeto.service.UsuarioService;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {  
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping("/")
	public Usuario salvar(@RequestBody Usuario usuario) {
		return usuarioService.salvar(usuario);
	}
	
	@DeleteMapping("/{id}")
	public void detelar(@PathVariable("id") Long id) {
		usuarioService.deletar(id);
	}
	
	@GetMapping("/")
	public List<Usuario> listar(){
		return usuarioService.listarUsuario();
	}
	
	//ver como passar somente string de email,senha
	@PostMapping("/autenticar")
	public ResponseEntity<?> validar(@RequestBody Usuario usuario) {
		Usuario usua = usuarioService.findByEmailAndSenha(usuario.getEmail(), usuario.getSenha());
		if(usua != null) {
			return ResponseEntity.status(HttpStatus.OK).body(usua);
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario ou senha invalidos");
		}
	}

}

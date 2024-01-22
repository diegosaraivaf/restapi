package com.projeto.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.joda.DateTimeFormatterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.dto.TokenDTO;
import com.projeto.entity.Usuario;
import com.projeto.exeption.NegocioException;
import com.projeto.service.EmailService;
import com.projeto.service.JwtService;
import com.projeto.service.UsuarioService;

import io.swagger.v3.oas.annotations.tags.Tag;

//import io.swagger.annotations.Api;

@Tag(name = "Usuario", description = "Acoes relacionadas a usuario")
@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {  
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private EmailService emailService;
	
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
		try {
			Usuario usuarioAutenticado = usuarioService.autenticarUsuario(usuario.getEmail(), usuario.getSenha());
			
			String token = jwtService.gerarToken(usuarioAutenticado);
			TokenDTO tokenDTO = new TokenDTO(usuarioAutenticado.getNome(),token);
			return ResponseEntity.ok(tokenDTO);
			//return ResponseEntity.status(HttpStatus.OK).body(usua);	
		}
		catch (NegocioException e) {
			return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Um erro inesperado aconteceu");
		}
	}
	
	@PostMapping("/gerarCodigoRecuperacaoSenha")
	public void geraCodigoRecuperacaoSenha(String email ) throws NegocioException {
		Usuario usuario  = usuarioService.findByEmail(email);
		
		if(usuario == null) {
			throw new NegocioException("Email invalido");
		}
		
		String codigoRecuperacaoSenha = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyymmssmm"+usuario.getId()));
		usuario.setCodigoRecuperacaoSenha(codigoRecuperacaoSenha);
		usuario.setDataGeracaoCodigoRecuperacaoSenha(LocalDateTime.now());
		usuarioService.atualizar(usuario);
		
		emailService.enviar(
				email, 
				"Recuperacao de senha ", 
				"clique no link abaixo para redefinir sua senha http://localhost:3000/RecuperarSenha?codigoRecuperacaoSenha="+codigoRecuperacaoSenha+"&email="+email);
		
	}
	
	@PostMapping("/redefinirSenha")
	public void redefinirSenha(Usuario usuario ) throws NegocioException {
		Usuario usuarioAux  = usuarioService.findByEmail(usuario.getEmail());
		
		if(usuarioAux == null) {
			throw new NegocioException("Email invalido");
		}
		if(usuarioAux.getCodigoRecuperacaoSenha() != usuario.getCodigoRecuperacaoSenha()) {
			throw new NegocioException("Codigo invalido");
		}
		if(ChronoUnit.MINUTES.between(usuarioAux.getDataGeracaoCodigoRecuperacaoSenha(), LocalDateTime.now()) > 30) {
			throw new NegocioException("Codigo de recuperacao esta inspirado, faça outro pedido");
		}
		
		usuarioAux.setSenha(usuario.getSenha());
		usuarioService.salvar(usuarioAux);
		
	}

}

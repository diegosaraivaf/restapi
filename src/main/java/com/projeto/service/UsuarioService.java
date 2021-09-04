package com.projeto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projeto.entity.Usuario;
import com.projeto.exeption.NegocioExeption;
import com.projeto.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Usuario salvar(Usuario usuario) {
		String senha  = passwordEncoder.encode(usuario.getSenha());
		usuario.setSenha(senha);
		return usuarioRepository.save(usuario);
	}
	
	public List<Usuario> listarUsuario() {
		return usuarioRepository.findAll();
	}
	
	public void deletar(Usuario usuario) {
		usuarioRepository.delete(usuario);
	}
	
	public void deletar(Long id) {
		Usuario usuario = porId(id);
		usuarioRepository.delete(usuario);
	}
	
	public Usuario porId(Long id) {
		return usuarioRepository.findById(id).get();
	}
	
	public Usuario autenticarUsuario(String email,String senha) throws NegocioExeption {
		Usuario usuario = findByEmail(email);
		
		if(usuario == null) {
			throw new NegocioExeption("Usuario nao encontrado");
		}
		
		if(!passwordEncoder.matches(senha, usuario.getSenha())) {
			throw new NegocioExeption("Senha invalida");
		}
		return usuario;
	}
	
	public Usuario findByEmail(String email) {
		return usuarioRepository.findByEmail(email);
	}
	
}

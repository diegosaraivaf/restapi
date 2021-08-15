package com.projeto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.entity.Usuario;
import com.projeto.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario salvar(Usuario usuario) {
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
	
	public Usuario findByEmailAndSenha(String email,String senha) {
		return usuarioRepository.findByEmailAndSenha(email,senha);
	}
	
}

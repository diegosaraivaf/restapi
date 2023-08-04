package com.projeto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.projeto.entity.Usuario;

@Service
public class SecurityUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UsuarioService usuarioService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = usuarioService.findByEmail(email);
		if(usuario == null) {
			throw new UsernameNotFoundException("Email nao cadastrado");
		}
		
		return User.builder()
				.username(usuario.getEmail())
				.password(usuario.getSenha())
				.roles("USER")
				.build();
	}

}

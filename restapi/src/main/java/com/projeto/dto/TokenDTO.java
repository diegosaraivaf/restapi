package com.projeto.dto;

public class TokenDTO {
	
	private String nome;
	
	private String token;
	
	public TokenDTO(String nome, String toke) {
		super();
		this.nome = nome;
		this.token = toke;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
}

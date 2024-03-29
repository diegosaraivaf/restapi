package com.projeto.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String nome;
	
	@Column(name = "sobre_nome")
	private String sobreNome;
	
	@Column
	private String email;
	
	@Column
	private String senha;
	
	@Column
	private String codigoRecuperacaoSenha;
	
	@Column
	private LocalDateTime dataGeracaoCodigoRecuperacaoSenha;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobreNome() {
		return sobreNome;
	}

	public void setSobreNome(String sobreNome) {
		this.sobreNome = sobreNome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCodigoRecuperacaoSenha() {
		return codigoRecuperacaoSenha;
	}

	public void setCodigoRecuperacaoSenha(String codigoRecuperacaoSenha) {
		this.codigoRecuperacaoSenha = codigoRecuperacaoSenha;
	}

	public LocalDateTime getDataGeracaoCodigoRecuperacaoSenha() {
		return dataGeracaoCodigoRecuperacaoSenha;
	}

	public void setDataGeracaoCodigoRecuperacaoSenha(LocalDateTime dataGeracaoCodigoRecuperacaoSenha) {
		this.dataGeracaoCodigoRecuperacaoSenha = dataGeracaoCodigoRecuperacaoSenha;
	}
}

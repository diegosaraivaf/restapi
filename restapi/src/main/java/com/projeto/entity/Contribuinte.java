package com.projeto.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Contribuinte implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	private String documento;
	
//	@JsonIgnore
//	@JoinColumn(name = "contribuinte_id")
//	@OneToMany(fetch = FetchType.LAZY)
//	private List<Endereco> enderecos;
	
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
//	public List<Endereco> getEnderecos() {
//		return enderecos;
//	}
//	public void setEnderecos(List<Endereco> enderecos) {
//		this.enderecos = enderecos;
//	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
}

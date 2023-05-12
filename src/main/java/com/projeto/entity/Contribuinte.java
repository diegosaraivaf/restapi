package com.projeto.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Contribuinte {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 @ApiModelProperty(value = "ID do endereço", example = "1") 
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

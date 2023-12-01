package com.projeto.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Caracteristica implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	@Column
	@Enumerated(EnumType.STRING)
	private TipoCaracteristica tipoCaracteristica;
	
	@OneToMany
	@JoinColumn(name = "caracteristica_id")
	private List<OpcaoCaracteristica> opcoesCaracteristica;

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

	public TipoCaracteristica getTipoCaracteristica() {
		return tipoCaracteristica;
	}

	public void setTipoCaracteristica(TipoCaracteristica tipoCaracteristica) {
		this.tipoCaracteristica = tipoCaracteristica;
	}

	public List<OpcaoCaracteristica> getOpcoesCaracteristica() {
		return opcoesCaracteristica;
	}

	public void setOpcoesCaracteristica(List<OpcaoCaracteristica> opcoesCaracteristica) {
		this.opcoesCaracteristica = opcoesCaracteristica;
	}
	
}

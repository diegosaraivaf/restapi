package com.projeto.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ImovelCaracteristica implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Imovel imovel;
	
	private Caracteristica caracteristica;
	
	private OpcaoCaracteristica opcaoCaracteristica;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Imovel getImovel() {
		return imovel;
	}

	public void setImovel(Imovel imovel) {
		this.imovel = imovel;
	}

	public Caracteristica getCaracteristica() {
		return caracteristica;
	}

	public void setCaracteristica(Caracteristica caracteristica) {
		this.caracteristica = caracteristica;
	}

	public OpcaoCaracteristica getOpcaoCaracteristica() {
		return opcaoCaracteristica;
	}

	public void setOpcaoCaracteristica(OpcaoCaracteristica opcaoCaracteristica) {
		this.opcaoCaracteristica = opcaoCaracteristica;
	}
	
}

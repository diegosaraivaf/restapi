package com.projeto.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Nfse {
	
	@Id
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "prestador_id")
	private Contribuinte prestador;
	
	@ManyToOne
	@JoinColumn(name = "tomador_id")
	private Contribuinte tomador;
	
	@Column(name = "local_prestacao")
	private String localPrestacao;//criar objeto municipio
	
	@Column(name="lista_items")
	private String listaItemns;//criar objeto de item de servico (descricao,quantidade,valor)
	
	@Column(name="valor_servico")
	private BigDecimal valorservico;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Contribuinte getPrestador() {
		return prestador;
	}

	public void setPrestador(Contribuinte prestador) {
		this.prestador = prestador;
	}

	public Contribuinte getTomador() {
		return tomador;
	}

	public void setTomador(Contribuinte tomador) {
		this.tomador = tomador;
	}

	public String getLocalPrestacao() {
		return localPrestacao;
	}

	public void setLocalPrestacao(String localPrestacao) {
		this.localPrestacao = localPrestacao;
	}

	public String getListaItemns() {
		return listaItemns;
	}

	public void setListaItemns(String listaItemns) {
		this.listaItemns = listaItemns;
	}

	public BigDecimal getValorservico() {
		return valorservico;
	}

	public void setValorservico(BigDecimal valorservico) {
		this.valorservico = valorservico;
	}
}

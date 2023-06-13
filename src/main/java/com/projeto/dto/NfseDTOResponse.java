package com.projeto.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.hibernate.Hibernate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.projeto.entity.Contribuinte;
import com.projeto.entity.ItemNfse;
import com.projeto.entity.Nfse;

import io.swagger.annotations.ApiModelProperty;

//a serializacao olha pro set? (funcionou uma vez )
//quando e um tipo nativo do java o 'example' funciona, mas quando e entity nao(tentar atualizar swagger depois )

@JsonInclude(Include.ALWAYS)
@JsonIgnoreProperties(value = {"tomador.nome"})
public class NfseDTOResponse implements Serializable{

	private static final long serialVersionUID = 1L;

	private Contribuinte prestador;
	
	private Contribuinte tomador;
	
	private String localPrestacao;
	
	private List<ItemNfse> itensNfse;
	
	private BigDecimal valorServico;
	
	public NfseDTOResponse() {
	}
	
	public NfseDTOResponse(Nfse nfse) {
		prestador = nfse.getPrestador();
		tomador = nfse.getTomador();
		localPrestacao = nfse.getLocalPrestacao();
		itensNfse  = nfse.getItensNfse();
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

	public List<ItemNfse> getItensNfse() {
		return itensNfse;
	}

	public void setItensNfse(List<ItemNfse> itensNfse) {
		this.itensNfse = itensNfse;
	}

	public BigDecimal getValorServico() {
		return valorServico;
	}

	public void setValorServico(BigDecimal valorServico) {
		this.valorServico = valorServico;
	}
}

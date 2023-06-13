package com.projeto.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.projeto.entity.Contribuinte;
import com.projeto.entity.ItemNfse;

import io.swagger.annotations.ApiModelProperty;

//a serializacao olha pro set? (funcionou uma vez )
//quando e um tipo nativo do java o 'example' funciona, mas quando e entity nao(tentar atualizar swagger depois )

@JsonInclude(Include.ALWAYS)
@JsonIgnoreProperties(value = {"tomador.nome"})
public class NfseDTORequest {
	
//	dataType  = "com.projeto.entity.Contribuinte"
	@ApiModelProperty(value = "Id referente ao Contribuinte", required = true  )
	@NotNull
	private Long prestadorId;
	
	 
	//@JsonIgnoreProperties({"nome", "documento"})
	 //@ApiModelProperty(value = "Endereço da pessoa", required = true,  example = "{'id':1}",dataType  = "com.projeto.entity.Contribuinte",hidden=true)
	//@ApiModelProperty(value = "Id referente o Contribuinte da nota", required = true, example = "{'id': '1', 'nome': 'Cliente A','documento':'2112312'}"  )
	@ApiModelProperty(value = "Id referente ao Contribuinte", required = true  )
	@NotNull
	private Long tomadorId;
	
	private String localPrestacao;
	
	private List<ItemNfse> itensNfse;
	
	private BigDecimal valorServico;

	public Long getPrestadorId() {
		return prestadorId;
	}

	public void setPrestadorId(Long prestadorId) {
		this.prestadorId = prestadorId;
	}
	
	public Long getTomadorId() {
		return tomadorId;
	}

	public void setTomadorId(Long tomadorId) {
		this.tomadorId = tomadorId;
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

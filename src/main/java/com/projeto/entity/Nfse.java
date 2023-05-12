package com.projeto.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.projeto.dto.NfseDTO;

import io.swagger.annotations.ApiModelProperty;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonInclude(content = Include.NON_NULL)
public class Nfse implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prestador_id")
	//@JsonSerialize(using = Contribuinte.class)
	private Contribuinte prestador;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tomador_id")
	private Contribuinte tomador;
	
	@NotNull
	@Column(name = "local_prestacao")
	private String localPrestacao;//criar objeto municipio
	
//	@Column(name="lista_items")
//	private String listaItens;//criar objeto de item de servico (descricao,quantidade,valor)
	
	//@NotBlank
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
	@JoinColumn(name = "nfse_id")
	private List<ItemNfse> itensNfse;
	
	@Column(name="valor_servico")
	private BigDecimal valorServico;
	
	public Nfse() {
	}
	
	public Nfse(NfseDTO dto) {
		prestador = new Contribuinte();
		//prestador.setId(dto.getPrestador().getId());
		tomador = new Contribuinte();
		tomador.setId(dto.getTomador().getId());
		localPrestacao = dto.getLocalPrestacao();
		itensNfse  = dto.getItensNfse();
	}

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

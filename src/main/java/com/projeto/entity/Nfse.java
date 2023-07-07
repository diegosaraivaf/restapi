package com.projeto.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.projeto.dto.NfseDTORequest;

@Entity
@JsonInclude(content = Include.NON_NULL)
public class Nfse implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prestador_id")
	private Contribuinte prestador;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tomador_id")
	private Contribuinte tomador;
	

	@Column(name = "local_prestacao")
	private String localPrestacao;//criar objeto municipio
	
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
	@JoinColumn(name = "nfse_id")
	private List<ItemNfse> itensNfse;
	
	@Column(name="valor_servico")
	private BigDecimal valorServico;
	
	@Column
	private LocalDate dataEmissao;
	
	@Column
	@Enumerated(EnumType.STRING)
	private SituacaoNfse situacaoNfse;
	
	public Nfse() {
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

	public LocalDate getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(LocalDate dataEmissao) {
		this.dataEmissao = dataEmissao;
	}
	
	public SituacaoNfse getSituacaoNfse() {
		return situacaoNfse;
	}

	public void setSituacaoNfse(SituacaoNfse situacaoNfse) {
		this.situacaoNfse = situacaoNfse;
	}

	public void convertFromDTO(NfseDTORequest dto) {
		if(dto.getPrestadorId() != null) {
			prestador = new Contribuinte();
			prestador.setId(dto.getPrestadorId());
		}
		if(dto.getTomadorId() != null) {
			tomador = new Contribuinte();
			tomador.setId(dto.getTomadorId());
		}
		localPrestacao = dto.getLocalPrestacao();
		valorServico = dto.getValorServico();
		if(itensNfse ==null) {
			itensNfse = new ArrayList<>();
		}
		itensNfse.clear();
		itensNfse.addAll(dto.getItensNfse());	
	}
	
}

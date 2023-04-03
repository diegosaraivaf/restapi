package com.projeto.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Nfse implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "prestador_id")
	private Contribuinte prestador;
	
	@ManyToOne
	@JoinColumn(name = "tomador_id")
	private Contribuinte tomador;
	
	@Column(name = "local_prestacao")
	private String localPrestacao;//criar objeto municipio
	
//	@Column(name="lista_items")
//	private String listaItens;//criar objeto de item de servico (descricao,quantidade,valor)
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "nfse_id")
	private List<ItemNfse> itensNfse;
	
	@Column(name="valor_servico")
	private BigDecimal valorServico;

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

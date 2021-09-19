package com.projeto.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Lancamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "tipo_lancamento")
	@Enumerated(EnumType.STRING)
	private TipoLancamento tipoLancamento;
	
	private BigDecimal valor;
	
	private Date dataEmissao;
	
	@Column(name = "situacao_lancamento")
	@Enumerated(EnumType.STRING)
	private SituacaoLancamento situacaoLancamento;
	
	@OneToMany
	@JoinColumn(name = "lancamento_id")
	private List<Parcela> parcelas;
	
	@ManyToOne
	@JoinColumn(name = "contribuinte_id")
	private Contribuinte contribuinte;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoLancamento getTipoLancamento() {
		return tipoLancamento;
	}

	public void setTipoLancamento(TipoLancamento tipoLancamento) {
		this.tipoLancamento = tipoLancamento;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	
	public SituacaoLancamento getSituacaoLancamento() {
		return situacaoLancamento;
	}

	public void setSituacaoLancamento(SituacaoLancamento situacaoLancamento) {
		this.situacaoLancamento = situacaoLancamento;
	}

	public List<Parcela> getParcelas() {
		return parcelas;
	}

	public void setParcelas(List<Parcela> parcelas) {
		this.parcelas = parcelas;
	}
	
	public Contribuinte getContribuinte() {
		return contribuinte;
	}
	
	public void setContribuinte(Contribuinte contribuinte) {
		this.contribuinte = contribuinte;
	}
	
}

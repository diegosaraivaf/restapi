package com.projeto.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.projeto.entity.Contribuinte;
import com.projeto.entity.Parcela;
import com.projeto.entity.SituacaoLancamento;
import com.projeto.entity.TipoLancamento;

public class LancamentoDTO {
	
	private Long id;
	
	private TipoLancamento tipoLancamento;
	
	@NotNull
	private BigDecimal valor;
	
	private Date dataEmissao;
	
	private SituacaoLancamento situacaoLancamento;
	
	@NotNull
	private List<Parcela> parcelas;
	
	@NotNull
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

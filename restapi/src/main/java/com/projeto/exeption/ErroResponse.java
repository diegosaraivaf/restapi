package com.projeto.exeption;

public class ErroResponse {
	private String erro;
	private String descricao;
	
	ErroResponse(String erro,String descricao){
		this.erro = erro;
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public String getErro() {
		return erro;
	}
}

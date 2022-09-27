package com.projeto.entity;

import java.math.BigDecimal;
import java.util.List;

public class Nfse {
	private Long id;
	private Contribuinte prestador;
	private Contribuinte tomador;
	private String local_prestacao;//criar objeto municipio
	private List<String> listaItemns;//criar objeto de item de servico (descricao,quantidade,valor)
	private BigDecimal valorservico;
}

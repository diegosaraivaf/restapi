package com.projeto.service;

import org.springframework.stereotype.Service;

import com.projeto.entity.Pessoa;

@Service
public class PessoaService {

	public Pessoa salvar(Pessoa pessoa) {
		return pessoa;
	}
}

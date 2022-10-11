package com.projeto.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.projeto.dto.LancamentoDTO;
import com.projeto.entity.Contribuinte;
import com.projeto.entity.Lancamento;

@RestController
@RequestMapping("testes")
public class TesteController {
	
	/**
	 * Teste de validacao com bean validator
	 * @Valid para ativar a execulcao da validacao e 
	 * @NotNull para adicionar restrincoes na entidade  
	 * */
	@PostMapping
	public Lancamento salvar(@RequestBody @Valid LancamentoDTO lancamentoDTO) {
		return null;
	}
	
	@PostMapping("/upload")
	public void method_name(@RequestPart("file") MultipartFile obj,@RequestPart("contribuinte") Contribuinte contribuinte) throws IOException {
		
		obj.transferTo(new File("C:\\Users\\Diego Ferreira\\Pictures\\Saved Pictures\\img.png"));
		
	}
	

}

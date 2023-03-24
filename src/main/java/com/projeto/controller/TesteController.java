package com.projeto.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.projeto.dto.LancamentoDTO;
import com.projeto.entity.Contribuinte;
import com.projeto.entity.Lancamento;
import com.projeto.entity.Parcela;
import com.projeto.entity.SituacaoLancamento;
import com.projeto.entity.TipoLancamento;
import com.projeto.repository.LancamentoRepository;
import com.projeto.repository.ParcelaRepository;
import com.projeto.service.LancamentoService;

@RestController
@RequestMapping("testes")
public class TesteController {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private ParcelaRepository parcelaRepository;
	
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
	

//	@RequestMapping(path = "/download", method = RequestMethod.GET)
//	public ResponseEntity<Resource> download(String param) throws IOException {
//
//	    // ...
//		File file = new File("C:\\Users\\Diego Ferreira\\Pictures\\Saved Pictures\\img.png");
//	    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//
//	    return ResponseEntity.ok()
//	            .headers(null)
//	            .contentLength(file.length())
//	            .contentType(MediaType.APPLICATION_OCTET_STREAM)
//	            .body(resource);
//	}
	
	@GetMapping(
			  value = "/download",
			  produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
			)
			public @ResponseBody byte[] getFile() throws IOException {
			    InputStream in = getClass()
			      .getResourceAsStream("C:/Users/Diego Ferreira/Pictures/Saved Pictures/img.png");
		
			    File file = new File("C:\\Users\\Diego Ferreira\\Pictures\\Saved Pictures\\img.png");
			    byte[] bytes = new byte[(int) file.length()];
			    FileInputStream  fis = new FileInputStream(file);
			    fis.read(bytes);
			    
			    return bytes;
			}
	
	@GetMapping("/download/{fileName:.+}")
	public ResponseEntity downloadFileFromLocal(@PathVariable String fileName) {
		Path path = Paths.get("C:/Users/Diego Ferreira/Pictures/Saved Pictures/img.png");
		Resource resource = null;
		try {
			resource = new UrlResource(path.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	
	@GetMapping("/teste")
	public void teste() throws IOException {
		Contribuinte c = new Contribuinte();
		c.setId(1L);
		
		Parcela p1 = new Parcela();
		p1.setId(1L);
		p1.setDataVencimento(LocalDate.now());
		p1.setSituacao("EMITIDA");
		p1.setValor(new BigDecimal("500"));
		
		
		Parcela p2 = new Parcela();
		p2.setId(2L);
		p2.setDataVencimento(LocalDate.now());
		p2.setSituacao("EMITIDA");
		p2.setValor(new BigDecimal("500"));
		
		List<Parcela> parcelas = Arrays.asList(p1);
		
		Lancamento l = new Lancamento();
		l.setId(1L);
		l.setContribuinte(c);
		l.setDataEmissao(new Date());
		l.setParcelas(parcelas);
		l.setSituacaoLancamento(SituacaoLancamento.EMITIDO);
		l.setTipoLancamento(TipoLancamento.ISS);
		l.setValor(new BigDecimal("1000"));
		
//		parcelaRepository.save(p1);
//		parcelaRepository.save(p2);
		lancamentoRepository.save(l);
	}
	
	
	@GetMapping("/teste2")
	public ResponseEntity teste2() throws InterruptedException {
		System.out.println("teste2");
		//Thread.sleep(10000);
		
		return ResponseEntity.ok().body("blz 2");
	}
	
	@GetMapping("/teste3")
	public ResponseEntity teste3() throws InterruptedException {
		System.out.println("teste3");
		//Thread.sleep(10000);
		
		return ResponseEntity.ok().body("blz 3");
	}
	
	
	

}

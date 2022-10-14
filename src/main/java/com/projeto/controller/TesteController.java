package com.projeto.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.swing.text.Document;
import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.InputStreamResource;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
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
	

}

package com.projeto.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

@Service
public class EmailService {
	
	@Value("${spring.mail.username}")
	private String remetente;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private Configuration fmConfiguration;
	
	
	/**
	 * Envio de email com Layout 
	 */
	public void enviar(String destinatario,String titulo,String message) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
			mimeMessageHelper.setSubject(titulo);
//			mimeMessageHelper.setFrom(remetente);//informacao de remetente e chave de acesso ficam no application.properties
			mimeMessageHelper.setTo(destinatario);
			
			
			Map<String, Object> parametros = new HashMap<>();
			parametros.put("nome","João");
			parametros.put("mensagem",message);
			
			mimeMessageHelper.setText(getConteudoTemplate(parametros),true);
			javaMailSender.send(mimeMessageHelper.getMimeMessage());
		} 
		catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	private String getConteudoTemplate(Map<String, Object> parametros) {
		try {
			StringBuffer content = new StringBuffer();
			content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("template1.flth"), parametros));
			return content.toString();
		} 
		catch (IOException | TemplateException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Envio de email simples 
	 */
	public void enviarEmailSimples() {
		String destinatario = "diegosaraivaferreira@gmail.com";
		String titulo = "titulo teste";
		String message = "mensagem teste";
		
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(remetente);
		simpleMailMessage.setTo(destinatario);
		simpleMailMessage.setSubject(titulo);
		simpleMailMessage.setText(message);
		javaMailSender.send(simpleMailMessage);
	}
}

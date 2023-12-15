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
	
	private Configuration fmConfiguration;
	
	
	
	public void enviar(String destinatario,String titulo, String message) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(remetente);
		simpleMailMessage.setTo(destinatario);
		simpleMailMessage.setSubject(titulo);
		simpleMailMessage.setText(message);
		javaMailSender.send(simpleMailMessage);
	}
	
	public void enviar() {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
			mimeMessageHelper.setSubject("titulo");
			mimeMessageHelper.setFrom("email@gmail.com");
			mimeMessageHelper.setTo("email@gmail.com");
			
			
			Map<String, Object> propsMap = new HashMap<>();
			propsMap.put("nome","teste");
			propsMap.put("mensagem","mensagem teste 234");
			
			mimeMessageHelper.setText(getConteudoTemplate(propsMap),true);
			javaMailSender.send(mimeMessageHelper.getMimeMessage());
		} 
		catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public String getConteudoTemplate(Map<String, Object> model) {
		try {
			StringBuffer content = new StringBuffer();
			content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("template1.flth"), model));
			return content.toString();
		} 
		catch (IOException | TemplateException e) {
			e.printStackTrace();
		}
		return null;
	}
}

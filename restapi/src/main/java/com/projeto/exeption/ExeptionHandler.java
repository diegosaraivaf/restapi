package com.projeto.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.QueryException;

@ControllerAdvice
public class ExeptionHandler 
//extends ResponseEntityExceptionHandler
{
	
//Handler retirado pois nao estava deixando os hadles especificos tratarem a execao
//	@ExceptionHandler(Exception.class) 
//	public ResponseEntity<?>handlerExeption(Exception e) {
//		ErroResponse erro = new ErroResponse("Erro desconhecido ", e.getMessage());
//		e.printStackTrace(); //new HttpRequestMethodNotSupportedException(null);
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro); 
//	}
	
	@ExceptionHandler(QueryException.class) 
	public ResponseEntity<?>handlerQueryException(QueryException e) {
//		ErroResponse erro = new ErroResponse("Erro na consulta", e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); 
	}
	
	@ExceptionHandler(IllegalArgumentException.class) 
	public ResponseEntity<?>handlerIllegalArgumentException(IllegalArgumentException e) {
		//ErroResponse erro = new ErroResponse("Erro na consulta", e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); 
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> handlerNullPointerException(NullPointerException e) {
		ErroResponse erro = new ErroResponse("null pointer", e.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> handlerHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		Throwable rootCause = ExceptionUtils.getRootCause(e);
		ErroResponse erro = null;
		
		if(rootCause instanceof InvalidFormatException) {
			InvalidFormatException ex = ((InvalidFormatException)rootCause);
			String campoErro = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
			
			
			String mensagem = String.format("Campo '%s' valor '%s' e invalido entre com um capo do tipo '%s' . ", 
					campoErro,
					ex.getValue().toString(),
					ex.getTargetType().getName());
			erro = new ErroResponse("null pointer", mensagem);
		}
		else if(rootCause instanceof MismatchedInputException) {
			MismatchedInputException ex = ((MismatchedInputException)rootCause);
			erro = new ErroResponse("Campo invalido", ex.getMessage());
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {		
		List<FieldError> erros = e.getBindingResult().getFieldErrors();
		List<String> result = new ArrayList<>();
		
		for(FieldError fe : erros) {
			String mensagem = String.format("Campo '%s' %s. ", 
					fe.getField(),
					fe.getDefaultMessage());
			result.add(mensagem);
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handlerNegocioExeption(NegocioException ex){
		ErroResponse erro = new ErroResponse("erro de negocio", ex.getMessage());
		
		return ResponseEntity.status(ex.getHttpStatus()).body(erro);
	}
}

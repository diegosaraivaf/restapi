package com.projeto.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;

@ControllerAdvice
public class ExeptionHandler 
//extends ResponseEntityExceptionHandler
{
	
	/*Handler retirado pois esta capturando os erros 500 e retornado 400, assim mascarando o erro
	 * 
	 * @ExceptionHandler(Exception.class) public ResponseEntity<?>
	 * handlerExeption(Exception e) {
	 * 
	 * ErroResponse erro = new ErroResponse("teste", e.getMessage());
	 * e.printStackTrace(); //new HttpRequestMethodNotSupportedException(null);
	 * return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro); }
	 */
	
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
		String mensagem = String.format("Campo '%s' %s. ", 
				e.getBindingResult().getFieldError().getField(),
				e.getBindingResult().getFieldError().getDefaultMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagem);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handlerNegocioExeption(NegocioException ex){
		ErroResponse erro = new ErroResponse("erro de negocio", ex.getMessage());
		
		return ResponseEntity.status(ex.getHttpStatus()).body(erro);
	}
}

package com.projeto.exeption;

public class NegocioException extends Exception{
	private int httpStatus;
	
	public final static int BADREQUEST = 400;
	public final static int NOTFOUND = 404;
	
	public NegocioException(String mensagem,int httpStatus){
		super(mensagem);
		this.httpStatus = httpStatus; 
	}
	
	public NegocioException(String mensagem){
		super(mensagem);
		this.httpStatus = BADREQUEST; 
	}
	
	public int getHttpStatus() {
		return httpStatus;
	}
}

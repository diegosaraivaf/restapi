package com.projeto.exeption;

public class NegocioExeption extends Exception{
	private int httpStatus;
	
	public final static int BADREQUEST = 400;
	public final static int NOTFOUND = 404;
	
	public NegocioExeption(String mensagem,int httpStatus){
		super(mensagem);
		this.httpStatus = httpStatus; 
	}
	
	public int getHttpStatus() {
		return httpStatus;
	}
}

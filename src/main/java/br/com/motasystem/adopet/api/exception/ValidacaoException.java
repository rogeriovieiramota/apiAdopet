package br.com.motasystem.adopet.api.exception;

public class ValidacaoException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public ValidacaoException(String message) {
		super(message);
	}		
}

package com.mariathecharmix.sd.RegistroUsuario.exceptions;

public class CustomeFieldValidationException extends Exception{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6000581617868820316L;
	private String fieldName;
	
	public CustomeFieldValidationException(String message, String fieldName) {
		super(message);
		this.fieldName = fieldName;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	
}

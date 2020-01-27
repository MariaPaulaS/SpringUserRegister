package com.mariathecharmix.sd.RegistroUsuario.exceptions;

public class UsernameOrIdNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4681604493821832953L;

	public UsernameOrIdNotFoundException() {
		super("Usuario o id no encontrado.");
	}
	
	public UsernameOrIdNotFoundException(String message) {
		super(message);

	}
	
}

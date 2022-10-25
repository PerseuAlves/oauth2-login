package io.github.PerseuAlves.oauth2login.exception;

public class IllegalArgumentException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public IllegalArgumentException() {
		super("Invalid argument");
	}
}

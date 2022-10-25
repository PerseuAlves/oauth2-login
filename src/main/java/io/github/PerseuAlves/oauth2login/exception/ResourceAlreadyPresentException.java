package io.github.PerseuAlves.oauth2login.exception;

public class ResourceAlreadyPresentException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ResourceAlreadyPresentException(String msg) {
		super(msg);
	}
}

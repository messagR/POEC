package fr.exceptions;

public class PersonneException extends Exception {

	private static final long serialVersionUID = -8970323031815306689L;

	public PersonneException() {
	}

	public PersonneException(String message) {
		super(message);
	}

	public PersonneException(Throwable cause) {
		super(cause);
	}

	public PersonneException(String message, Throwable cause) {
		super(message, cause);
	}

	public PersonneException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

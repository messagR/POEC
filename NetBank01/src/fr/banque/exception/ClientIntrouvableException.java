package fr.banque.exception;

@SuppressWarnings("serial")
public class ClientIntrouvableException extends Exception {

	public ClientIntrouvableException() {
	}

	public ClientIntrouvableException(String message) {
		super(message);
	}

	public ClientIntrouvableException(Throwable cause) {
		super(cause);
	}

	public ClientIntrouvableException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClientIntrouvableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

package fr.banque.exception;

@SuppressWarnings("serial")
public class CompteIntrouvableException extends Exception {

	public CompteIntrouvableException() {
	}

	public CompteIntrouvableException(String message) {
		super(message);
	}

	public CompteIntrouvableException(Throwable cause) {
		super(cause);
	}

	public CompteIntrouvableException(String message, Throwable cause) {
		super(message, cause);
	}

	public CompteIntrouvableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

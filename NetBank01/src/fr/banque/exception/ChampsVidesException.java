package fr.banque.exception;

@SuppressWarnings("serial")
public class ChampsVidesException extends Exception {

	public ChampsVidesException() {
	}

	public ChampsVidesException(String message) {
		super(message);
	}

	public ChampsVidesException(Throwable cause) {
		super(cause);
	}

	public ChampsVidesException(String message, Throwable cause) {
		super(message, cause);
	}

	public ChampsVidesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

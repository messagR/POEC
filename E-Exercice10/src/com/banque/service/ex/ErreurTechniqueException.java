/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.service.ex;

/**
 * Erreur tehcnique.
 */
public class ErreurTechniqueException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur de l'objet.
	 */
	public ErreurTechniqueException() {
		super();
	}

	/**
	 * Constructeur de l'objet.
	 *
	 * @param pMessage
	 */
	public ErreurTechniqueException(String pMessage) {
		super(pMessage);
	}

	/**
	 * Constructeur de l'objet.
	 *
	 * @param pCause
	 */
	public ErreurTechniqueException(Throwable pCause) {
		super(pCause);
	}

	/**
	 * Constructeur de l'objet.
	 *
	 * @param pMessage
	 * @param pCause
	 */
	public ErreurTechniqueException(String pMessage, Throwable pCause) {
		super(pMessage, pCause);
	}

}

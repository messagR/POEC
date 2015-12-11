/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.dao.ex;

/**
 * Erreur propre au DAO.
 */
public class ExceptionDao extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur de l'objet.
	 */
	public ExceptionDao() {
		super();
	}

	/**
	 * Constructeur de l'objet.
	 *
	 * @param pMessage
	 */
	public ExceptionDao(String pMessage) {
		super(pMessage);
	}

	/**
	 * Constructeur de l'objet.
	 *
	 * @param pCause
	 */
	public ExceptionDao(Throwable pCause) {
		super(pCause);
	}

	/**
	 * Constructeur de l'objet.
	 *
	 * @param pMessage
	 * @param pCause
	 */
	public ExceptionDao(String pMessage, Throwable pCause) {
		super(pMessage, pCause);
	}

}

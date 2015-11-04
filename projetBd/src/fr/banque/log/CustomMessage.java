/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package fr.banque.log;

import java.text.MessageFormat;

import org.apache.logging.log4j.message.Message;

/**
 * Exemple de Message en Log4J2.
 */
public class CustomMessage implements Message {
	private static final long serialVersionUID = 1L;

	public enum TypeCible {
		COMPTE, CLIENT;
	}

	private int numero;
	private String message;
	private TypeCible type;
	private Throwable erreur;

	/**
	 * Constructeur.
	 */
	public CustomMessage() {
		super();
	}

	/**
	 * Constructeur.
	 *
	 * @param uneCible
	 *            la cible (un compte ou un client)
	 * @param unNumero
	 *            le numero du compte ou du client
	 * @param unMessage
	 *            le message d'erreur
	 * @param uneErreur
	 *            l'erreur (si besoin)
	 */
	public CustomMessage(TypeCible uneCible, int unNumero, String unMessage, Throwable uneErreur) {
		super();
		this.type = uneCible;
		this.numero = unNumero;
		this.message = unMessage;
		this.erreur = uneErreur;
	}

	@Override
	public String getFormat() {
		if (this.erreur == null) {
			return "{0} n°{1} indique l''information : {2}";
		}
		return "{0} n°{1} indique l''information : {2} - Avec l''erreur : {3}";
	}

	@Override
	public String getFormattedMessage() {
		MessageFormat mf = new MessageFormat(this.getFormat());
		return mf.format(this.getParameters());
	}

	@Override
	public Object[] getParameters() {
		Object[] resultat = (this.erreur == null) ? new Object[3] : new Object[4];
		if (this.type == TypeCible.COMPTE) {
			resultat[0] = "Le compte";
		}
		if (this.type == TypeCible.CLIENT) {
			resultat[1] = "Le client";
		}
		resultat[1] = this.numero;
		resultat[2] = this.message;
		if (this.erreur != null) {
			resultat[3] = this.erreur.getMessage();
		}
		return resultat;
	}

	@Override
	public Throwable getThrowable() {
		return this.erreur;
	}

}

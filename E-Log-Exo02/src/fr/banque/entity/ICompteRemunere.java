/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package fr.banque.entity;

import java.io.Serializable;

/**
 * Interface representant un compte remunere.
 */
public interface ICompteRemunere extends Serializable {

	/**
	 * Calcul les interets par rapport au solde actuel.
	 *
	 * @return les interets par rapport au solde actuel.
	 */
	public abstract double calculerInterets();

	/**
	 * Verse les interets par rapport au solde actuel.
	 */
	public abstract void verserInterets();

	/**
	 * Recupere le taux.
	 *
	 * @return le taux
	 */
	public abstract double getTaux();

	/**
	 * Modifie la valeur du taux.
	 *
	 * @param taux
	 *            le nouveau taux, entre [0, 1[.
	 * @throws IllegalArgumentException
	 *             si le taux est invalid
	 */
	public abstract void setTaux(double taux);

}
/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package fr.banque;

import java.io.Serializable;

/**
 * Interface representant un compte a seuil.
 */
public interface ICompteASeuil extends Serializable {

	/**
	 * Retire l'argent si c'est possible.
	 *
	 * @param unMontant
	 *            un montant a retirer
	 */
	public abstract void retirer(double unMontant) throws BanqueException;

	/**
	 * Recupere le seuil.
	 *
	 * @return le seuil
	 */
	public abstract double getSeuil();

	/**
	 * Modifie la valeur du seuil.
	 *
	 * @param seuil
	 *            le nouveau seuil
	 */
	public abstract void setSeuil(double unSeuil);

}
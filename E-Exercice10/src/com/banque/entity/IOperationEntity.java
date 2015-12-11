/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.entity;

import java.sql.Timestamp;

/**
 * Represente une operation
 */
public interface IOperationEntity extends IEntity {

	/**
	 * Recupere la propriete <i>compteId</i>.
	 *
	 * @return the compteId la valeur de la propriete.
	 */
	public abstract Integer getCompteId();

	/**
	 * Fixe la propriete <i>compteId</i>.
	 *
	 * @param pCompteId
	 *            la nouvelle valeur pour la propriete compteId.
	 */
	public abstract void setCompteId(Integer pCompteId);

	/**
	 * Recupere la date de creation de l'operation
	 *
	 * @return la date de creation de l'operation
	 */
	public abstract Timestamp getDate();

	/**
	 * Recupere le libelle de l'operation
	 *
	 * @return le libelle de l'operation
	 */
	public abstract String getLibelle();

	/**
	 * Recupere le montant de l'operation
	 *
	 * @return le montant de l'operation
	 */
	public abstract Double getMontant();

	/**
	 * Fixe la date de creation de l'operation
	 *
	 * @param uneDate
	 *            la date de creation de l'operation
	 */
	public abstract void setDate(Timestamp uneDate);

	/**
	 * Fixe le libelle de l'operation
	 *
	 * @param unLibelle
	 *            le libelle de l'operation
	 */
	public abstract void setLibelle(String unLibelle);

	/**
	 * Fixe le montant de l'operation
	 *
	 * @param unMontant
	 *            le montant de l'operation
	 */
	public abstract void setMontant(Double unMontant);

}
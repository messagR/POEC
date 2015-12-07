/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package fr.banque;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Classe representant une operation.
 */
public class Operation implements Serializable {
	private static final long serialVersionUID = 1L;

	private int numero;
	private String libelle;
	private double montant;
	private Timestamp date;

	private int compteId;

	/**
	 * Constructeur. <br/>
	 * Id aura une valeur de -1 par defaut.
	 */
	public Operation() {
		this(-1, null, 0, null, -1);
	}

	/**
	 * Constructeur.
	 *
	 * @param unNumero
	 *            un numero d'operation
	 * @param unLibelle
	 *            un libelle
	 * @param unMontant
	 *            un montant
	 * @param uneDate
	 *            une date
	 * @param unCompteId
	 *            un id de compte
	 *
	 */
	public Operation(int unNumero, String unLibelle, double unMontant, Timestamp uneDate, int unCompteId) {
		super();
		this.setNumero(unNumero);
		this.setLibelle(unLibelle);
		this.setMontant(unMontant);
		this.setDate(uneDate);
		this.setCompteId(unCompteId);
	}

	/**
	 * Recupere la valeur de l'attribut. <br/>
	 *
	 * @return la veleur de numero
	 */
	public int getNumero() {
		return this.numero;
	}

	/**
	 * Modifie la valeur de l'attribut. <br/>
	 *
	 * @param numero
	 *            la nouvelle valeur de numero
	 */
	public void setNumero(int numero) {
		this.numero = numero;
	}

	/**
	 * Recupere la valeur de l'attribut. <br/>
	 *
	 * @return la veleur de libelle
	 */
	public String getLibelle() {
		return this.libelle;
	}

	/**
	 * Modifie la valeur de l'attribut. <br/>
	 *
	 * @param libelle
	 *            la nouvelle valeur de libelle
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	/**
	 * Recupere la valeur de l'attribut. <br/>
	 *
	 * @return la veleur de montant
	 */
	public double getMontant() {
		return this.montant;
	}

	/**
	 * Modifie la valeur de l'attribut. <br/>
	 *
	 * @param montant
	 *            la nouvelle valeur de montant
	 */
	public void setMontant(double montant) {
		this.montant = montant;
	}

	/**
	 * Recupere la valeur de l'attribut. <br/>
	 *
	 * @return la veleur de date
	 */
	public Timestamp getDate() {
		return this.date;
	}

	/**
	 * Modifie la valeur de l'attribut. <br/>
	 *
	 * @param date
	 *            la nouvelle valeur de date
	 */
	public void setDate(Timestamp date) {
		this.date = date;
	}

	/**
	 * Recupere la valeur de l'attribut. <br/>
	 *
	 * @return la veleur de compteId
	 */
	public int getCompteId() {
		return this.compteId;
	}

	/**
	 * Modifie la valeur de l'attribut. <br/>
	 *
	 * @param compteId
	 *            la nouvelle valeur de compteId
	 */
	public void setCompteId(int compteId) {
		this.compteId = compteId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getName());
		builder.append(" [numero=");
		builder.append(this.getNumero());
		builder.append(", libelle=");
		builder.append(this.getLibelle());
		builder.append(", montant=");
		builder.append(this.getMontant());
		builder.append(", date=");
		builder.append(this.getDate());
		builder.append(", compteId=");
		builder.append(this.getCompteId());
		builder.append("]");
		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj instanceof Operation) {
			Operation c = (Operation) obj;
			return this.getNumero() == c.getNumero();
		}
		return false;
	}
}

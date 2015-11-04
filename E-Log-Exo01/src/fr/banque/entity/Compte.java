/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package fr.banque.entity;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.banque.exception.BanqueException;

/**
 * Ceci est la classe Compte. <br>
 */
public class Compte implements Serializable {
	private static final long serialVersionUID = 1L;
	private final static Log LOG = LogFactory.getLog(Compte.class);

	private double solde;
	private int numero;
	private String libelle;

	/**
	 * Constructeur de l'objet. <br>
	 * Le numero par defaut sera -1.
	 */
	public Compte() {
		this(-1, 0d);
	}

	/**
	 * Constructeur de l'objet. <br>
	 *
	 * @param unNumero
	 *            le numero du compte
	 * @param unSoldeInitial
	 *            le solde initial du compte
	 */
	public Compte(int unNumero, double unSoldeInitial) {
		super();
		// On a choisit de ne pas faire de methode setNumero
		this.numero = unNumero;
		this.setSolde(unSoldeInitial);
	}

	/**
	 * Donne acces au solde du compte. <br>
	 *
	 * @return le solde du compte
	 */
	public double getSolde() {
		return this.solde;
	}

	/**
	 * Fixe le solde du compte. <br>
	 *
	 * @param unSolde
	 *            le nouveau solde du compte
	 */
	public void setSolde(double unSolde) {
		this.solde = unSolde;
		if (Compte.LOG.isDebugEnabled()) {
			Compte.LOG.debug("Le solde est negatif " + this.solde);
		}
	}

	/**
	 * Donne acces au numero du compte. <br>
	 *
	 * @return le numero du compte
	 */
	public int getNumero() {
		return this.numero;
	}

	/**
	 * Ajoute un montant au compte. <br>
	 *
	 * @param unMontant
	 *            le montant ajoute au compte
	 */
	public void ajouter(double unMontant) {
		this.setSolde(this.getSolde() + unMontant);
	}

	/**
	 * Retire un montant du compte. <br>
	 *
	 * @param unMontant
	 *            le montant retire du compte
	 * @throws BanqueException
	 *             si un probleme survient
	 */
	public void retirer(double unMontant) throws BanqueException {
		this.setSolde(this.getSolde() - unMontant);
	}

	/**
	 * Formatage du compte sous forme de String utilisable directement par
	 * System.out.println(..);. <br>
	 *
	 * @return une representation chainee de l'objet
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(128);
		sb.append(this.getClass().getName());
		sb.append(" N°=");
		sb.append(this.getNumero());
		sb.append(" Solde=");
		sb.append(this.getSolde());
		sb.append(" Libelle=");
		sb.append(this.getLibelle());
		return sb.toString();
	}

	/**
	 * Recupere le libelle du compte.
	 *
	 * @return le libelle du compte.
	 */
	public String getLibelle() {
		return this.libelle;
	}

	/**
	 * Modifie le libelle du compte.
	 *
	 * @param aLibelle
	 *            le libelle du compte.
	 */
	public void setLibelle(String aLibelle) {
		this.libelle = aLibelle;
	}

	/**
	 * Indique si deux comptes sont egaux. <br>
	 *
	 * Deux comptes sont egaux si ils ont le meme numero d'identification.
	 *
	 * @param obj
	 *            l'objet qui sera compare a this
	 * @return <code>true</code> si les deux sont egaux et <code>false</code>
	 *         sinon.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj instanceof Compte) {
			Compte c = (Compte) obj;
			return this.getNumero() == c.getNumero();
		}
		return false;
	}
}

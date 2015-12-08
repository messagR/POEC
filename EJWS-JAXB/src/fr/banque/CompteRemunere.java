/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package fr.banque;

/**
 * Ceci est la classe Compte remunere. <br>
 */
public class CompteRemunere extends Compte implements ICompteRemunere {
	private static final long serialVersionUID = 1L;

	private double taux;

	/**
	 * Constructeur.
	 */
	public CompteRemunere() {
		this(-1, 0, 0);
	}

	/**
	 * Constructeur de l'objet. <br>
	 *
	 * @param unNumero
	 *            le numero du compte
	 * @param unSoldeInitial
	 *            le solde initial du compte
	 * @param unTaux
	 *            un taux entre [0, 1[
	 */
	public CompteRemunere(int unNumero, double unSoldeInitial, double unTaux) {
		super(unNumero, unSoldeInitial);
		this.setTaux(unTaux);
	}

	@Override
	public double getTaux() {
		return this.taux;
	}

	@Override
	public void setTaux(double taux) {
		if ((taux < 0) || (taux >= 1)) {
			throw new IllegalArgumentException("Le taux doit etre entre [0, 1[");
		}
		this.taux = taux;
	}

	@Override
	public double calculerInterets() {
		return super.getSolde() * this.getTaux();
	}

	@Override
	public void verserInterets() {
		super.ajouter(this.calculerInterets());
	}

	@Override
	public String toString() {
		return super.toString() + " Taux=" + this.getTaux();
	}

}

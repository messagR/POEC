package fr.banque;

public class Compte {


	private int numCompte;
	private double solde;

	public Compte(int numCompte, int solde) {
		this.setNumCompte(numCompte);
		this.setSolde(solde);

	}

	/**
	 * @return the numero
	 */
	public int getNumCompte() {
		return this.numCompte;
	}

	/**
	 * @param numCompte the numero to set
	 */
	// Faut-il rendre setNumero(unNumero) public ? oui
	public void setNumCompte(int numCompte) {
		this.numCompte = numCompte;
	}

	/**
	 * @return the solde
	 */
	public double getSolde() {
		return this.solde;
	}

	/**
	 * @param solde the solde to set
	 */
	// Faut-il rendre setSolde(unSolde) public ? non
	private void setSolde(double solde) {
		this.solde = solde;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getName());
		builder.append(" [numCompte = ").append(this.getNumCompte());
		builder.append(", solde = ").append(this.getSolde()).append("]");
		return builder.toString();
	}

	public void ajouter(double unMontant) {
		this.setSolde(this.getSolde() + unMontant);
	}

	public void retirer(double unMontant) {
		this.setSolde(this.getSolde() - unMontant);
	}

}

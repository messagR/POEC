package fr.banque;

public class Compte extends Entite {

	private double solde;

	public Compte() {
		this(0);
	}

	public Compte(int solde) {
		this.setSolde(solde);

	}

	/**
	 * @return the solde
	 */
	public double getSolde() {
		return this.solde;
	}

	/**
	 * @param solde
	 *            the solde to set
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
		builder.append(super.toString());
		builder.delete(builder.length() - 1, builder.length());
		builder.append(", solde = ").append(this.getSolde()).append("]");
		return builder.toString();
	}

	public void ajouter(double unMontant) {
		this.setSolde(this.getSolde() + unMontant);
	}

	public void retirer(double unMontant) {
		this.setSolde(this.getSolde() - unMontant);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	// @Override
	// public int hashCode() {
	// if (this.getNumero() == -1) {
	// return super.hashCode();
	// }
	// return (this.getClass().getName() + "_" + this.getNumero()).hashCode();
	// }

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof Compte) {
			return super.equals(obj);
		}
		return false;
	}

}

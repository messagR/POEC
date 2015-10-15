package fr.banque;

/**
 * visu package pour la factory
 */
class Compte extends Entite implements ICompte {

	private double solde;

	/**
	 * visu package pour la factory
	 */
	Compte() {
		this(0);
	}

	/**
	 * visu package pour la factory
	 */
	Compte(double solde) {
		this.setSolde(solde);

	}

	@Override
	public double getSolde() {
		return this.solde;
	}

	private void setSolde(double solde) {
		this.solde = solde;
	}

	@Override
	public void ajouter(double unMontant) {
		this.setSolde(this.getSolde() + unMontant);
	}

	@Override
	public void retirer(double unMontant) throws BanqueException {
		this.setSolde(this.getSolde() - unMontant);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.delete(builder.length() - 1, builder.length());
		builder.append(", solde = ").append(this.getSolde()).append("]");
		return builder.toString();
	}

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

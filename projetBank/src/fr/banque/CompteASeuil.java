package fr.banque;

public class CompteASeuil extends Compte {

	private double seuil;

	public CompteASeuil() {
		super();
	}

	public CompteASeuil(int solde) {
		super(solde);
	}

	/**
	 * @return the seuil
	 */
	public double getSeuil() {
		return this.seuil;
	}

	/**
	 * @param seuil
	 *            the seuil to set
	 */
	public void setSeuil(double seuil) {
		this.seuil = seuil;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.delete(builder.length() - 1, builder.length());
		builder.append(", seuil = ").append(this.getSeuil()).append("]");
		return builder.toString();

	}

	@Override
	public void retirer(double uneValeur) {
		if (this.getSeuil() <= (this.getSolde() - uneValeur)) {
			super.retirer(uneValeur);
		}
		else {
			System.out.println("Seuil atteint");
		}
	}
}

package fr.banque.entity;

import fr.banque.exception.BanqueException;

class CompteASeuilRemunere extends CompteRemunere implements ICompteASeuil, ICompteASeuilRemunere {
	private static final long serialVersionUID = 1L;

	private double seuil;

	public CompteASeuilRemunere() {
		this(-1, "", 0.0);
	}

	public CompteASeuilRemunere(int id, String libelle, double solde) {
		super(id, libelle, solde);
		this.setSeuil(0);
	}

	public CompteASeuilRemunere(int id, String libelle, double solde, double taux) {
		super(id, libelle, solde, taux);
		this.setSeuil(0);
	}

	public CompteASeuilRemunere(int id, String libelle, double solde, double seuil, double taux) {
		super(id, libelle, solde, taux);
		this.setSeuil(seuil);
	}

	@Override
	public double getSeuil() {
		return this.seuil;
	}

	@Override
	public void setSeuil(double unSeuil) {
		this.seuil = unSeuil;
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
	public void retirer(double uneValeur) throws BanqueException {
		if ((this.getSolde() - uneValeur) <= this.getSeuil()) {
			throw new BanqueException(
					"Votre seuil de " + this.getSeuil() + " ne vous permet pas de retirer "
							+ uneValeur + " de votre compte " + this.getNumero());
		} else {
			super.retirer(uneValeur);
		}
	}

}

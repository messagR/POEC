package fr.banque;

import fr.banque.exception.BanqueException;

class CompteASeuilRemunere extends CompteRemunere implements ICompteASeuil {

	private CompteASeuil compteASeuil;

	public CompteASeuilRemunere() {
		this(-1, "", 0.0);
	}

	public CompteASeuilRemunere(int id, String libelle, double solde) {
		super(id, libelle, solde);
	}

	public CompteASeuilRemunere(int id, String libelle, double solde, double taux) {
		super(id, libelle, solde, taux);
	}

	public CompteASeuilRemunere(int id, String libelle, double solde, double seuil, double taux) {
		super(id, libelle, solde, taux);
		this.compteASeuil = new CompteASeuil(id, libelle, solde, seuil);
	}

	@Override
	public double getSeuil() {
		return this.compteASeuil.getSeuil();
	}

	@Override
	public void setSeuil(double seuil) throws BanqueException {
		this.compteASeuil.setSeuil(seuil);
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
		// Il faut syncronizer les soldes
		this.compteASeuil.setSolde(this.getSolde());
		this.compteASeuil.retirer(uneValeur);
		this.setSolde(this.compteASeuil.getSolde());
	}

}

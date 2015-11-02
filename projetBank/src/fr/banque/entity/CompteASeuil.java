package fr.banque.entity;

import fr.banque.exception.BanqueException;

class CompteASeuil extends Compte implements ICompteASeuil {
	private static final long serialVersionUID = 1L;

	private double seuil;

	public CompteASeuil() {
		super();
	}

	public CompteASeuil(int id, String libelle, double solde) {
		super(id, libelle, solde);
	}

	public CompteASeuil(int id, String libelle, double solde, double seuil) {
		super(id, libelle, solde);
		try {
			this.setSeuil(seuil);
		} catch (BanqueException e) {
			try {
				this.setSeuil(0);
			} catch (BanqueException e1) {
				this.seuil = 0;
			}
		}
	}

	@Override
	public double getSeuil() {
		return this.seuil;
	}

	@Override
	public void setSeuil(double seuil) throws BanqueException {
		if (super.getSolde() < seuil) {
			throw new BanqueException(String.format(
					"Solde insuffisant : modification seuil impossible pour le compte n°{} avec un seuil de {}",
					this.getNumero(), seuil));
		} else {
			this.seuil = seuil;
		}
	}

	@Override
	public void retirer(double uneValeur) throws BanqueException {
		if (this.getSeuil() <= (this.getSolde() - uneValeur)) {
			super.retirer(uneValeur);
		}
		else {
			throw new BanqueException("Votre seuil de " + this.getSeuil() + " ne vous permet pas de retirer "
					+ uneValeur + " de votre compte " + this.getNumero());
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.delete(builder.length() - 1, builder.length());
		builder.append(", seuil = ").append(this.getSeuil()).append("]");
		return builder.toString();

	}
}

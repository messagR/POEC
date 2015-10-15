package fr.banque;

class CompteASeuil extends Compte implements ICompteASeuil {

	private double seuil;

	public CompteASeuil() {
		super();
	}

	public CompteASeuil(double solde) {
		super(solde);
	}

	public CompteASeuil(double solde, double seuil) {
		super(solde);
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
			throw new BanqueException(
					"Solde insuffisant : modification seuil impossible pour le compte n°" + this.getNumero()
					+ " avec un seuil de " + seuil);
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
			throw new BanqueException("Solde insuffisant après retrait : retrait impossible");
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

package fr.banque;

class CompteASeuilRemunere extends CompteASeuil implements ICompteRemunere, ICompteASeuilRemunere {

	private double taux;

	public CompteASeuilRemunere() {
		super();
	}

	public CompteASeuilRemunere(int id, String libelle, double solde) {
		super(id, libelle, solde);
	}

	public CompteASeuilRemunere(int id, String libelle, double solde, double seuil) {
		super(id, libelle, solde, seuil);
	}

	public CompteASeuilRemunere(int id, String libelle, double solde, double seuil, double taux) {
		super(id, libelle, solde, seuil);
		this.setTaux(taux);
	}

	@Override
	public double getTaux() {
		return this.taux;
	}

	@Override
	public void setTaux(double taux) {
		this.taux = taux;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.delete(builder.length() - 1, builder.length());
		builder.append(", taux = ").append(this.getTaux()).append("]");
		return builder.toString();

	}

	@Override
	public double calculerInterets() {
		// : qui va calculer les intérêts du compte (taux*solde)
		return this.getSolde() * this.taux;
	}

	@Override
	public String verserInterets() {
		this.ajouter(this.calculerInterets());
		return "versement de " + this.calculerInterets() + " d'interets";
	}

}

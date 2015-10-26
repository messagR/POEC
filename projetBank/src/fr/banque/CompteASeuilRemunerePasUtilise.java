package fr.banque;

class CompteASeuilRemunerePasUtilise extends CompteASeuil implements ICompteRemunere {

	private ICompteRemunere compteRemmunere;

	public CompteASeuilRemunerePasUtilise() {
		super();
	}

	public CompteASeuilRemunerePasUtilise(int id, String libelle, double solde) {
		super(id, libelle, solde);
	}

	public CompteASeuilRemunerePasUtilise(int id, String libelle, double solde, double seuil) {
		super(id, libelle, solde, seuil);
	}

	public CompteASeuilRemunerePasUtilise(int id, String libelle, double solde, double seuil, double taux) {
		super(id, libelle, solde, seuil);
		this.setTaux(taux);
	}

	@Override
	public double getTaux() {
		return this.compteRemmunere.getTaux();
	}

	@Override
	public void setTaux(double taux) {
		this.compteRemmunere.setTaux(taux);
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
		return this.getSolde() * this.compteRemmunere.getTaux();
	}

	@Override
	public String verserInterets() {
		this.ajouter(this.calculerInterets());
		return "versement de " + this.calculerInterets() + " d'interets";
	}

}

package fr.banque;

class CompteRemunere extends Compte implements ICompteRemunere {

	private double taux; // qui représente le pourcentage de rémunération du
	// valeur enter 0 et 1

	public CompteRemunere() {
		super();
	}

	public CompteRemunere(double solde) {
		super(solde);
	}

	public CompteRemunere(double solde, double taux) {
		super(solde);
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
	public double calculerInterets() {
		//: qui va calculer les intérêts du compte (taux*solde)
		return this.getSolde() * this.taux;
	}

	@Override
	public String verserInterets() {
		this.ajouter(this.calculerInterets());
		return "Versement de " + this.calculerInterets() + " d'interets";
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.delete(builder.length() - 1, builder.length());
		builder.append(", taux = ").append(this.getTaux()).append("]");
		return builder.toString();
	}
}

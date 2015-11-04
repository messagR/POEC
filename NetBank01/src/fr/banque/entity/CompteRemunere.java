package fr.banque.entity;

class CompteRemunere extends Compte implements ICompteRemunere {
	private static final long serialVersionUID = 1L;

	private double taux;
	// qui représente le pourcentage de rémunération du
	// valeur enter 0 et 1

	public CompteRemunere() {
		super();
	}

	public CompteRemunere(int id, String libelle, double solde) {
		super(id, libelle, solde);
	}

	public CompteRemunere(int id, String libelle, double solde, double taux) {
		super(id, libelle, solde);
		this.setTaux(taux);
	}

	@Override
	public double getTaux() {
		return this.taux;
	}

	@Override
	public void setTaux(double taux) {
		if ((taux < 0) || (taux >= 1)) {
			throw new IllegalArgumentException("Le taux doit etre entre [0, 1]");
		}
		this.taux = taux;
	}

	@Override
	public double calculerInterets() {
		return this.getSolde() * this.taux;
	}

	@Override
	public String verserInterets() {
		super.ajouter(this.calculerInterets());
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

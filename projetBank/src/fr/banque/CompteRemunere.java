package fr.banque;

public class CompteRemunere extends Compte {

	private double taux; // qui représente le pourcentage de rémunération du
	// valeur enter 0 et 1

	public CompteRemunere() {
		super();
	}

	public CompteRemunere(int solde) {
		super(solde);
	}

	/**
	 * @return the taux
	 */
	public double getTaux() {
		return this.taux;
	}

	/**
	 * @param taux
	 *            the taux to set
	 */
	public void setTaux(double taux) {
		this.taux = taux;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.delete(builder.length() - 1, builder.length());
		builder.append(", taux = ").append(this.getTaux()).append("]");
		return builder.toString();
	}

	public double calculerInterets(){
		//: qui va calculer les intérêts du compte (taux*solde)
		return this.getSolde() * this.taux;
	}

	public void verserInterets(){
		this.ajouter(this.calculerInterets());
	}
}

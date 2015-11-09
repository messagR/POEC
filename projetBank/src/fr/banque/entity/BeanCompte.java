package fr.banque.entity;

public class BeanCompte extends Compte {
	private static final long serialVersionUID = 1L;

	private Double seuil;
	private Double taux;

	public BeanCompte() {
		this(-1, "", 0.0);
	}

	public BeanCompte(int id, String libelle, double solde) {
		super(id, libelle, solde);
	}

	public BeanCompte(int id, String libelle, double solde, Double seuil, Double taux) {
		super(id, libelle, solde);
		this.setSeuil(seuil);
		this.setTaux(taux);
	}

	public Double getSeuil() {
		return this.seuil;
	}

	public void setSeuil(Double seuil) {
		this.seuil = seuil;
	}

	public Double getTaux() {
		return this.taux;
	}

	public void setTaux(Double taux) {
		this.taux = taux;
	}
}

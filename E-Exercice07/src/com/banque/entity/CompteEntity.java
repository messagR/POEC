/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.entity;

/**
 * Le bean qui represente un Compte. <br>
 */
public class CompteEntity extends AbstractEntity implements ICompteEntity {

	private static final long serialVersionUID = 1L;

	private String libelle;
	private Double solde;
	private Double decouvert;
	private Double taux;

	private Integer utilisateurId;

	/**
	 * Constructeur de l'objet. <br>
	 */
	public CompteEntity() {
		super();
	}

	/**
	 * Constructeur de l'objet. <br>
	 *
	 * @param unId
	 *            l'id d'un compte
	 * @param unLibelle
	 *            le libelle du compte
	 * @param unSolde
	 *            le solde du compte
	 * @param unDecouvert
	 *            le decouvert du compte
	 * @param unTaux
	 *            un taux
	 */
	public CompteEntity(Integer unId, String unLibelle, Double unSolde,
			Double unDecouvert, Double unTaux) {
		super(unId);
		this.setLibelle(unLibelle);
		this.setSolde(unSolde);
		this.setDecouvert(unDecouvert);
	}

	@Override
	public Double getTaux() {
		return this.taux;
	}

	@Override
	public void setTaux(Double taux) {
		this.taux = taux;
	}

	@Override
	public Integer getUtilisateurId() {
		return this.utilisateurId;
	}

	@Override
	public void setUtilisateurId(Integer pUtilisateurId) {
		this.utilisateurId = pUtilisateurId;
	}

	@Override
	public Double getDecouvert() {
		return this.decouvert;
	}

	@Override
	public String getLibelle() {
		return this.libelle;
	}

	@Override
	public Double getSolde() {
		return this.solde;
	}

	@Override
	public void setDecouvert(Double unDecouvert) {
		this.decouvert = unDecouvert;
	}

	@Override
	public void setLibelle(String unLibelle) {
		if ((unLibelle == null) || (unLibelle.trim().length() == 0)) {
			unLibelle = null;
		} else {
			this.libelle = unLibelle;
		}
	}

	@Override
	public void setSolde(Double unSolde) {
		this.solde = unSolde;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		String parent = super.toString();
		parent = parent.substring(0, parent.length() - 1);
		sb.append(parent);
		sb.append(",libelle=");
		sb.append(this.getLibelle());
		sb.append(",solde=");
		sb.append(this.getSolde());
		sb.append(",decouvert=");
		sb.append(this.getDecouvert());
		sb.append(",decouvert=");
		sb.append(this.getTaux());
		sb.append("}");
		return sb.toString();
	}
}
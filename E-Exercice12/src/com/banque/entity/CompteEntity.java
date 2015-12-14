/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.entity;

import java.math.BigDecimal;

/**
 * Le bean qui represente un Compte. <br>
 */
public class CompteEntity extends AbstractEntity implements ICompteEntity {

	private static final long serialVersionUID = 1L;

	private String libelle;
	private BigDecimal solde;
	private BigDecimal decouvert;
	private BigDecimal taux;

	private Integer utilisateurId;

	/**
	 * Constructeur de l'objet. <br>
	 */
	public CompteEntity() {
		this(null, null, null, null, null);
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
	public CompteEntity(Integer unId, String unLibelle, BigDecimal unSolde, BigDecimal unDecouvert, BigDecimal unTaux) {
		super(unId);
		this.libelle = unLibelle;
		this.solde = unSolde;
		this.decouvert = unDecouvert;
		this.taux = unTaux;
	}

	@Override
	public BigDecimal getTaux() {
		return this.taux;
	}

	@Override
	public void setTaux(BigDecimal aTaux) {
		this.taux = aTaux;
	}

	@Override
	public BigDecimal getDecouvert() {
		if (this.decouvert == null) {
			this.decouvert = new BigDecimal(0d);
		}
		return this.decouvert;
	}

	@Override
	public String getLibelle() {
		return this.libelle;
	}

	@Override
	public BigDecimal getSolde() {
		return this.solde;
	}

	@Override
	public void setDecouvert(BigDecimal unDecouvert) {
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
	public void setSolde(BigDecimal unSolde) {
		this.solde = unSolde;
	}

	@Override
	public Integer getUtilisateurId() {
		return this.utilisateurId;
	}

	@Override
	public void setUtilisateurId(Integer utilisateurId) {
		this.utilisateurId = utilisateurId;
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
		sb.append(",taux=");
		sb.append(this.getTaux());
		sb.append(",utilisateurId=");
		sb.append(this.getUtilisateurId());
		sb.append("}");
		return sb.toString();
	}
}
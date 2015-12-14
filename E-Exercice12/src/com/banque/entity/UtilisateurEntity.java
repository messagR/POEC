/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.entity;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Le bean qui represente un utilisateur. <br>
 */
public class UtilisateurEntity extends AbstractEntity implements IUtilisateurEntity {

	private static final long serialVersionUID = 1L;

	private String login;
	private String password;

	private String nom;
	private String prenom;
	private Boolean sex;
	private Timestamp derniereConnection;
	private Date dateDeNaissance;
	private String adresse;
	private String telephone;
	private Integer codePostal;

	/**
	 * Constructeur de l'objet. <br>
	 *
	 * Par defaut l'id du compte sera -1
	 */
	public UtilisateurEntity() {
		super();
	}

	@Override
	public String getTelephone() {
		return this.telephone;
	}

	@Override
	public void setTelephone(String aTelephone) {
		if ((aTelephone == null) || aTelephone.trim().isEmpty()) {
			this.telephone = null;
		} else {
			this.telephone = aTelephone;
		}
	}

	@Override
	public Date getDateDeNaissance() {
		return this.dateDeNaissance;
	}

	@Override
	public void setDateDeNaissance(Date aDateDeNaissance) {
		this.dateDeNaissance = aDateDeNaissance;
	}

	@Override
	public String getAdresse() {
		return this.adresse;
	}

	@Override
	public void setAdresse(String aAdresse) {
		if ((aAdresse == null) || aAdresse.trim().isEmpty()) {
			this.adresse = null;
		} else {
			this.adresse = aAdresse;
		}
	}

	@Override
	public Integer getCodePostal() {
		return this.codePostal;
	}

	@Override
	public void setCodePostal(Integer aCodePostal) {
		this.codePostal = aCodePostal;
	}

	@Override
	public Boolean getSex() {
		return this.sex;
	}

	@Override
	public void setSex(Boolean pSex) {
		this.sex = pSex;
	}

	@Override
	public String getLogin() {
		return this.login;
	}

	@Override
	public void setLogin(String pLogin) {
		if ((pLogin == null) || pLogin.trim().isEmpty()) {
			this.login = null;
		} else {
			this.login = pLogin;
		}
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public void setPassword(String pPassword) {
		if ((pPassword == null) || pPassword.trim().isEmpty()) {
			this.password = null;
		} else {
			this.password = pPassword;
		}
	}

	@Override
	public String getNom() {
		return this.nom;
	}

	@Override
	public void setNom(String pNom) {
		if ((pNom == null) || pNom.trim().isEmpty()) {
			this.nom = null;
		} else {
			this.nom = pNom;
		}
	}

	@Override
	public String getPrenom() {
		return this.prenom;
	}

	@Override
	public void setPrenom(String pPrenom) {
		if ((pPrenom == null) || pPrenom.trim().isEmpty()) {
			this.prenom = null;
		} else {
			this.prenom = pPrenom;
		}
	}

	@Override
	public Timestamp getDerniereConnection() {
		return this.derniereConnection;
	}

	@Override
	public void setDerniereConnection(Timestamp pDerniereConnection) {
		this.derniereConnection = pDerniereConnection;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		String parent = super.toString();
		parent = parent.substring(0, parent.length() - 1);
		sb.append(parent);
		sb.append(",sex=");
		sb.append(this.getSex());
		sb.append(",nom=");
		sb.append(this.getNom());
		sb.append(",prenom=");
		sb.append(this.getPrenom());
		sb.append(",login=");
		sb.append(this.getLogin());
		sb.append(",adresse=");
		sb.append(this.getAdresse());
		sb.append(",Telephone=");
		sb.append(this.getTelephone());
		sb.append(",CodePostal=");
		sb.append(this.getCodePostal());
		sb.append(",DateNaissance=");
		sb.append(this.getDateDeNaissance());
		sb.append("}");
		return sb.toString();
	}
}
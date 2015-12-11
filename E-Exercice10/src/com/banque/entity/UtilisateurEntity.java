/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.entity;

import java.sql.Timestamp;

/**
 * Le bean qui represente un utilisateur. <br>
 */
public class UtilisateurEntity extends AbstractEntity implements
IUtilisateurEntity {

	private static final long serialVersionUID = 1L;

	/** Sex masculin. */
	public final static Byte SEX_M = new Byte((byte) 0);
	/** Sex feminin. */
	public final static Byte SEX_F = new Byte((byte) 1);

	private String login;
	private String password;

	private String nom;
	private String prenom;
	private Byte sex;
	private Timestamp derniereConnection;

	/**
	 * Constructeur de l'objet. <br>
	 */
	public UtilisateurEntity() {
		super();
	}

	@Override
	public Byte getSex() {
		return this.sex;
	}

	@Override
	public void setSex(Byte pSex) {
		this.sex = pSex;
	}

	@Override
	public String getLogin() {
		return this.login;
	}

	@Override
	public void setLogin(String pLogin) {
		this.login = pLogin;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public void setPassword(String pPassword) {
		this.password = pPassword;
	}

	@Override
	public String getNom() {
		return this.nom;
	}

	@Override
	public void setNom(String pNom) {
		this.nom = pNom;
	}

	@Override
	public String getPrenom() {
		return this.prenom;
	}

	@Override
	public void setPrenom(String pPrenom) {
		this.prenom = pPrenom;
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
		sb.append("}");
		return sb.toString();
	}
}
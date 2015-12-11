/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.entity;

import java.sql.Timestamp;

/**
 * Represente une utilisateur.
 */
public interface IUtilisateurEntity extends IEntity {

	/**
	 * Recupere la propriete <i>sex</i>.
	 *
	 * @return the sex la valeur de la propriete (SEX_M ou SEX_F).
	 */
	public abstract Byte getSex();

	/**
	 * Fixe la propriete <i>sex</i>.
	 *
	 * @param pSex
	 *            la nouvelle valeur pour la propriete sex (SEX_M ou SEX_F).
	 */
	public abstract void setSex(Byte pSex);

	/**
	 * Recupere la propriete <i>login</i>.
	 *
	 * @return the login la valeur de la propriete.
	 */
	public abstract String getLogin();

	/**
	 * Fixe la propriete <i>login</i>.
	 *
	 * @param pLogin
	 *            la nouvelle valeur pour la propriete login.
	 */
	public abstract void setLogin(String pLogin);

	/**
	 * Recupere la propriete <i>password</i>.
	 *
	 * @return the password la valeur de la propriete.
	 */
	public abstract String getPassword();

	/**
	 * Fixe la propriete <i>password</i>.
	 *
	 * @param pPassword
	 *            la nouvelle valeur pour la propriete password.
	 */
	public abstract void setPassword(String pPassword);

	/**
	 * Recupere la propriete <i>nom</i>.
	 *
	 * @return the nom la valeur de la propriete.
	 */
	public abstract String getNom();

	/**
	 * Fixe la propriete <i>nom</i>.
	 *
	 * @param pNom
	 *            la nouvelle valeur pour la propriete nom.
	 */
	public abstract void setNom(String pNom);

	/**
	 * Recupere la propriete <i>prenom</i>.
	 *
	 * @return the prenom la valeur de la propriete.
	 */
	public abstract String getPrenom();

	/**
	 * Fixe la propriete <i>prenom</i>.
	 *
	 * @param pPrenom
	 *            la nouvelle valeur pour la propriete prenom.
	 */
	public abstract void setPrenom(String pPrenom);

	/**
	 * Recupere la propriete <i>derniereConnection</i>.
	 *
	 * @return the derniereConnection la valeur de la propriete.
	 */
	public abstract Timestamp getDerniereConnection();

	/**
	 * Fixe la propriete <i>derniereConnection</i>.
	 *
	 * @param pDerniereConnection
	 *            la nouvelle valeur pour la propriete derniereConnection.
	 */
	public abstract void setDerniereConnection(Timestamp pDerniereConnection);

}
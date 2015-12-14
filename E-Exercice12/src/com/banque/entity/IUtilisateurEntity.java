/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.entity;

import java.sql.Date;
import java.sql.Timestamp;

public interface IUtilisateurEntity extends IEntity {

	public abstract String getTelephone();

	public abstract void setTelephone(String aTelephone);

	public abstract Date getDateDeNaissance();

	public abstract void setDateDeNaissance(Date aDateDeNaissance);

	public abstract String getAdresse();

	public abstract void setAdresse(String aAdresse);

	public abstract Integer getCodePostal();

	public abstract void setCodePostal(Integer aCodePostal);

	public abstract Boolean getSex();

	public abstract void setSex(Boolean pSex);

	public abstract String getLogin();

	public abstract void setLogin(String pLogin);

	public abstract String getPassword();

	public abstract void setPassword(String pPassword);

	public abstract String getNom();

	public abstract void setNom(String pNom);

	public abstract String getPrenom();

	public abstract void setPrenom(String pPrenom);

	public abstract Timestamp getDerniereConnection();

	public abstract void setDerniereConnection(Timestamp pDerniereConnection);

}
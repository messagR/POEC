package fr.banque.entity;

import java.util.Date;

public interface IUtilisateur extends IEntite {

	public abstract String getNom();

	public abstract void setNom(String nom);

	public abstract String getPrenom();

	public abstract void setPrenom(String prenom);

	public abstract void setDerniereConnection(Date derniereConnection);

	public abstract Date getDerniereConnection();

	public abstract String getLogin();

	public abstract void setLogin(String login);

	public abstract String getPassword();

	public abstract void setPassword(String password);

}
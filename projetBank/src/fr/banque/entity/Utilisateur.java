package fr.banque.entity;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class Utilisateur extends Entite implements IUtilisateur {
	private static final long serialVersionUID = 1L;
	private final static Logger LOG = LogManager.getLogger();

	private String nom, prenom, login, password;
	private Date derniereConnection;

	Utilisateur() {
		this(0, "", "", "", "", new Date());
	}

	Utilisateur(int id, String nom, String prenom, String login, String password, Date derniereConnection) {
		this.setNumero(id);
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setLogin(login);
		this.setPassword(password);
		this.setDerniereConnection(derniereConnection);
	}

	@Override
	public String getNom() {
		return this.nom;
	}

	@Override
	public void setNom(String nom) {
		this.nom = nom;
	}

	@Override
	public String getPrenom() {
		return this.prenom;
	}

	@Override
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	@Override
	public String getLogin() {
		return this.login;
	}

	@Override
	public void setLogin(String login) {
		this.login = login;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Date getDerniereConnection() {
		return this.derniereConnection;
	}

	@Override
	public void setDerniereConnection(Date derniereConnection) {
		this.derniereConnection = derniereConnection;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.delete(builder.length() - 1, builder.length());
		if (this.getNom() != null) {
			builder.append(", nom = ").append(this.getNom()).append(", ");
		}
		if (this.getPrenom() != null) {
			builder.append("prenom = ").append(this.getPrenom()).append(", ");
		}
		if (this.getLogin() != null) {
			builder.append("login = ").append(this.getLogin()).append(", ");
		}
		if (this.getPassword() != null) {
			builder.append("mot de passe = ").append(this.getPassword()).append(", ");
		}
		builder.append("derniere connexion = ").append(this.getDerniereConnection()).append(", ");
		builder.append("]");
		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof Utilisateur) {
			return super.equals(obj);
		}
		return false;
	}

}

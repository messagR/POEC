package fr.banque.entity;

import java.util.Date;

import fr.banque.exception.BanqueException;

public interface IClient extends IEntite {

	public abstract String getNom();

	public abstract void setNom(String nom);

	public abstract String getPrenom();

	public abstract void setPrenom(String prenom);

	public abstract int getAge();

	public abstract void setAge(int age);

	public abstract ICompte[] getComptes();

	public abstract void setComptes(ICompte[] comptes);

	public abstract void ajouterCompte(ICompte unCompte) throws BanqueException;

	public abstract ICompte getCompte(int numeroCompte);

	public abstract void setSexe(int sexe);

	public abstract int getSexe();

	public abstract void setDerniereConnection(Date derniereConnection);

	public abstract Date getDerniereConnection();

	public abstract void setCodePostal(int codePostal);

	public abstract int getCodePostal();

	public abstract void setTelephone(String telephone);

	public abstract String getTelephone();

	public abstract String getLogin();

	public abstract void setLogin(String login);

	public abstract String getPassword();

	public abstract void setPassword(String password);

	public abstract String getAdresse();

	public abstract void setAdresse(String adresse);

}
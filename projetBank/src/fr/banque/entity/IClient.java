package fr.banque.entity;

import fr.banque.exception.BanqueException;

public interface IClient extends IUtilisateur {

	public abstract int getAge();

	public abstract void setAge(int age);

	public abstract ICompte[] getComptes();

	public abstract void setComptes(ICompte[] comptes);

	public abstract void ajouterCompte(ICompte unCompte) throws BanqueException;

	public abstract ICompte getCompte(int numeroCompte);

	public abstract void setSexe(int sexe);

	public abstract int getSexe();

	public abstract void setCodePostal(int codePostal);

	public abstract int getCodePostal();

	public abstract void setTelephone(String telephone);

	public abstract String getTelephone();

	public abstract String getAdresse();

	public abstract void setAdresse(String adresse);

}
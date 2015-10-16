package com.exo.classe;

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

}
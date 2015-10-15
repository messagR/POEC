package fr.banque;

public interface ICompte extends IEntite {

	public abstract double getSolde();

	public abstract void ajouter(double unMontant);

	public abstract void retirer(double unMontant) throws BanqueException;

}
package fr.banque;

import java.util.List;

import fr.banque.exception.BanqueException;

public interface ICompte extends IEntite {

	public abstract double getSolde();

	public abstract String getLibelle();

	public abstract void setLibelle(String libelle);

	public abstract List<IOperation> getOperations();

	void setOperations(List<IOperation> operations);

	public abstract void ajouter(double unMontant);

	public abstract void retirer(double unMontant) throws BanqueException;

	public abstract void ajouterOperation(IOperation uneOperation);

}
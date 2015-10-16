package com.exo.classe;

public interface ICompteASeuil extends ICompte {

	public abstract double getSeuil();

	public abstract void setSeuil(double seuil) throws BanqueException;

	@Override
	public abstract void retirer(double uneValeur) throws BanqueException;
}

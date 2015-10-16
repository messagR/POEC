package com.exo.classe;

public interface ICompteRemunere extends ICompte {

	public abstract double getTaux();

	public abstract void setTaux(double taux);

	public abstract double calculerInterets();

	public abstract String verserInterets();
}

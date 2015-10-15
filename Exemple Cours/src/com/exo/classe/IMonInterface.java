package com.exo.classe;

import java.io.Serializable;

public interface IMonInterface extends Serializable, Cloneable {

	public final static String VALEUR_CONSTANTE = "Bonjour";

	public abstract void faireQQChose();

	public abstract int calculerUnNombre(int unA);

}

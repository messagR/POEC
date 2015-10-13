package com.exo.entite;

public class SuperAvion<T> {

	private T[] soute;
	private int taille;

	/** Par defaut, 25 cases dans la soute */
	public final static int TAILLE_DEFAULT = 25;

	/**
	 * Constructeur sans parametre.<br/>
	 * Par defaut, 25 cases dans la soute
	 */
	public SuperAvion() {
		this(SuperAvion.TAILLE_DEFAULT);
	}

	public SuperAvion(int taille) {
		this.taille = taille;
	}

	public void charger(int place, T unElement) {
		if (this.soute == null) {
			this.soute = (T[]) new Object[this.taille];
		}
		if ((place < this.taille) && (place >= 0)) {
			this.soute[place] = unElement;
		}
	}

	public T decharger(int place) {
		return this.soute[place];
	}

}

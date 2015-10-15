package com.exo.classe;

import java.io.IOException;
import java.sql.SQLException;

import javax.crypto.BadPaddingException;

/**
 * @author PC
 *
 */
public class Personne implements IPersonne {

	/**
	 * mon attribut nom
	 */
	private String nom;
	private String prenom;
	private int age;

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println("dead");
	}

	public Personne() {
		this(" ", " ");
	}

	public Personne(String nom, String prenom) {
		this(nom == null ? " " : nom, prenom == null ? " " : prenom, 0);
	}

	public Personne(String nom, String prenom, int age) {
		this.setNom(nom);
		this.setPrenom(prenom);
		try {
			this.setAge(age);
		} catch (MonException | BadPaddingException | IOException | SQLException e) {
			// TODO ajouter un log d'erreur
			try {
				this.setAge(0);
			} catch (BadPaddingException | MonException | IOException | SQLException e1) {
				this.age = 0;
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.exo.classe.IPersonne#getNom()
	 */
	@Override
	public String getNom() {
		if (this.nom == null) {
			return "";
		}
		return this.nom;
	}

	/* (non-Javadoc)
	 * @see com.exo.classe.IPersonne#setNom(java.lang.String)
	 */
	@Override
	public void setNom(String nom) {
		if (this.nom == null) {
			throw new MaSecondeException("Nom est null");
		}
		this.nom = nom;
	}

	/* (non-Javadoc)
	 * @see com.exo.classe.IPersonne#getPrenom()
	 */
	@Override
	public String getPrenom() {
		if (this.prenom == null) {
			return "";
		}
		return this.prenom;
	}

	/* (non-Javadoc)
	 * @see com.exo.classe.IPersonne#setPrenom(java.lang.String)
	 */
	@Override
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/* (non-Javadoc)
	 * @see com.exo.classe.IPersonne#getAge()
	 */
	@Override
	public int getAge() {
		return this.age;
	}

	/* (non-Javadoc)
	 * @see com.exo.classe.IPersonne#setAge(int)
	 */
	@Override
	public void setAge(int unAge) throws MonException, IOException, SQLException, BadPaddingException {
		if (unAge >= 0) {
			this.age = unAge;
		} else {
			throw new MonException("L'age doit etre strictement positif");
		}
	}

	// précise que la méthode n'est plus a utiliser
	/* (non-Javadoc)
	 * @see com.exo.classe.IPersonne#inverser(int, int)
	 */
	@Override
	@Deprecated
	public int inverser(int x, int y) {
		x = 12;
		y = 14;
		return x + y;
	}

	/* (non-Javadoc)
	 * @see com.exo.classe.IPersonne#inverser(com.exo.classe.IPersonne)
	 */
	@Override
	public void inverser(IPersonne p) throws MonException, BadPaddingException, IOException, SQLException {
		p.setAge(105);
	}

	/* (non-Javadoc)
	 * @see com.exo.classe.IPersonne#afficher()
	 */
	@Override
	public void afficher() {
		System.out.println("Personne : " + this.getNom() + " " + this.getPrenom() + " " + this.getAge());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override // Précise qu'on redefini une methode
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getName()).append(" [");
		if (this.getNom() != null) {
			builder.append("nom =").append(this.getNom()).append(", ");
		}
		if (this.getPrenom() != null) {
			builder.append("prenom =").append(this.getPrenom()).append(", ");
		}
		builder.append("age =").append(this.getAge());
		// builder.append("@(ref
		// memoire)").append(Integer.toHexString(this.hashCode()));
		builder.append("]");
		return builder.toString();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		// if (this.getClass() == obj.getClass()) {
		if (obj instanceof Personne) {
			IPersonne pTmp = (IPersonne) obj;
			// String moi = this.toString();
			// String lui = pTmp.toString();
			// return moi.equals(lui);
			return ((this.getNom() == pTmp.getNom()) || this.getNom().equals(pTmp.getNom()))
					&& ((this.getPrenom() == pTmp.getPrenom()) || this.getPrenom().equals(pTmp.getPrenom()))
					&& (this.getAge() == pTmp.getAge());
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public int hashCode() {
		String b = this.getClass().getName() + "_" + this.toString();
		return b.hashCode();
	}
}

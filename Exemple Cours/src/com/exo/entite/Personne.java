package com.exo.entite;

/**
 * @author PC
 *
 */
public class Personne {

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
		this("", "");
	}

	public Personne(String nom, String prenom) {
		this(nom == null ? "" : nom, prenom == null ? "" : prenom, 0);
	}

	public Personne(String nom, String prenom, int age) {
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setAge(age);
	}

	public String getNom() {
		if (this.nom == null) {
			return "";
		}
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		if (this.prenom == null) {
			return "";
		}
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	// précise que la méthode n'est plus a utiliser
	@Deprecated
	public int inverser(int x, int y) {
		x = 12;
		y = 14;
		return x + y;
	}

	public void inverser(Personne p) {
		p.setAge(105);
	}

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
			Personne pTmp = (Personne) obj;
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

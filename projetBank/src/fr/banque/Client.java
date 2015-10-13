package fr.banque;

public class Client extends Entite {

	private String nom, prenom;
	private int age;
	private Compte[] comptes;

	public final static int NB_COMPTE_MAX = 5;

	public Client() {
		this("", "", 0);
	}

	public Client(String nom, String prenom, int age) {
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setAge(age);
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * @param nom
	 *            the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the prenom
	 */
	public String getPrenom() {
		return this.prenom;
	}

	/**
	 * @param prenom
	 *            the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return this.age;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return the tableau
	 */
	public Compte[] getComptes() {
		return this.comptes;
	}

	/**
	 * @param comptes
	 *            the tableau to set
	 */
	public void setComptes(Compte[] comptes) {
		this.comptes = comptes;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.delete(builder.length() - 1, builder.length());
		if (this.getNom() != null) {
			builder.append(", nom = ").append(this.getNom()).append(", ");
		}
		if (this.getPrenom() != null) {
			builder.append("prenom = ").append(this.getPrenom()).append(", ");
		}
		builder.append("age = ").append(this.getAge()).append(", ");
		builder.append("numero client = ").append(this.getNumero());
		if (this.getComptes() != null) {
			builder.append(", ").append("comptes = ");
			int i = 0;
			while (i < this.comptes.length) {
				if (this.comptes[i] == null) {
					break;
				}
				if (i > 0) {
					builder.append(", ");
				}
				builder.append(this.comptes[i].toString().split(" ", 2)[1]);

				i++;
			}

		}
		// builder.append(Arrays.toString(this.getComptes()));
		builder.append("]");
		return builder.toString();
	}

	// Méthodes (au moins):
	public void ajouterCompte(Compte unCompte) {
		int i = 0;
		if (this.comptes == null) {
			this.comptes = new Compte[Client.NB_COMPTE_MAX];
		}
		while (i < this.comptes.length) {
			if (this.comptes[i] == null) {
				this.comptes[i] = unCompte;
				break;
			}
			i++;
		}
		if (i > this.comptes.length) {
			System.err.println("Tableau plein");
		}
	}

	public Compte getCompte(int numeroCompte) {
		int i = 0;
		while ((i < this.comptes.length) && (this.comptes[i] != null)) {
			if (this.comptes[i].getNumero() == numeroCompte) {
				return this.comptes[i];
			}
			i++;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	// @Override
	// public int hashCode() {
	// if (this.getNumero() == -1) {
	// return super.hashCode();
	// }
	// return (this.getClass().getName() + "_" + this.getNumero()).hashCode();
	// }

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof Client){
			return super.equals(obj);
		}
		return false;
	}

}

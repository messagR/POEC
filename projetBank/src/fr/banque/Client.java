package fr.banque;

public class Client {

	private String nom, prenom;
	private int age, numClient;
	private Compte[] comptes;

	public final static int NB_COMPTE_MAX = 5;

	public Client() {
		this("", "", 0, 0);
	}

	public Client(String nom, String prenom, int age, int numClient) {
		this.nom = nom;
		this.prenom = prenom;
		this.age = age;
		this.numClient = numClient;
		// this.comptes = new Compte[Client.NB_COMPTE_MAX];
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
	 * @return the numero
	 */
	public int getNumClient() {
		return this.numClient;
	}

	/**
	 * @param numero
	 *            the numero to set
	 */
	public void setNumClient(int numero) {
		this.numClient = numero;
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
		builder.append(this.getClass().getName()).append(" [");
		if (this.getNom() != null) {
			builder.append("nom = ").append(this.getNom()).append(", ");
		}
		if (this.getPrenom() != null) {
			builder.append("prenom = ").append(this.getPrenom()).append(", ");
		}
		builder.append("age = ").append(this.getAge()).append(", ");
		builder.append("numero = ").append(this.getNumClient());
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
	public void ajouterCompte(Compte unCompte){
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
			if (this.comptes[i].getNumCompte() == numeroCompte) {
				return this.comptes[i];
			}
			i++;
		}
		return null;
	}

}

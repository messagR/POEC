package fr.banque;

/**
 * visu package pour la factory
 */
class ClientSupprime extends Entite implements IClient {

	private String nom, prenom;
	private int age;
	private ICompte[] comptes;

	public final static int NB_COMPTE_MAX = 5;

	/**
	 * visu package pour la factory
	 */
	ClientSupprime() {
		this("", "", 0);
	}

	/**
	 * visu package pour la factory
	 */
	ClientSupprime(String nom, String prenom, int age) {
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setAge(age);
	}

	@Override
	public String getNom() {
		return this.nom;
	}

	@Override
	public void setNom(String nom) {
		this.nom = nom;
	}

	@Override
	public String getPrenom() {
		return this.prenom;
	}

	@Override
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	@Override
	public int getAge() {
		return this.age;
	}

	@Override
	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public ICompte[] getComptes() {
		return this.comptes;
	}

	@Override
	public void setComptes(ICompte[] comptes) {
		this.comptes = comptes;
	}

	@Override
	public void ajouterCompte(ICompte unCompte) throws BanqueException {
		int i = 0;
		if (this.comptes == null) {
			this.comptes = new ICompte[ClientSupprime.NB_COMPTE_MAX];
		}
		while (i < this.comptes.length) {
			if (this.comptes[i] == null) {
				this.comptes[i] = unCompte;
				break;
			}
			i++;
		}
		if (i == this.comptes.length) {
			throw new BanqueException("Nombre de comptes maximum atteint pour le client n°" + this.getNumero());
		}
	}

	@Override
	public ICompte getCompte(int numeroCompte) {
		int i = 0;
		while ((i < this.comptes.length) && (this.comptes[i] != null)) {
			if (this.comptes[i].getNumero() == numeroCompte) {
				return this.comptes[i];
			}
			i++;
		}
		return null;
	}

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
		builder.append("age = ").append(this.getAge()).append("]");
		if (this.getComptes() != null) {
			builder.append("\n         | ").append("comptes = ");
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
		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof ClientSupprime){
			return super.equals(obj);
		}
		return false;
	}

}

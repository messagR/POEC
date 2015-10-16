package com.exo.classe;

public final class Factory {

	private int dernierNumeroClient;
	private int dernierNumeroCompte;

	// singleton
	private static Factory instance;

	private Factory() {
		super();
		this.setDernierNumeroClient(1);
		this.setDernierNumeroCompte(100000);
	}

	// getInstance ou getDefaultInstance
	public static synchronized Factory getInstance() {
		//		if (Factory.instance == null) {
		//			synchronized (Factory.class) {
		if (Factory.instance == null) {
			Factory.instance = new Factory();
		}
		//			}
		// }
		return Factory.instance;
	}
	// /singleton

	public int getDernierNumeroCompte() {
		return this.dernierNumeroCompte;
	}

	private void setDernierNumeroCompte(int dernierNumCompte) {
		this.dernierNumeroCompte = dernierNumCompte;
	}

	public int getDernierNumeroClient() {
		return this.dernierNumeroClient;
	}

	private void setDernierNumeroClient(int dernierNumClient) {
		this.dernierNumeroClient = dernierNumClient;
	}

	public void affecteNumeroCompte(ICompte compte) {
		compte.setNumero(this.getDernierNumeroCompte());
		this.dernierNumeroCompte++;

	}

	public void decrementeNumeroCompte() {
		this.dernierNumeroCompte--;

	}

	/**
	 * @param args
	 *            (0-> class de l'objet (type du compte), 1->solde, 2->taux ou
	 *            seuil, 3->taux)
	 * @return
	 * @throws BanqueException
	 */
	public ICompte creerCompte(Object... args) throws BanqueException {
		ICompte compte = null;
		Class type = null;
		double solde = 0;
		double taux = 0.0;
		double seuil = 0.0;

		switch (args.length) {
		case 0:
			compte = new Compte(solde);
			break;
		case 1:
			type = (Class) args[0];
			if (type == ICompte.class) {
				compte = new Compte();
			} else if (type == ICompteRemunere.class) {
				compte = new CompteRemunere();
			} else if (type == ICompteASeuil.class) {
				compte = new CompteASeuil();
			} else if (type == ICompteASeuilRemunere.class) {
				compte = new CompteASeuilRemunere();
			}
			break;
		case 2:
			type = (Class) args[0];
			solde = (Double) args[1];
			compte = new Compte(solde);
			break;
		case 3:
			type = (Class) args[0];
			solde = (Double) args[1];
			if (type == ICompteRemunere.class) {
				taux = (Double) args[2];
				compte = new CompteRemunere(solde, taux);
			} else if (type == ICompteASeuil.class) {
				seuil = (Double) args[2];
				if (solde < seuil) {
					throw new BanqueException("Solde insuffisant : creation de compte impossible");
				} else {
					compte = new CompteASeuil(solde, seuil);
				}
			}
			break;
		case 4:
			type = (Class) args[0];
			solde = (Double) args[1];
			seuil = (Double) args[2];
			taux = (Double) args[3];
			if (solde < seuil) {
				throw new BanqueException("Solde insuffisant : creation de compte impossible");
			} else {
				compte = new CompteASeuilRemunere(solde, seuil, taux);
			}
			break;
		}

		this.affecteNumeroCompte(compte);
		return compte;
	}

	public IClient creerClient() {
		return this.creerClient("", "", 0);
	}

	public IClient creerClient(String nom, String prenom, int age) {
		IClient client = new Client(nom, prenom, age);
		client.setNumero(this.getDernierNumeroClient());
		this.dernierNumeroClient++;
		return client;
	}

	// inner class (classe dans la classe)

	private class Client extends Entite implements IClient {

		private String nom, prenom;
		private int age;
		private ICompte[] comptes;

		public final static int NB_COMPTE_MAX = 5;

		/**
		 * visu package pour la factory
		 */
		Client() {
			this("", "", 0);
		}

		/**
		 * visu package pour la factory
		 */
		Client(String nom, String prenom, int age) {
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
				this.comptes = new ICompte[Client.NB_COMPTE_MAX];
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
			if (obj instanceof Client) {
				return super.equals(obj);
			}
			return false;
		}

	}


}

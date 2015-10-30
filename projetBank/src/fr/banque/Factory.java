package fr.banque;

import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import fr.banque.exception.BanqueException;

public final class Factory {

	/*
	 * private int dernierNumeroClient; private int dernierNumeroCompte; private
	 * int dernierNumeroOperation;
	 */

	// singleton
	private static Factory instance;

	private Factory() {
		super();
		/*
		 * this.setDernierNumeroClient(1); this.setDernierNumeroCompte(100000);
		 * this.setDernierNumeroOperation(1);
		 */
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

	// public int getDernierNumeroCompte() { return this.dernierNumeroCompte; }
	//
	// public int getDernierNumeroOperation() { return
	// this.dernierNumeroOperation; }
	//
	// public void setDernierNumeroOperation(int dernierNumeroOperation) {
	// this.dernierNumeroOperation = dernierNumeroOperation; }
	//
	// private void setDernierNumeroCompte(int dernierNumCompte) {
	// this.dernierNumeroCompte = dernierNumCompte; }
	//
	// public int getDernierNumeroClient() { return this.dernierNumeroClient; }
	//
	// private void setDernierNumeroClient(int dernierNumClient) {
	// this.dernierNumeroClient = dernierNumClient; }
	//
	// public void affecteNumeroCompte(ICompte compte) {
	// compte.setNumero(this.getDernierNumeroCompte());
	// this.dernierNumeroCompte++;
	//
	// }
	//
	// public void affecteNumeroOperation(IOperation operation) {
	// operation.setNumero(this.getDernierNumeroOperation());
	// this.dernierNumeroOperation++;
	//
	// }
	//
	// public void decrementeNumeroCompte() { this.dernierNumeroCompte--;
	//
	// }

	public IOperation creerOperation(int id) {
		IOperation operation = this.creerOperation(id, "", 0.0, new Date());
		// this.affecteNumeroOperation(operation);
		return operation;
	}

	public IOperation creerOperation(int id, String libelle, double montant, Date date) {
		IOperation operation = new Operation(id, libelle, montant, date);
		// this.affecteNumeroOperation(operation);
		return operation;
	}

	/**
	 * @param args
	 *            (0-> class de l'objet (type du compte), 1->id, 2->libelle,
	 *            3->solde, 4->taux ou seuil, 5->taux)
	 * @return
	 * @throws BanqueException
	 */
	public ICompte creerCompte(Object... args) throws BanqueException {
		ICompte compte = null;
		Class type = null;
		int id = 0;
		String libelle = null;
		double solde = 0;
		double taux = 0.0;
		double seuil = 0.0;

		switch (args.length) {
		case 0:
			compte = new Compte(id, libelle, solde);
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
		case 4:
			type = (Class) args[0];
			id = (int) args[1];
			libelle = (String) args[2];
			solde = (Double) args[3];
			if (type == ICompte.class) {
				compte = new Compte(id, libelle, solde);
			} else if (type == ICompteRemunere.class) {
				compte = new CompteRemunere(id, libelle, solde);
			} else if (type == ICompteASeuil.class) {
				compte = new CompteASeuil(id, libelle, solde);
			} else if (type == ICompteASeuilRemunere.class) {
				compte = new CompteASeuilRemunere(id, libelle, solde);
			}
			break;
		case 5:
			type = (Class) args[0];
			id = (int) args[1];
			libelle = (String) args[2];
			solde = (Double) args[3];
			if (type == ICompteRemunere.class) {
				taux = (Double) args[4];
				compte = new CompteRemunere(id, libelle, solde, taux);
			} else if (type == ICompteASeuil.class) {
				seuil = (Double) args[4];
				if (solde < seuil) {
					throw new BanqueException("Solde insuffisant : creation de compte impossible");
				} else {
					compte = new CompteASeuil(id, libelle, solde, seuil);
				}
			} else if (type == ICompteASeuilRemunere.class) {
				seuil = (Double) args[4];
				if (solde < seuil) {
					throw new BanqueException("Solde insuffisant : creation de compte impossible");
				} else {
					compte = new CompteASeuilRemunere(id, libelle, solde, seuil);
				}
			}
			break;
		case 6:
			type = (Class) args[0];
			id = (int) args[1];
			libelle = (String) args[2];
			solde = (Double) args[3];
			seuil = (Double) args[4];
			taux = (Double) args[5];
			if (solde < seuil) {
				throw new BanqueException("Solde insuffisant : creation de compte impossible");
			} else {
				compte = new CompteASeuilRemunere(id, libelle, solde, seuil, taux);
			}
			break;
		}

		// this.affecteNumeroCompte(compte);
		return compte;
	}

	public IClient creerClient() {
		return this.creerClient(0, "", "", 0, "", "", "", "", 0, 0, new Date());
	}

	public IClient creerClient(int id, String nom, String prenom, int age, String login, String password,
			String adresse, String telephone, int codePostal, int sexe, Date derniereConnection) {
		IClient client = new Client(id, nom, prenom, age, login, password, adresse, telephone, codePostal, sexe,
				derniereConnection);
		/*
		 * client.setNumero(this.getDernierNumeroClient());
		 * this.dernierNumeroClient++;
		 */
		return client;
	}

	// inner class (classe dans la classe)

	private class Client extends Entite implements IClient {

		private String nom, prenom, login, password, adresse, telephone;
		private int age, codePostal, sexe;
		private Date derniereConnection;
		// private List<ICompte> comptes;
		private Map<Integer, ICompte> comptes;

		public final static int NB_COMPTE_MAX = 5;

		/**
		 * visu package pour la factory
		 */
		Client() {
			this(0, "", "", 0, "", "", "", "", 0, 0, new Date());
		}

		/**
		 * visu package pour la factory
		 */
		Client(int id, String nom, String prenom, int age, String login, String password, String adresse,
				String telephone, int codePostal, int sexe, Date derniereConnection) {
			this.setNumero(id);
			this.setNom(nom);
			this.setPrenom(prenom);
			this.setAge(age);
			this.setLogin(login);
			this.setPassword(password);
			this.setAdresse(adresse);
			this.setTelephone(telephone);
			this.setCodePostal(codePostal);
			this.setSexe(sexe);
			this.setDerniereConnection(derniereConnection);
		}

		/* (non-Javadoc)
		 * @see fr.banque.IClient2#getNom()
		 */
		@Override
		public String getNom() {
			return this.nom;
		}

		/* (non-Javadoc)
		 * @see fr.banque.IClient2#setNom(java.lang.String)
		 */
		@Override
		public void setNom(String nom) {
			this.nom = nom;
		}

		/* (non-Javadoc)
		 * @see fr.banque.IClient2#getPrenom()
		 */
		@Override
		public String getPrenom() {
			return this.prenom;
		}

		/* (non-Javadoc)
		 * @see fr.banque.IClient2#setPrenom(java.lang.String)
		 */
		@Override
		public void setPrenom(String prenom) {
			this.prenom = prenom;
		}

		/* (non-Javadoc)
		 * @see fr.banque.IClient2#getAge()
		 */
		@Override
		public int getAge() {
			return this.age;
		}

		/* (non-Javadoc)
		 * @see fr.banque.IClient2#setAge(int)
		 */
		@Override
		public void setAge(int age) {
			this.age = age;
		}

		/* (non-Javadoc)
		 * @see fr.banque.IClient2#getComptes()
		 */
		@Override
		public ICompte[] getComptes() {
			ICompte[] comptes = new ICompte[Client.NB_COMPTE_MAX];
			// return this.comptes.toArray(comptes);
			if (this.comptes != null) {
				return this.comptes.values().toArray(comptes);
			} else {
				return null;
			}
		}

		/* (non-Javadoc)
		 * @see fr.banque.IClient2#setComptes(fr.banque.ICompte[])
		 */
		@Override
		public void setComptes(ICompte[] comptes) {
			// this.comptes = Arrays.asList(comptes);
			int i = 0;
			while (i < comptes.length) {
				if (comptes[i] != null) {
					// this.comptes.add(comptes[i]);
					this.comptes.put(comptes[i].getNumero(), comptes[i]);
				}
				i++;
			}
		}

		/* (non-Javadoc)
		 * @see fr.banque.IClient2#getLogin()
		 */
		@Override
		public String getLogin() {
			return this.login;
		}

		/* (non-Javadoc)
		 * @see fr.banque.IClient2#setLogin(java.lang.String)
		 */
		@Override
		public void setLogin(String login) {
			this.login = login;
		}

		/* (non-Javadoc)
		 * @see fr.banque.IClient2#getPassword()
		 */
		@Override
		public String getPassword() {
			return this.password;
		}

		/* (non-Javadoc)
		 * @see fr.banque.IClient2#setPassword(java.lang.String)
		 */
		@Override
		public void setPassword(String password) {
			this.password = password;
		}

		/* (non-Javadoc)
		 * @see fr.banque.IClient2#getAdresse()
		 */
		@Override
		public String getAdresse() {
			return this.adresse;
		}

		/* (non-Javadoc)
		 * @see fr.banque.IClient2#setAdresse(java.lang.String)
		 */
		@Override
		public void setAdresse(String adresse) {
			this.adresse = adresse;
		}

		/* (non-Javadoc)
		 * @see fr.banque.IClient2#getTelephone()
		 */
		@Override
		public String getTelephone() {
			return this.telephone;
		}

		/* (non-Javadoc)
		 * @see fr.banque.IClient2#setTelephone(java.lang.String)
		 */
		@Override
		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}

		/* (non-Javadoc)
		 * @see fr.banque.IClient2#getCodePostal()
		 */
		@Override
		public int getCodePostal() {
			return this.codePostal;
		}

		/* (non-Javadoc)
		 * @see fr.banque.IClient2#setCodePostal(int)
		 */
		@Override
		public void setCodePostal(int codePostal) {
			this.codePostal = codePostal;
		}

		/* (non-Javadoc)
		 * @see fr.banque.IClient2#getDerniereConnection()
		 */
		@Override
		public Date getDerniereConnection() {
			return this.derniereConnection;
		}

		/* (non-Javadoc)
		 * @see fr.banque.IClient2#setDerniereConnection(java.util.Date)
		 */
		@Override
		public void setDerniereConnection(Date derniereConnection) {
			this.derniereConnection = derniereConnection;
		}

		/* (non-Javadoc)
		 * @see fr.banque.IClient2#getSexe()
		 */
		@Override
		public int getSexe() {
			return this.sexe;
		}

		/* (non-Javadoc)
		 * @see fr.banque.IClient2#setSexe(int)
		 */
		@Override
		public void setSexe(int sexe) {
			this.sexe = sexe;
		}

		/* (non-Javadoc)
		 * @see fr.banque.IClient2#setComptes(java.util.Map)
		 */
		public void setComptes(Map<Integer, ICompte> comptes) {
			this.comptes = comptes;
		}

		/* (non-Javadoc)
		 * @see fr.banque.IClient2#ajouterCompte(fr.banque.ICompte)
		 */
		@Override
		public void ajouterCompte(ICompte unCompte) throws BanqueException {
			if (this.comptes == null) {
				//				this.comptes = new ArrayList<ICompte>();
				this.comptes = new Hashtable<Integer, ICompte>();
			}
			if (Client.NB_COMPTE_MAX == this.comptes.size()) {
				throw new BanqueException("Nombre de comptes maximum atteint pour le client n°" + this.getNumero());
			} else {
				// this.comptes.add(unCompte);
				this.comptes.put(unCompte.getNumero(), unCompte);
			}
		}

		/* (non-Javadoc)
		 * @see fr.banque.IClient2#getCompte(int)
		 */
		@Override
		public ICompte getCompte(int numeroCompte) {
			// Iterator iterCompte = this.comptes.iterator();
			// while (iterCompte.hasNext()) {
			// ICompte compte = iterCompte.next();
			// if (compte.getNumero() == numeroCompte) {
			// return compte;
			// }
			// }
			return this.comptes.get(numeroCompte);
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
			if (this.getLogin() != null) {
				builder.append("login = ").append(this.getLogin()).append(", ");
			}
			if (this.getPassword() != null) {
				builder.append("mot de passe = ").append(this.getPassword()).append(", ");
			}
			builder.append("cp = ").append(this.getCodePostal()).append(", ");
			if (this.getAdresse() != null) {
				builder.append("adresse = ").append(this.getAdresse()).append(", ");
			}
			builder.append("telephone = ").append(this.getTelephone()).append(", ");
			builder.append("sexe = ");
			switch (this.getSexe()) {
			case 0:
				builder.append("Mascullin");
				break;
			case 1:
				builder.append("Feminin");
				break;
			default:
				builder.append("ne sais pas");
				break;
			}
			builder.append(", ");
			builder.append("derniere connexion = ").append(this.getDerniereConnection()).append(", ");
			builder.append("age = ").append(this.getAge());
			if (this.getComptes() != null) {
				builder.append("\n         | ").append("comptes = ");
				// Iterator iterCompte = this.comptes.iterator();
				Iterator<ICompte> iterCompte = this.comptes.values().iterator();
				while (iterCompte.hasNext()) {
					ICompte compte = iterCompte.next();
					builder.append(compte.toString().split(" ", 2)[1]);
					if (iterCompte.hasNext()) {
						builder.append(", ");
					}
				}
			}
			builder.append("]");
			return builder.toString();
		}

		/* (non-Javadoc)
		 * @see fr.banque.IClient2#equals(java.lang.Object)
		 */
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

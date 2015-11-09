package fr.banque.entity;

import java.util.Date;

import fr.banque.exception.BanqueException;

public final class Factory {

	private int dernierNumeroClient;
	private int dernierNumeroCompte;
	private int dernierNumeroOperation;

	// singleton
	private static Factory instance;

	private Factory() {
		super();
		this.setDernierNumeroClient(1);
		this.setDernierNumeroCompte(100000);
		this.setDernierNumeroOperation(1);
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

	public int getDernierNumeroOperation() {
		return this.dernierNumeroOperation;
	}

	public void setDernierNumeroOperation(int dernierNumeroOperation) {
		this.dernierNumeroOperation = dernierNumeroOperation;
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

	public void affecteNumeroOperation(IOperation operation) {
		operation.setNumero(this.getDernierNumeroOperation());
		this.dernierNumeroOperation++;

	}

	public void decrementeNumeroCompte() {
		this.dernierNumeroCompte--;

	}

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
		Class<?> type = null;
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
			type = (Class<?>) args[0];
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
			type = (Class<?>) args[0];
			id = (Integer) args[1];
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
			type = (Class<?>) args[0];
			id = (Integer) args[1];
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
			type = (Class<?>) args[0];
			id = (Integer) args[1];
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
		return compte;
	}

	public IClient creerClient() {
		return this.creerClient(0, "", "", 0, "", "", "", "", 0, 0, new Date());
	}

	public IClient creerClient(int id, String nom, String prenom, int age, String login, String password,
			String adresse, String telephone, int codePostal, int sexe, Date derniereConnection) {
		IClient client = new Client(id, nom, prenom, age, login, password, adresse, telephone, codePostal, sexe,
				derniereConnection);

		// client.setNumero(this.getDernierNumeroClient());
		// this.dernierNumeroClient++;

		return client;
	}
}

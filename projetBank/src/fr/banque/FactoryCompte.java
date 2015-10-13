package fr.banque;

public class FactoryCompte {

	private static FactoryCompte instance;
	private int dernierNumCompte;

	private FactoryCompte() {
		this.setDernierNumCompte(100000);
	}

	public static FactoryCompte getInstance() {
		if(FactoryCompte.instance == null) {
			synchronized (FactoryCompte.class) {
				if (FactoryCompte.instance == null) {
					FactoryCompte.instance = new FactoryCompte();
				}
			}
		}
		return FactoryCompte.instance;
	}

	/**
	 * @return the dernierNumCompte
	 */
	public int getDernierNumCompte() {
		return this.dernierNumCompte;
	}

	/**
	 * @param dernierNumCompte
	 *            the dernierNumCompte to set
	 */
	private void setDernierNumCompte(int dernierNumCompte) {
		this.dernierNumCompte = dernierNumCompte;
	}

	public Compte creerCompte() {
		return this.creerCompte(0);
	}

	public Compte creerCompte(int solde) {
		Compte compte = new Compte(solde);
		compte.setNumero(this.getDernierNumCompte());
		this.dernierNumCompte++;
		return compte;
	}

	public CompteRemunere creerCompteRemunere() {
		return this.creerCompteRemunere(0);
	}

	public CompteRemunere creerCompteRemunere(int solde) {
		CompteRemunere compte = new CompteRemunere(solde);
		compte.setNumero(this.getDernierNumCompte());
		this.dernierNumCompte++;
		return compte;
	}

	public CompteASeuil creerCompteASeuil() {
		return this.creerCompteASeuil(0);
	}

	public CompteASeuil creerCompteASeuil(int solde) {
		CompteASeuil compte = new CompteASeuil(solde);
		compte.setNumero(this.getDernierNumCompte());
		this.dernierNumCompte++;
		return compte;
	}

}

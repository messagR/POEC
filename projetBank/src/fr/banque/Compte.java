package fr.banque;

import java.util.List;

/**
 * visu package pour la factory
 */
class Compte extends Entite implements ICompte {

	private String libelle;
	private double solde;
	private List operations;

	/**
	 * visu package pour la factory
	 */
	Compte() {
		this(0, "", 0);
	}

	/**
	 * visu package pour la factory
	 */
	Compte(int id, String libelle, double solde) {
		this.setNumero(id);
		this.setLibelle(libelle);
		this.setSolde(solde);

	}

	public double getSolde() {
		return this.solde;
	}

	private void setSolde(double solde) {
		this.solde = solde;
	}

	/**
	 * @return the libelle
	 */
	public String getLibelle() {
		return this.libelle;
	}

	/**
	 * @param libelle
	 *            the libelle to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	/**
	 * @return the operations
	 */
	public List getOperations() {
		return this.operations;
	}

	/**
	 * @param operations
	 *            the operations to set
	 */
	public void setOperations(List operations) {
		this.operations = operations;
	}

	@Override
	public void ajouter(double unMontant) {
		this.setSolde(this.getSolde() + unMontant);
	}

	@Override
	public void retirer(double unMontant) throws BanqueException {
		this.setSolde(this.getSolde() - unMontant);
	}

	@Override
	public void ajouterOperation(IOperation uneOperation) {
		this.operations.add(uneOperation);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.delete(builder.length() - 1, builder.length());
		builder.append(", libelle = ").append(this.getLibelle());
		builder.append(", solde = ").append(this.getSolde()).append("]");
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
		if (obj instanceof Compte) {
			return super.equals(obj);
		}
		return false;
	}

}

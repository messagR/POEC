package fr.banque.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.banque.exception.BanqueException;

class Compte extends Entite implements ICompte {
	private static final long serialVersionUID = 1L;

	private String libelle;
	private double solde;
	private List<IOperation> operations;

	Compte() {
		this(0, "", 0);
	}

	Compte(int id, String libelle, double solde) {
		this.setNumero(id);
		this.setLibelle(libelle);
		this.setSolde(solde);

	}

	@Override
	public double getSolde() {
		return this.solde;
	}

	protected void setSolde(double solde) {
		this.solde = solde;
	}

	@Override
	public String getLibelle() {
		return this.libelle;
	}

	@Override
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	@Override
	public List<IOperation> getOperations() {
		return this.operations;
	}

	@Override
	public void setOperations(List<IOperation> operations) {
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
		if(this.operations == null){
			this.operations = new ArrayList<IOperation>();
		}
		this.operations.add(uneOperation);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.delete(builder.length() - 1, builder.length());
		builder.append(", libelle = ").append(this.getLibelle());
		builder.append(", solde = ").append(this.getSolde());
		if (this.getOperations() != null) {
			builder.append("\n         | ").append("operations = ");
			// Iterator iterCompte = this.comptes.iterator();
			Iterator<IOperation> iteroperation = this.operations.iterator();
			while (iteroperation.hasNext()) {
				IOperation operation = iteroperation.next();
				builder.append(operation.toString().split(" ", 2)[1]);
				if (iteroperation.hasNext()) {
					builder.append(", ");
				}
			}
		}
		builder.append("]");
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

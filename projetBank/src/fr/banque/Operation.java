package fr.banque;

import java.util.Date;

/**
 * visu package pour la factory
 */
class Operation extends Entite implements IOperation {

	private String libelle;
	private double montant;
	private Date date;

	/**
	 * visu package pour la factory
	 */
	Operation() {
		this(0, "", 0, new Date());
	}

	/**
	 * visu package pour la factory
	 */
	Operation(int id, String libelle, double montant, Date date) {
		this.setNumero(id);
		this.setLibelle(libelle);
		this.setMontant(montant);
		this.setDate(date);

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
	 * @return the montant
	 */
	public double getMontant() {
		return this.montant;
	}

	/**
	 * @param montant
	 *            the montant to set
	 */
	public void setMontant(double montant) {
		this.montant = montant;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.delete(builder.length() - 1, builder.length());
		builder.append(", libelle = ").append(this.getLibelle());
		builder.append(", montant = ").append(this.getMontant());
		builder.append(", date = ").append(this.getDate()).append("]");
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
		if (obj instanceof Operation) {
			return super.equals(obj);
		}
		return false;
	}

}

package com.exo.classe;

import java.io.Serializable;
import java.util.Date;

// objet serialisable : inputStream outputStream
// pas de serialisable si connection, resultset, statement...
class Operation extends Entite implements IOperation, Serializable {
	private static final long serialVersionUID = 1L;

	private String libelle;
	private transient String libelle2;
	// non serialisable (champs calcule, static ou constante) dans un objet
	// serialisable
	private double montant;
	private Date date;

	Operation() {
		this(0, "", 0, new Date());
	}

	Operation(int id, String libelle, double montant, Date date) {
		this.setNumero(id);
		this.setLibelle(libelle);
		this.setMontant(montant);
		this.setDate(date);

	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public double getMontant() {
		return this.montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public Date getDate() {
		return this.date;
	}

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

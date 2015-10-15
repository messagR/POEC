package com.exo.classe;

import java.util.Date;

public class Chien extends Animal {

	private Date adoption;

	public Chien() {
	}

	@Override
	public void crier() {
		System.out.println("Wouaf");

	}

	/**
	 * @return the adoption
	 */
	public Date getAdoption() {
		return this.adoption;
	}

	/**
	 * @param adoption
	 *            the adoption to set
	 */
	public void setAdoption(Date adoption) {
		this.adoption = adoption;
	}

}

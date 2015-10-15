package com.exo.classe;

public abstract class Animal {

	private String nom;
	private int age;

	public Animal() {
	}

	public abstract void crier();

	/**
	 * @return the nom
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * @param nom
	 *            the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return this.age;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	public void faireDuBruit() {
		this.crier();
		this.crier();
		this.crier();
		this.crier();
		this.crier();
	}
}

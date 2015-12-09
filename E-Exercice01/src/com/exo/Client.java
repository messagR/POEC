/**
 * test
 */
package com.exo;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author PC
 *
 */
public class Client {
	private static final Log LOG = LogFactory.getLog(Client.class);

	private String nom;
	private String prenom;
	private int age;
	private List<Adresse> adresses;

	/**
	 *
	 */
	public Client() {
		this("", "");
	}

	public Client(String nom, String prenom) {
		this(nom, prenom, 0);
	}

	public Client(String nom, String prenom, int age) {
		Client.LOG.info("constructeur " + this.toString());
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setAge(age);
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public List<Adresse> getAdresses() {
		return this.adresses;
	}

	public void setAdresses(List<Adresse> adresse) {
		this.adresses = adresse;
	}

	public void init() {
		Client.LOG.info("init " + this.toString());
	}

	public void destroy() {
		Client.LOG.info("destroy " + this.toString());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Client [");
		if ((this.nom != null) && !this.nom.equals("")) {
			builder.append(this.nom);
		}
		if ((this.prenom != null) && !this.prenom.equals("")) {
			builder.append(" ").append(this.prenom);
		}
		if (this.age != 0) {
			builder.append(", ");
			builder.append(this.age).append(" an(s)");
		}
		if ((this.adresses != null) && !this.adresses.isEmpty()) {
			builder.append(", Adresse(s) :");
			for (Adresse adr : this.adresses) {
				builder.append(adr.toString());
			}
		}
		builder.append("]");
		return builder.toString();
	}

}

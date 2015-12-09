/**
 * test
 */
package com.exo;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author PC
 *
 */
public class Adresse {
	private static final Log LOG = LogFactory.getLog(Client.class);

	private String codePostal;
	private String adresse;
	private String ville;
	private String pays;

	/**
	 *
	 */
	public Adresse() {
		this("", "", "", "");
	}

	public Adresse(String codePostal, String adresse, String ville, String pays) {
		Adresse.LOG.info("constructeur " + this.toString());
		this.setCodePostal(codePostal);
		this.setAdresse(adresse);
		this.setVille(ville);
		this.setPays(pays);
	}

	public String getCodePostal() {
		return this.codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getAdresse() {
		return this.adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getVille() {
		return this.ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getPays() {
		return this.pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	@PostConstruct
	public void init() {
		Adresse.LOG.info("init " + this.toString());
	}

	public void destroy() {
		Adresse.LOG.info("destroy " + this.toString());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		if (this.adresse != null) {
			builder.append(this.adresse);
		}
		if (this.codePostal != null) {
			builder.append(" ").append(this.codePostal);
		}
		if (this.ville != null) {
			builder.append(" ").append(this.ville);
			builder.append(", ");
		}
		if (this.pays != null) {
			builder.append(this.pays);
		}
		builder.append("]");
		return builder.toString();
	}

}

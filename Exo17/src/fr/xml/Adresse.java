/**
 * test
 */
package fr.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author PC
 *
 */
@XmlRootElement(name = "adresse")
@XmlAccessorType(XmlAccessType.NONE)
public class Adresse {

	@XmlAttribute
	private String ville;

	@XmlElement
	private String rue;

	@XmlAttribute
	private String pays;

	@XmlAttribute
	private int codePostal;

	public Adresse() {
		this("", "", "", 0);
	}

	public Adresse(String ville, String rue, String pays, int codePostal) {
		this.setVille(ville);
		this.setRue(rue);
		this.setPays(pays);
		this.setCodePostal(codePostal);
	}

	public String getVille() {
		return this.ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getRue() {
		return this.rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getPays() {
		return this.pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public int getCodePostal() {
		return this.codePostal;
	}

	public void setCodePostal(int codePostal) {
		this.codePostal = codePostal;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.delete(builder.length() - 1, builder.length());
		builder.append("Adresse [");
		if (this.getVille() != null) {
			builder.append("ville=").append(this.getVille()).append(", ");
		}
		if (this.getRue() != null) {
			builder.append("rue=").append(this.getRue()).append(", ");
		}
		if (this.getPays() != null) {
			builder.append("pays=").append(this.getPays()).append(", ");
		}
		builder.append("codePostal=").append(this.getCodePostal());
		builder.append("]");
		return builder.toString();
	}

}

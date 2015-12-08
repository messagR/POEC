/**
 * test
 */
package fr.xml;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import fr.exceptions.PersonneException;

/**
 * @author PC
 *
 */
@XmlRootElement(name = "personne")
@XmlAccessorType(XmlAccessType.NONE)
public class Personne {

	@XmlElementWrapper(name = "adresses")
	@XmlElement(name = "adresse")
	private List<Adresse> adresses;

	@XmlElement
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date dateNaissance;

	@XmlElement
	private String nom;

	@XmlElement
	private String prenom;

	public Personne() {
		this("", "");
	}

	public Personne(String nom, String prenom) {
		this(nom, prenom, null);
	}

	public Personne(String nom, String prenom, Date dateNaiss) {
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setDateNaissance(dateNaiss);
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

	public Date getDateNaissance() {
		return this.dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public List<Adresse> getAdresses() {
		return this.adresses;
	}

	public void setAdresses(List<Adresse> adresses) {
		this.adresses = adresses;
	}

	public void addAdresse(Adresse adresse) throws PersonneException {
		if (this.getAdresses() == null) {
			this.adresses = new ArrayList<Adresse>();
		}
		if (this.adresses.contains(adresse)) {
			throw new PersonneException("Adresse " + adresse + " déjà ajoutée");
		}
		this.adresses.add(adresse);
	}

	public void deleteAdresse(Adresse adresse) throws PersonneException {
		if (!this.adresses.contains(adresse)) {
			throw new PersonneException("Adresse " + adresse + " introuvable");
		}
		this.adresses.remove(adresse);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.delete(builder.length() - 1, builder.length());
		builder.append("Personne [");
		if (this.getNom() != null) {
			builder.append("nom=").append(this.getNom()).append(", ");
		}
		if (this.getPrenom() != null) {
			builder.append("prenom=").append(this.getPrenom()).append(", ");
		}
		if (this.getDateNaissance() != null) {
			builder.append("dateNaissance=").append(this.getDateNaissance()).append(", ");
		}
		if (this.getAdresses() != null) {
			builder.append("\n         | ").append("adresses = ");
			Iterator<Adresse> iterAdresses = this.getAdresses().iterator();
			while (iterAdresses.hasNext()) {
				Adresse adresse = iterAdresses.next();
				builder.append(adresse.toString().split(" ", 2)[1]);
				if (iterAdresses.hasNext()) {
					builder.append(", ");
				}
			}
		}
		builder.append("]");
		return builder.toString();
	}
}

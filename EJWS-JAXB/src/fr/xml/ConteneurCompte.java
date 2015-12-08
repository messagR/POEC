/**
 * test
 */
package fr.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author PC
 *
 */
@XmlRootElement(name = "compte")
@XmlAccessorType(XmlAccessType.NONE)
public class ConteneurCompte implements Serializable {
	private static final long serialVersionUID = 1L;

	@XmlAttribute(required = true)
	private Integer numero;
	@XmlElement
	private Double solde;
	@XmlElement
	private String libelle;
	@XmlElement
	private Double seuil;
	@XmlElement
	private Double taux;
	@XmlAttribute
	private String type;

	public ConteneurCompte() {
		super();
	}

	public Integer getNumero() {
		return this.numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Double getSolde() {
		return this.solde;
	}

	public void setSolde(Double solde) {
		this.solde = solde;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Double getSeuil() {
		return this.seuil;
	}

	public void setSeuil(Double seuil) {
		this.seuil = seuil;
	}

	public Double getTaux() {
		return this.taux;
	}

	public void setTaux(Double taux) {
		this.taux = taux;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}
}

/**
 * test
 */
package fr.banque;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author PC
 *
 */
@XmlRootElement(name = "listeCompte")
@XmlAccessorType(XmlAccessType.NONE)
public class ListeCompte {

	@XmlElement(name = "compte")
	private List<Compte> listeCompte;

	@XmlAttribute(name = "size")
	private int nbComptes;

	ListeCompte() {
		super();
	}

	public void add(Compte compte) {
		if (this.listeCompte == null) {
			this.listeCompte = new ArrayList<Compte>();
		}
		this.listeCompte.add(compte);
		this.nbComptes++;
	}

	public List<Compte> getListeCompte() {
		return this.listeCompte;
	}

	public void setListeCompte(List<Compte> listeCompte) {
		this.listeCompte = listeCompte;
	}

	public int getNbComptes() {
		return this.nbComptes;
	}

	public void setNbComptes(int nbComptes) {
		this.nbComptes = nbComptes;
	}

}

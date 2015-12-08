/**
 * test
 */
package fr.banque;

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
@XmlRootElement(name = "listeClient")
@XmlAccessorType(XmlAccessType.NONE)
public class ListeClient {

	@XmlElement(name = "client")
	private List<Client> listeClient;

	@XmlAttribute
	private int nbClients;

	ListeClient() {
		super();
	}

	public List<Client> getListeClient() {
		return this.listeClient;
	}

	public void setListeClient(List<Client> listeClient) {
		this.listeClient = listeClient;
	}

	public int getNbClients() {
		return this.nbClients;
	}

	public void setNbClients(int nbClients) {
		this.nbClients = nbClients;
	}

}

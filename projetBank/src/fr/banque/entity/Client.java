package fr.banque.entity;

import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.banque.exception.BanqueException;

class Client extends Utilisateur implements IClient {
	private static final long serialVersionUID = 1L;
	private final static Logger LOG = LogManager.getLogger();

	private String adresse, telephone;
	private int age, codePostal, sexe;
	// private List<ICompte> comptes;
	private Map<Integer, ICompte> comptes;

	public final static int NB_COMPTE_MAX = 5;

	Client() {
		super();
	}

	Client(int id, String nom, String prenom, int age, String login, String password, String adresse, String telephone,
			int codePostal, int sexe, Date derniereConnection) {
		super(id, nom, prenom, login, password, derniereConnection);
		this.setAge(age);
		this.setAdresse(adresse);
		this.setTelephone(telephone);
		this.setCodePostal(codePostal);
		this.setSexe(sexe);
	}

	@Override
	public int getAge() {
		return this.age;
	}

	@Override
	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public ICompte[] getComptes() {
		ICompte[] comptes = new ICompte[Client.NB_COMPTE_MAX];
		// return this.comptes.toArray(comptes);
		if (this.comptes != null) {
			return this.comptes.values().toArray(comptes);
		} else {
			return null;
		}
	}

	@Override
	public void setComptes(ICompte[] comptes) {
		// this.comptes = Arrays.asList(comptes);
		int i = 0;
		while (i < comptes.length) {
			if (comptes[i] != null) {
				// this.comptes.add(comptes[i]);
				this.comptes.put(comptes[i].getNumero(), comptes[i]);
			}
			i++;
		}
	}

	@Override
	public String getAdresse() {
		return this.adresse;
	}

	@Override
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	@Override
	public String getTelephone() {
		return this.telephone;
	}

	@Override
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Override
	public int getCodePostal() {
		return this.codePostal;
	}

	@Override
	public void setCodePostal(int codePostal) {
		this.codePostal = codePostal;
	}

	@Override
	public int getSexe() {
		return this.sexe;
	}

	@Override
	public void setSexe(int sexe) {
		this.sexe = sexe;
	}

	public void setComptes(Map<Integer, ICompte> comptes) {
		this.comptes = comptes;
	}

	@Override
	public void ajouterCompte(ICompte unCompte) throws BanqueException {
		if (this.comptes == null) {
			// this.comptes = new ArrayList<ICompte>();
			this.comptes = new Hashtable<Integer, ICompte>();
		}
		if (Client.NB_COMPTE_MAX == this.comptes.size()) {
			throw new BanqueException(
					"Nombre de comptes maximum atteint pour le client n°" + this.getNumero());
		} else {
			if (this.comptes.containsKey(unCompte.getNumero())) {
				throw new BanqueException(
						"Le client n°" + this.getNumero() + " a deja un compte n°" + unCompte.getNumero());
			}
			// this.comptes.add(unCompte);
			this.comptes.put(unCompte.getNumero(), unCompte);
		}
	}

	@Override
	public ICompte getCompte(int numeroCompte) {
		// Iterator iterCompte = this.comptes.iterator();
		// while (iterCompte.hasNext()) {
		// ICompte compte = iterCompte.next();
		// if (compte.getNumero() == numeroCompte) {
		// return compte;
		// }
		// }
		ICompte resultat = this.comptes.get(numeroCompte);
		if (resultat == null) {
			Client.LOG.warn("Pas de compte ayant le numero " + numeroCompte + " pour le client " + this.getNumero());
		}
		return resultat;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.delete(builder.length() - 1, builder.length());
		builder.append("cp = ").append(this.getCodePostal()).append(", ");
		if (this.getAdresse() != null) {
			builder.append("adresse = ").append(this.getAdresse()).append(", ");
		}
		builder.append("telephone = ").append(this.getTelephone()).append(", ");
		builder.append("sexe = ");
		switch (this.getSexe()) {
		case 0:
			builder.append("Mascullin");
			break;
		case 1:
			builder.append("Feminin");
			break;
		default:
			builder.append("ne sais pas");
			break;
		}
		builder.append(", ");
		builder.append("age = ").append(this.getAge());
		if (this.getComptes() != null) {
			builder.append("\n         | ").append("comptes = ");
			// Iterator iterCompte = this.comptes.iterator();
			Iterator<ICompte> iterCompte = this.comptes.values().iterator();
			while (iterCompte.hasNext()) {
				ICompte compte = iterCompte.next();
				builder.append(compte.toString().split(" ", 2)[1]);
				if (iterCompte.hasNext()) {
					builder.append(", ");
				}
			}
		}
		builder.append("]");
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
		if (obj instanceof Client) {
			return super.equals(obj);
		}
		return false;
	}

}

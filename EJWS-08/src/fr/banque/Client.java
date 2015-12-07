/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package fr.banque;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Ceci est la classe Client. <br/>
 *
 * Le client possede comme attributs des types Object ainsi que des types
 * simples. <br/>
 */
public class Client implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nom;
	private String prenom;
	private int age;
	private int numero;
	// Toujours utiliser une Interface pour la declaration
	private Map<Integer, Compte> tabComptes;

	/**
	 * Constructeur de l'objet. <br/>
	 * Par defait le client aura un numero = -1 et un age de 0
	 */
	public Client() {
		this(-1, null, null, 0);
	}

	/**
	 * Constructeur de l'objet. <br/>
	 *
	 * @param unNumero
	 *            un numero
	 * @param unNom
	 *            le nom du client
	 * @param unPrenom
	 *            le prenom du client
	 * @param unAge
	 *            l'age du client
	 */
	public Client(int unNumero, String unNom, String unPrenom, int unAge) {
		super();
		this.setNom(unNom);
		this.setPrenom(unPrenom);
		this.setAge(unAge);
		// Par contre, pour l'instanciation, on choisit une classe (HashMap,
		// HashTable, TreeMap, ...)
		this.tabComptes = new HashMap<Integer, Compte>();
		this.setNumero(unNumero);
	}

	/**
	 * Retourne l'age du client. <br/>
	 *
	 * @return l'age du client
	 */
	public int getAge() {
		return this.age;
	}

	/**
	 * Retourne le nom du client. <br/>
	 *
	 * @return le nom du client
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * Retourne le prenom du client. <br/>
	 *
	 * @return le prenom du client
	 */
	public String getPrenom() {
		return this.prenom;
	}

	/**
	 * Fixe l'age du client. <br/>
	 *
	 * @param unAge
	 *            le nouvel age du client
	 */
	public void setAge(int unAge) {
		this.age = unAge;
	}

	/**
	 * Fixe le nom du client. <br/>
	 *
	 * @param unNom
	 *            le nouveau nom du client
	 */
	public void setNom(String unNom) {
		this.nom = unNom;
	}

	/**
	 * Fixe le numero du client. <br/>
	 * final = on ne peut pas la surcharger / refaire <br/>
	 *
	 * @param unNumero
	 *            le nouveau numero du client
	 */
	public final void setNumero(int unNumero) {
		this.numero = unNumero;
	}

	/**
	 * Retourne le numero du client. <br/>
	 *
	 * @return le numero du client, -1 si ce client n'a pas de numero
	 */
	public final int getNumero() {
		return this.numero;
	}

	/**
	 * Fixe le prenom du client. <br/>
	 *
	 * @param unPrenom
	 *            le nouveau prenom du client
	 */
	public void setPrenom(String unPrenom) {
		this.prenom = unPrenom;
	}

	/**
	 * Retourne tous les comptes du client sous forme d'iterateur. <br/>
	 *
	 * @return la liste des comptes du client
	 */
	public Compte[] getComptes() {
		return this.tabComptes.values().toArray(new Compte[this.tabComptes.size()]);
	}

	/**
	 * Retourne un compte particulier. <br/>
	 *
	 * @param unNumero
	 *            numero du compte
	 * @return le compte vise ou null si il n'existe pas
	 */
	public Compte getCompte(int unNumero) {
		return this.tabComptes.get(unNumero);
		// Ou, si on ne veut pas de boxing
		// return this.tabComptes.get(Integer.valueOf(unNumero));
	}

	/**
	 * Ajoute un compte dans la liste des comptes de l'utilisateur. <br/>
	 *
	 * @param unCompte
	 *            le compte a ajouter
	 */
	public void ajouterCompte(Compte unCompte) {
		if (unCompte != null) {
			this.tabComptes.put(unCompte.getNumero(), unCompte);
			// Ou, si on ne veut pas de boxing
			// this.tabComptes.put(Integer.valueOf(unCompte.getNumero()),
			// unCompte);
		}
	}

	/**
	 * Formatage du client sous forme de String utilisable directement par
	 * System.out.println(..);. <br/>
	 *
	 * La methode toString() est heritee de la classe java.lang.Object, elle est
	 * tres pratique quand on veut debuguer un programme. Elle est
	 * automatiquement appellee quand on fait de la concatenation entre chaines
	 * de characteres : "a"+12+"b"+monClient. <br/>
	 *
	 * @return une representation chainee de l'objet
	 */
	@Override
	public String toString() {
		// L'utilisation du '+' entre chaine de charactere n'est pas tres
		// optimise
		// Il est prefereble d'utiliser un StringBuffer pour fabriquer une
		// chaine
		// de charactere et eviter ainsi la lourdeur d'execution liee au '+'
		// entre
		// chaine de charactere
		StringBuffer sb = new StringBuffer();
		sb.append(this.getClass().getName());
		sb.append(" Nom: ");
		sb.append(this.getNom());
		sb.append(" Prenom: ");
		sb.append(this.getPrenom());
		sb.append(" Age: ");
		sb.append(this.getAge());
		sb.append("\n");
		for (Compte compte : this.tabComptes.values()) {
			if (compte != null) {
				sb.append(compte);
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	/**
	 * Indique si deux clients sont egaux. <br/>
	 *
	 * Deux clients sont egaux si ils ont le meme numero d'identification.
	 *
	 * @param obj
	 *            l'objet qui sera compare a this
	 * @return <code>true</code> si les deux sont egaux et <code>false</code>
	 *         sinon.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj instanceof Client) {
			Client c = (Client) obj;
			return this.getNumero() == c.getNumero();
		}
		return false;
	}
}
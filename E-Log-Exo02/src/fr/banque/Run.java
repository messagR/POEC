/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 *
 * exo 2
 */

package fr.banque;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.banque.entity.Client;
import fr.banque.entity.Compte;
import fr.banque.entity.CompteASeuil;
import fr.banque.entity.CompteRemunere;
import fr.banque.entity.ICompteASeuil;
import fr.banque.entity.ICompteRemunere;
import fr.banque.exception.BanqueException;

/**
 * La classe de lancement. <br/>
 */
public class Run {

	private final static Logger LOG = LogManager.getLogger(Run.class);

	/**
	 * Lancement des tests. <br>
	 *
	 * @param args
	 *            les arguments de lancement
	 */
	public static void main(String[] args) {

		Run.LOG.debug("-- Start--");

		Run.LOG.info("creation du client principale Mr Dupont ");
		// creation du client principale Mr Dupont
		Client client = new Client(1, "Dupont", "Henry", 28);
		// Creation des comptes
		Run.LOG.info("Creation des comptes");
		Compte c0 = new Compte(0, 200);
		Compte c1 = new Compte(1, 1000);
		CompteASeuil c2 = new CompteASeuil(2, 400, 100);
		CompteRemunere c3 = new CompteRemunere(3, 400, 0.10);
		CompteRemunere c4 = new CompteRemunere(4, 600, 0.50);
		client.ajouterCompte(c0);
		client.ajouterCompte(c1);
		client.ajouterCompte(c2);
		client.ajouterCompte(c3);
		client.ajouterCompte(c4);
		Run.LOG.debug("Voici mon client {}", client);
		// System.out.println(client);
		try {
			// Doit partir en exception
			Run.LOG.debug("Avant retirer de 500");
			client.getCompte(2).retirer(500);

			// System.out.println("On ne verra jamais ce texte, car on part dans
			// le catch a la ligne du dessus");
		} catch (BanqueException e) {
			Run.LOG.error("Erreur lors deu retier de 500 : {}", e.getMessage());
			// e.printStackTrace();
		}
		Run.LOG.debug("Le compte c2 = {}", c2);
		// System.out.println(c2);
		c3.verserInterets();
		Run.LOG.debug("Le compte c3 = {}", c3);

		// System.out.println(c3);

		// Polymorphisme
		ICompteASeuil c = new CompteASeuil(50, 200, 50);
		// Marche car, Un CompteASeuil 'est une forme de' ICompteASeuil
		// Mais impossible d'ecrire :
		// c.getSolde();
		// Car pour le compilateur c est un ICompteASeuil, il n'y a pas de
		// methode
		// getSolde dedans
		// il faut caster, pour forcer la main au compilateur
		((Compte) c).getSolde();

		// Versement des interets sur tous les comptes remuneres
		Compte[] cpts = client.getComptes();
		for (int i = 0; i < cpts.length; i++) {
			Compte compte = cpts[i];
			// On regarde si l'objet est 'une forme de'
			if (compte instanceof ICompteRemunere) {
				// avant de le caster en ce qui nous interesse
				// Afin de prendre tout ce qui est remunerable
				// on prend l'interface
				((ICompteRemunere) compte).verserInterets();
			}
		}

	}
}
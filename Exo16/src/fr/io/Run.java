/**
 * test
 */
package fr.io;

import java.io.IOException;

/**
 * @author PC
 *
 */
public class Run {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UtilisateurWriter utilisateurWriter = new UtilisateurWriter("liste.csv");
		NomPrenomReader nomPrenomReader = new NomPrenomReader("nom2.txt", "prenom.txt");
		try {
			utilisateurWriter.writeUtilisateur(nomPrenomReader.readNom(), nomPrenomReader.readPrenom(), 50);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

/**
 * test
 */
package fr.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author PC
 *
 */
public class NomPrenomReader {

	private String nomFichierNom, nomFichierPrenom;

	public NomPrenomReader() {
		this("", "");
	}

	public NomPrenomReader(String fichierNom, String fichierPrenom) {
		this.setNomFichierNom(fichierNom);
		this.setNomFichierPrenom(fichierPrenom);
	}

	/**
	 * @return the nomFichierNom
	 */
	public String getNomFichierNom() {
		return this.nomFichierNom;
	}

	/**
	 * @param nomFichierNom
	 *            the nomFichierNom to set
	 */
	public void setNomFichierNom(String nomFichierNom) {
		this.nomFichierNom = nomFichierNom;
	}

	/**
	 * @return the nomFichierPrenom
	 */
	public String getNomFichierPrenom() {
		return this.nomFichierPrenom;
	}

	/**
	 * @param nomFichierPrenom
	 *            the nomFichierPrenom to set
	 */
	public void setNomFichierPrenom(String nomFichierPrenom) {
		this.nomFichierPrenom = nomFichierPrenom;
	}

	public List<String> readNom() throws IOException {
		List<String> listeNom = new ArrayList<String>();
		File fichier = new File("C:/Users/PC/git/POEC/Exo16/src/" + this.getNomFichierNom());
		if (fichier.exists() && fichier.canRead()) {
			BufferedReader reader = null;
			try (FileReader fr = new FileReader(fichier)) {
				reader = new BufferedReader(fr);
				String ligne = null;
				while ((ligne = reader.readLine()) != null) {
					listeNom.add(ligne);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("Fichier '" + fichier + "' pas trouve");
		}
		return listeNom;
	}

	public List<String> readPrenom() throws IOException {
		List<String> listePrenom = new ArrayList<String>();
		File fichier = new File("C:/Users/PC/git/POEC/Exo16/src/" + this.getNomFichierPrenom());
		if (fichier.exists() && fichier.canRead()) {
			BufferedReader reader = null;
			try (FileReader fr = new FileReader(fichier)) {
				reader = new BufferedReader(fr);
				String ligne = null;
				while ((ligne = reader.readLine()) != null) {
					listePrenom.add(ligne);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("Fichier '" + fichier + "' pas trouve");
		}
		return listePrenom;
	}

	// /**
	// * @param args
	// */
	// public static void main(String[] args) {
	// NomPrenomReader nomPrenomReader = new NomPrenomReader("nom.txt",
	// "prenom.txt");
	// try {
	// List<String> liste = nomPrenomReader.readNom();
	// Iterator<String> iter = liste.iterator();
	// System.out.println("liste des noms : ");
	// while (iter.hasNext()) {
	// System.out.println("\t" + iter.next());
	// }
	// liste = nomPrenomReader.readPrenom();
	// iter = liste.iterator();
	// System.out.println("liste des prenoms : ");
	// while (iter.hasNext()) {
	// System.out.println("\t" + iter.next());
	// }
	//
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

}

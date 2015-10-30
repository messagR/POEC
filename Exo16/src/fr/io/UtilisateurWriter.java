/**
 * test
 */
package fr.io;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author PC
 *
 */
public class UtilisateurWriter {

	private String nomFichier;

	public UtilisateurWriter() {
		this("");
	}

	public UtilisateurWriter(String fichier) {
		this.setNomFichier(fichier);
	}

	/**
	 * @return the nomFichier
	 */
	public String getNomFichier() {
		return this.nomFichier;
	}

	/**
	 * @param nomFichier
	 *            the nomFichier to set
	 */
	public void setNomFichier(String nomFichier) {
		this.nomFichier = nomFichier;
	}

	public void writeUtilisateur(List<String> desNoms, List<String> desPrenoms, int combien) throws IOException {
		String genres[] = { "Mr", "Mm" };
		Random r = new Random();
		Collections.shuffle(desNoms);
		Collections.shuffle(desPrenoms);
		try (FileWriter fw = new FileWriter("C:/Users/PC/git/POEC/Exo16/src/" + this.getNomFichier())) {
			for (int i = 1; i <= combien; i++) {
				String genre = genres[r.nextInt(genres.length)];
				String nom = desNoms.get(r.nextInt(desNoms.size()));
				String prenom = desPrenoms.get(r.nextInt(desPrenoms.size()));
				String ligne = i + ";" + genre + ";" + nom + ";" + prenom;
				fw.write(ligne + "\n");
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}

	// /**
	// * @param args
	// */
	// public static void main(String[] args) {
	// UtilisateurWriter utilisateurWriter = new UtilisateurWriter("liste.csv");
	// NomPrenomReader nomPrenomReader = new NomPrenomReader("nom.txt",
	// "prenom.txt");
	// try {
	// utilisateurWriter.writeUtilisateur(nomPrenomReader.readNom(),
	// nomPrenomReader.readPrenom(), 10);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
}

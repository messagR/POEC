import java.util.Date;

public class Bonjour{

	/**
	 * commentaire <b>javadoc</b> : <br/>
	 *
	 * @author PC
	 * @param args tableau d'arguments demandes pour execution
	 */
	public static void main(String[] args) {
		System.out.println("Bonjour tout le monde"); // commentaire
		Date dateSys = new Date();
		java.sql.Date dateSQL = new java.sql.Date(0);
		System.out.println("Format date système : " + String.valueOf(dateSys));
		System.out.println("Format date SQL : " + String.valueOf(dateSQL));

	}

}
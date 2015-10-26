/**
 * test
 */
package projetBd;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import fr.banque.BanqueException;
import fr.banque.Factory;
import fr.banque.IClient;
import fr.banque.ICompte;
import fr.banque.ICompteASeuil;
import fr.banque.ICompteASeuilRemunere;
import fr.banque.ICompteRemunere;
import fr.banque.IOperation;

/**
 * @author PC
 *
 */
public class TestDB {

	public static Connection seConnecter(String unLogin, String unPassword, String uneUrl) throws SQLException {
		Connection connexion = null;
		String nomDuDriver = "com.mysql.jdbc.Driver";
		try {
			Class.forName(nomDuDriver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		connexion = DriverManager.getConnection(uneUrl, unLogin, unPassword);
		return connexion;
	}

	public static IClient recupererClient(String unNom, String unPrenom) {
		IClient client = null;
		Connection connexion = null;
		PreparedStatement ste = null;
		ResultSet resultat = null;
		Factory f = Factory.getInstance();

		try {
			connexion = TestDB.seConnecter("root", "root", "jdbc:mysql://localhost:3306/banque");
			String requete = "select *,  (DATE_FORMAT(FROM_DAYS(TO_DAYS(NOW())-TO_DAYS(dateDeNaissance)), '%Y')+0) as age from utilisateur where nom = ? and prenom = ?";
			ste = connexion.prepareStatement(requete);
			ste.setString(1, unNom);
			ste.setString(2, unPrenom);
			resultat = ste.executeQuery();
			if (resultat.next()) {
				int idclient = resultat.getInt("id");
				String nom = resultat.getString("nom");
				String prenom = resultat.getString("prenom");
				int age = resultat.getInt("age");
				String login = resultat.getString("login");
				String password = resultat.getString("password");
				String adresse = resultat.getString("adresse");
				String telephone = resultat.getString("telephone");
				int codePostal = resultat.getInt("codePostal");
				int sexe = resultat.getInt("sex");
				Date derniereConnection = resultat.getDate("derniereConnection");
				client = f.creerClient(idclient, nom, prenom, age, login, password, adresse, telephone, codePostal,
						sexe, derniereConnection);
				System.out.println(client);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (resultat != null) {
				try {
					resultat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (ste != null) {
				try {
					ste.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connexion != null) {
				try {
					connexion.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return client;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection connexion = null;
		PreparedStatement steClient = null;
		ResultSet resClient = null;
		Factory f = Factory.getInstance();
		PreparedStatement steCompte = null;
		ResultSet resCompte = null;
		PreparedStatement steOperation = null;
		ResultSet resOperation = null;

		try {
			connexion = TestDB.seConnecter("root", "root", "jdbc:mysql://localhost:3306/banque");
			String requete = "select *,  (DATE_FORMAT(FROM_DAYS(TO_DAYS(NOW())-TO_DAYS(dateDeNaissance)), '%Y')+0) as age from utilisateur";
			steClient = connexion.prepareStatement(requete);
			resClient = steClient.executeQuery();

			List listeClient = new ArrayList();
			while (resClient.next()) {
				int idclient = resClient.getInt("id");
				String nom = resClient.getString("nom");
				String prenom = resClient.getString("prenom");
				int age = resClient.getInt("age");
				String login = resClient.getString("login");
				String password = resClient.getString("password");
				String adresse = resClient.getString("adresse");
				String telephone = resClient.getString("telephone");
				int codePostal = resClient.getInt("codePostal");
				int sexe = resClient.getInt("sex");
				Date derniereConnection = resClient.getDate("derniereConnection");
				StringBuffer sb = new StringBuffer();
				sb.append(" id : " + idclient);
				sb.append(" nom : " + nom);
				sb.append(" prenom : " + prenom);
				sb.append(" age : " + age);
				System.out.println("utilisateurs : " + sb);
				IClient client = f.creerClient(idclient, nom, prenom, age, login, password, adresse, telephone,
						codePostal, sexe, derniereConnection);
				listeClient.add(client);

				requete = "select compte.* from utilisateur "
						+ "inner join compte on utilisateur.id = compte.utilisateurId where utilisateur.id = ?";
				steCompte = connexion.prepareStatement(requete);
				steCompte.setInt(1, client.getNumero());
				resCompte = steCompte.executeQuery();
				ICompte compte = null;
				while (resCompte.next()) {
					Class type = null;
					int idCompte = resCompte.getInt("id");
					String libelleCompte = resCompte.getString("libelle");
					double solde = resCompte.getDouble("solde");
					double seuil = resCompte.getDouble("decouvert");
					double taux = 0.0;
					if (resCompte.wasNull()) {
						taux = resCompte.getDouble("taux");
						if (resCompte.wasNull()) {
							type = ICompte.class;
							try {
								compte = f.creerCompte(type, idCompte, libelleCompte, solde);
							} catch (BanqueException e) {
								e.printStackTrace();
							}
						} else {
							type = ICompteRemunere.class;
							try {
								compte = f.creerCompte(type, idCompte, libelleCompte, solde, taux);
							} catch (BanqueException e) {
								e.printStackTrace();
							}
						}
					} else {
						taux = resCompte.getDouble("taux");
						if (resCompte.wasNull()) {
							type = ICompteASeuil.class;
							try {
								compte = f.creerCompte(type, idCompte, libelleCompte, solde, seuil);
							} catch (BanqueException e) {
								e.printStackTrace();
							}
						} else {
							type = ICompteASeuilRemunere.class;
							try {
								compte = f.creerCompte(type, idCompte, libelleCompte, solde, seuil, taux);
							} catch (BanqueException e) {
								e.printStackTrace();
							}
						}
					}
					sb = new StringBuffer();
					sb.append(" id : " + idCompte);
					sb.append(" libelle : " + libelleCompte);
					sb.append(" solde : " + solde);
					sb.append(" seuil : " + seuil);
					sb.append(" taux : " + taux);
					System.out.println("compte : " + sb);
					compte.setNumero(idCompte);
					try {
						client.ajouterCompte(compte);
					} catch (BanqueException e) {
						e.printStackTrace();
					}

					requete = "select op.* as id from utilisateur as util "
							+ "inner join compte as cpte on util.id = cpte.utilisateurId "
							+ "inner join operation as op on cpte.id = op.compteId where util.id = ? and cpte.id = ?";
					steOperation = connexion.prepareStatement(requete);
					steOperation.setInt(1, client.getNumero());
					steOperation.setInt(2, compte.getNumero());
					resOperation = steOperation.executeQuery();
					while (resOperation.next()) {
						int idOperation = resOperation.getInt("id");
						String libelleOperation = resCompte.getString("libelle");
						double montant = resOperation.getDouble("montant");
						Timestamp date = resOperation.getTimestamp("date");
						sb = new StringBuffer();
						sb.append(" id : " + idOperation);
						sb.append(" libelle : " + libelleOperation);
						sb.append(" montant : " + montant);
						sb.append(" date : " + date);
						System.out.println("operation : " + sb);
						IOperation operation = f.creerOperation(idOperation, libelleOperation, montant, date);
						operation.setNumero(idOperation);
						compte.ajouterOperation(operation);

					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (resClient != null) {
				try {
					resClient.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (steClient != null) {
				try {
					steClient.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (resCompte != null) {
				try {
					resCompte.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (steCompte != null) {
				try {
					steCompte.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connexion != null) {
				try {
					connexion.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}

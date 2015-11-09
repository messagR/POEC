/**
 * test
 */
package projetBd;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.banque.entity.Factory;
import fr.banque.entity.IClient;
import fr.banque.entity.ICompte;
import fr.banque.entity.ICompteASeuil;
import fr.banque.entity.ICompteASeuilRemunere;
import fr.banque.entity.ICompteRemunere;
import fr.banque.entity.IOperation;
import fr.banque.exception.BanqueException;
import fr.banque.exception.ChampsVidesException;
import fr.banque.exception.ClientIntrouvableException;
import fr.banque.exception.CompteIntrouvableException;
import fr.banque.log.CustomMessage;

/**
 * @author PC
 *
 */
public class AccesDB {
	private final static Logger LOG = LogManager.getLogger();

	private static Connection connexion;
	// private final static String DRIVER = "com.mysql.jdbc.Driver";

	// AccesDB() {
	// try {
	// Class.forName(UtilDB.DRIVER);
	// } catch (ClassNotFoundException e) {
	// e.printStackTrace();
	// System.exit(-1);
	// }
	// }

	public AccesDB(String driver) throws SQLException {
		try {
			Class.forName(driver);
		} catch (Throwable cnf) {
			throw new SQLException("Impossible de charger le driver '" + driver + "' " + cnf);
		}
	}

	public void seConnecter(String unLogin, String unPassword, String uneUrl) {
		try {
			AccesDB.connexion = DriverManager.getConnection(uneUrl, unLogin, unPassword);
		} catch (SQLException e) {
			AccesDB.LOG.error("Erreur lors de la creation de la connection ", e);
		}
	}

	public void seDeconnecter() {
		if (AccesDB.connexion != null) {
			try {
				AccesDB.connexion.close();
			} catch (SQLException e) {
				AccesDB.LOG.error("Erreur lors de la connection", e);
			}
		}
	}

	public List<IClient> listeClient() {

		Factory f = Factory.getInstance();
		List<IClient> listeClient = new ArrayList<IClient>();
		PreparedStatement steClient = null;
		ResultSet resClient = null;
		PreparedStatement steCompte = null;
		ResultSet resCompte = null;
		PreparedStatement steOperation = null;
		ResultSet resOperation = null;
		try {
			String requete = "select *,	(DATE_FORMAT(FROM_DAYS(TO_DAYS(NOW())-TO_DAYS(dateDeNaissance)),'%Y')+0) as age "
					+ "from utilisateur";
			steClient = AccesDB.connexion.prepareStatement(requete);
			resClient = steClient.executeQuery();

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
				Date derniereConnection = new Date();
				if (resClient.getTimestamp("derniereConnection") != null) {
					derniereConnection.setTime(resClient.getTimestamp("derniereConnection").getTime());
				}
				IClient client = f.creerClient(idclient, nom, prenom, age, login, password, adresse, telephone,
						codePostal, sexe, derniereConnection);
				listeClient.add(client);

				requete = "select compte.* from utilisateur "
						+ "inner join compte on utilisateur.id = compte.utilisateurId " + "where utilisateur.id = ?";
				steCompte = AccesDB.connexion.prepareStatement(requete);
				steCompte.setInt(1, client.getNumero());
				resCompte = steCompte.executeQuery();
				ICompte compte = null;
				while (resCompte.next()) {
					Class<?> type = null;
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
								AccesDB.LOG.error(new CustomMessage(CustomMessage.TypeCible.COMPTE, idCompte,
										"Erreur lors de la creation du compte ", e));
							}
						} else {
							type = ICompteRemunere.class;
							try {
								compte = f.creerCompte(type, idCompte, libelleCompte, solde, taux);
							} catch (BanqueException e) {
								AccesDB.LOG.error(new CustomMessage(CustomMessage.TypeCible.COMPTE, idCompte,
										"Erreur lors de la creation du compte ", e));
							}
						}
					} else {
						taux = resCompte.getDouble("taux");
						if (resCompte.wasNull()) {
							type = ICompteASeuil.class;
							try {
								compte = f.creerCompte(type, idCompte, libelleCompte, solde, seuil);
							} catch (BanqueException e) {
								AccesDB.LOG.error(new CustomMessage(CustomMessage.TypeCible.COMPTE, idCompte,
										"Erreur lors de la creation du compte ", e));
							}
						} else {
							type = ICompteASeuilRemunere.class;
							try {
								compte = f.creerCompte(type, idCompte, libelleCompte, solde, seuil, taux);
							} catch (BanqueException e) {
								AccesDB.LOG.error(new CustomMessage(CustomMessage.TypeCible.COMPTE, idCompte,
										"Erreur lors de la creation du compte ", e));
							}
						}
					}
					try {
						client.ajouterCompte(compte);
					} catch (BanqueException e) {
						AccesDB.LOG.error(new CustomMessage(CustomMessage.TypeCible.CLIENT, client.getNumero(),
								"Erreur lors de l'ajout du compte " + compte.getNumero(), e));
					}

					requete = "select op.* from utilisateur as util "
							+ "inner join compte as cpte on util.id = cpte.utilisateurId "
							+ "inner join operation as op on cpte.id = op.compteId "
							+ "where util.id = ? and cpte.id = ?";
					steOperation = AccesDB.connexion.prepareStatement(requete);
					steOperation.setInt(1, client.getNumero());
					steOperation.setInt(2, compte.getNumero());
					resOperation = steOperation.executeQuery();
					while (resOperation.next()) {
						int idOperation = resOperation.getInt("id");
						String libelleOperation = resCompte.getString("libelle");
						double montant = resOperation.getDouble("montant");
						Timestamp date = resOperation.getTimestamp("date");
						java.util.Date dateOp = new java.util.Date();
						dateOp.setTime(date.getTime());
						IOperation operation = f.creerOperation(idOperation, libelleOperation, montant, dateOp);
						compte.ajouterOperation(operation);

					}
				}
			}
		} catch (SQLException e) {
			AccesDB.LOG.error("Erreur lors du listing client", e);
		} finally {
			if (resClient != null) {
				try {
					resClient.close();
				} catch (SQLException e) {
					AccesDB.LOG.error("Erreur lors de la fermeture du resultat", e);
				}
			}
			if (steClient != null) {
				try {
					steClient.close();
				} catch (SQLException e) {
					AccesDB.LOG.error("Erreur lors de la fermeture du statement", e);
				}
			}
			if (resCompte != null) {
				try {
					resCompte.close();
				} catch (SQLException e) {
					AccesDB.LOG.error("Erreur lors de la fermeture du resultat", e);
				}
			}
			if (steCompte != null) {
				try {
					steCompte.close();
				} catch (SQLException e) {
					AccesDB.LOG.error("Erreur lors de la fermeture du statement", e);
				}
			}
		}
		return listeClient;
	}

	public void afficherNomPrenomUtilisateur() {

		PreparedStatement steClient = null;
		ResultSet resClient = null;
		try {
			String requete = "select nom, prenom " + "from utilisateur";
			steClient = AccesDB.connexion.prepareStatement(requete);
			resClient = steClient.executeQuery();

			while (resClient.next()) {
				String nom = resClient.getString("nom");
				String prenom = resClient.getString("prenom");
				System.out.println(nom + " - " + prenom);

			}

		} catch (SQLException e) {
			AccesDB.LOG.error("Erreur lors de la recuperation utilisateur", e);
		} finally {
			if (resClient != null) {
				try {
					resClient.close();
				} catch (SQLException e) {
					AccesDB.LOG.error("Erreur lors de la fermeture du resultat", e);
				}
			}
			if (steClient != null) {
				try {
					steClient.close();
				} catch (SQLException e) {
					AccesDB.LOG.error("Erreur lors de la fermeture du statement", e);
				}
			}
		}
	}

	public boolean verifieCompteUtilisateur(int userID, int compteID) throws CompteIntrouvableException {
		boolean retour = true;

		PreparedStatement ste = null;
		ResultSet res = null;
		try {
			String requete = "select * from compte where utilisateurId = ? and id = ?";
			ste = AccesDB.connexion.prepareStatement(requete);
			ste.setInt(1, userID);
			ste.setInt(2, compteID);
			res = ste.executeQuery();

			if (!res.next()) {
				retour = false;
				throw new CompteIntrouvableException(
						"Le compte n°" + compteID + " n'appartient pas au client n°" + userID);
			}

		} catch (SQLException e) {
			AccesDB.LOG.error("Erreur lors de la recuperation du compte", e);
		} finally {
			if (res != null) {
				try {
					res.close();
				} catch (SQLException e) {
					AccesDB.LOG.error("Erreur lors de la fermeture du resultat", e);
				}
			}
			if (ste != null) {
				try {
					ste.close();
				} catch (SQLException e) {
					AccesDB.LOG.error("Erreur lors de la fermeture du statement", e);
				}
			}
		}
		return retour;
	}

	public IClient authentifier(String login, String password)
			throws SQLException,
			ClientIntrouvableException, ChampsVidesException {
		IClient client = null;

		if (((login == null) || (login == "")) && ((password == null) || (password == ""))) {
			throw new ChampsVidesException("Login et mot de passe non renseignes");
		}
		if ((login == null) || (login == "")) {
			throw new ChampsVidesException("Login non renseigne");
		}
		if ((password == null) || (password == "")) {
			throw new ChampsVidesException("Mot de passe non renseigne");
		}

		PreparedStatement ste = null;
		ResultSet resultat = null;
		Factory f = Factory.getInstance();

		try {
			String requete = "select *,  (DATE_FORMAT(FROM_DAYS(TO_DAYS(NOW())-TO_DAYS(dateDeNaissance)), '%Y')+0) as age "
					+ "from utilisateur where login = ? and password = ?";
			ste = AccesDB.connexion.prepareStatement(requete);
			ste.setString(1, login);
			ste.setString(2, password);
			resultat = ste.executeQuery();
			if (resultat.next()) {
				int idclient = resultat.getInt("id");
				String nom = resultat.getString("nom");
				String prenom = resultat.getString("prenom");
				int age = resultat.getInt("age");
				String adresse = resultat.getString("adresse");
				String telephone = resultat.getString("telephone");
				int codePostal = resultat.getInt("codePostal");
				int sexe = resultat.getInt("sex");
				Date derniereConnection = new Date();
				if (resultat.getTimestamp("derniereConnection") != null) {
					derniereConnection.setTime(resultat.getTimestamp("derniereConnection").getTime());
				}
				client = f.creerClient(idclient, nom, prenom, age, login, password, adresse, telephone, codePostal,
						sexe, derniereConnection);
			} else {
				throw new ClientIntrouvableException("Erreur d'authentification");
			}
		} catch (SQLException e) {
			// AccesDB.LOG.error("Erreur lors de l'authentification", e);
			throw new SQLException(e);
		} finally {
			if (resultat != null) {
				try {
					resultat.close();
				} catch (SQLException e) {
					AccesDB.LOG.error("Erreur lors de la fermeture du resultat", e);
				}
			}
			if (ste != null) {
				try {
					ste.close();
				} catch (SQLException e) {
					AccesDB.LOG.error("Erreur lors de la fermeture du statement", e);
				}
			}
		}
		return client;
	}

	public IClient recupererClient(String unNom, String unPrenom)
			throws SQLException, ChampsVidesException, ClientIntrouvableException {
		IClient client = null;

		if (((unNom == null) || (unNom == "")) && ((unPrenom == null) || (unPrenom == ""))) {
			throw new ChampsVidesException("Nom et prenom non renseignes");
		}
		if (unNom == null) {
			unNom = "";
		}
		if (unPrenom == null) {
			unPrenom = "";
		}

		PreparedStatement ste = null;
		ResultSet resultat = null;
		Factory f = Factory.getInstance();

		try {
			String requete = "select *, (DATE_FORMAT(FROM_DAYS(TO_DAYS(NOW())-TO_DAYS(dateDeNaissance)), '%Y')+0) as age from utilisateur where nom = ? and prenom = ?";
			ste = AccesDB.connexion.prepareStatement(requete);
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
				Date derniereConnection = new Date();
				if (resultat.getTimestamp("derniereConnection") != null) {
					derniereConnection.setTime(resultat.getTimestamp("derniereConnection").getTime());
				}
				client = f.creerClient(idclient, nom, prenom, age, login, password, adresse, telephone, codePostal,
						sexe, derniereConnection);
			} else {
				throw new ClientIntrouvableException("Client introuvable");
			}
		} catch (SQLException e) {
			// AccesDB.LOG.error("Erreur lors de la recuperation client", e);
			throw new SQLException(e);
		} finally {
			if (resultat != null) {
				try {
					resultat.close();
				} catch (SQLException e) {
					AccesDB.LOG.error("Erreur lors de la fermeture du resultat", e);
				}
			}
			if (ste != null) {
				try {
					ste.close();
				} catch (SQLException e) {
					AccesDB.LOG.error("Erreur lors de la fermeture du statement", e);
				}
			}
		}
		return client;

	}

	public List<ICompte> listeCompte(int userId)
			throws SQLException, ClientIntrouvableException {
		if (userId <= 0) {
			throw new ClientIntrouvableException("Id client invalide");
		}

		List<ICompte> listeCompte = new ArrayList<ICompte>();
		PreparedStatement ste = null;
		ResultSet resultat = null;
		Factory f = Factory.getInstance();

		try {
			String requete = "select * "
					+ "from compte where utilisateurId = ?";
			ste = AccesDB.connexion.prepareStatement(requete);
			ste.setInt(1, userId);
			resultat = ste.executeQuery();
			while (resultat.next()) {
				ICompte compte = null;
				Class<?> type = null;
				int idCompte = resultat.getInt("id");
				String libelleCompte = resultat.getString("libelle");
				double solde = resultat.getDouble("solde");
				double seuil = resultat.getDouble("decouvert");
				double taux = 0.0;
				if (resultat.wasNull()) {
					taux = resultat.getDouble("taux");
					if (resultat.wasNull()) {
						type = ICompte.class;
						try {
							compte = f.creerCompte(type, idCompte, libelleCompte, solde);
						} catch (BanqueException e) {
							AccesDB.LOG.error(new CustomMessage(CustomMessage.TypeCible.COMPTE, idCompte,
									"Erreur lors de la creation du compte ", e));
						}
					} else {
						type = ICompteRemunere.class;
						try {
							compte = f.creerCompte(type, idCompte, libelleCompte, solde, taux);
						} catch (BanqueException e) {
							AccesDB.LOG.error(new CustomMessage(CustomMessage.TypeCible.COMPTE, idCompte,
									"Erreur lors de la creation du compte ", e));
						}
					}
				} else {
					taux = resultat.getDouble("taux");
					if (resultat.wasNull()) {
						type = ICompteASeuil.class;
						try {
							compte = f.creerCompte(type, idCompte, libelleCompte, solde, seuil);
						} catch (BanqueException e) {
							AccesDB.LOG.error(new CustomMessage(CustomMessage.TypeCible.COMPTE, idCompte,
									"Erreur lors de la creation du compte ", e));
						}
					} else {
						type = ICompteASeuilRemunere.class;
						try {
							compte = f.creerCompte(type, idCompte, libelleCompte, solde, seuil, taux);
						} catch (BanqueException e) {
							AccesDB.LOG.error(new CustomMessage(CustomMessage.TypeCible.COMPTE, idCompte,
									"Erreur lors de la creation du compte ", e));
						}
					}
				}
				listeCompte.add(compte);
			}
		} catch (SQLException e) {
			AccesDB.LOG.error("Erreur lors de listing de compte", e);
		} finally {
			if (resultat != null) {
				try {
					resultat.close();
				} catch (SQLException e) {
					AccesDB.LOG.error("Erreur lors de la fermeture du resultat", e);
				}
			}
			if (ste != null) {
				try {
					ste.close();
				} catch (SQLException e) {
					AccesDB.LOG.error("Erreur lors de la fermeture du statement", e);
				}
			}
		}
		return listeCompte;
	}

	public void afficherIdComptesUtilisateur(int userId) throws SQLException, ClientIntrouvableException {
		if (userId <= 0) {
			throw new ClientIntrouvableException("Id client invalide");
		}

		PreparedStatement ste = null;
		ResultSet resultat = null;

		try {
			String requete = "select id " + "from compte where utilisateurId = ?";
			ste = AccesDB.connexion.prepareStatement(requete);
			ste.setInt(1, userId);
			resultat = ste.executeQuery();
			while (resultat.next()) {
				int idCompte = resultat.getInt("id");
				System.out.println(idCompte);
			}
		} catch (SQLException e) {
			AccesDB.LOG.error("Erreur lors de la recuperation du client", e);
		} finally {
			if (resultat != null) {
				try {
					resultat.close();
				} catch (SQLException e) {
					AccesDB.LOG.error("Erreur lors de la fermeture du resultat", e);
				}
			}
			if (ste != null) {
				try {
					ste.close();
				} catch (SQLException e) {
					AccesDB.LOG.error("Erreur lors de la fermeture du statement", e);
				}
			}
		}
	}

	public ICompte rechercherCompte(int id) throws SQLException, CompteIntrouvableException {
		ICompte compte = null;
		if (id <= 0) {
			throw new CompteIntrouvableException("Id compte invalide");
		}

		PreparedStatement ste = null;
		ResultSet resultat = null;
		Factory f = Factory.getInstance();
		try {
			String requete = "select * from compte where id = ?";
			ste = AccesDB.connexion.prepareStatement(requete);
			ste.setInt(1, id);
			resultat = ste.executeQuery();
			if (resultat.next()) {
				Class<?> type = null;
				int idCompte = resultat.getInt("id");
				String libelleCompte = resultat.getString("libelle");
				double solde = resultat.getDouble("solde");
				double seuil = resultat.getDouble("decouvert");
				double taux = 0.0;
				if (resultat.wasNull()) {
					taux = resultat.getDouble("taux");
					if (resultat.wasNull()) {
						type = ICompte.class;
						try {
							compte = f.creerCompte(type, idCompte, libelleCompte, solde);
						} catch (BanqueException e) {
							AccesDB.LOG.error(new CustomMessage(CustomMessage.TypeCible.COMPTE, idCompte,
									"Erreur lors de la creation du compte ", e));
						}
					} else {
						type = ICompteRemunere.class;
						try {
							compte = f.creerCompte(type, idCompte, libelleCompte, solde, taux);
						} catch (BanqueException e) {
							AccesDB.LOG.error(new CustomMessage(CustomMessage.TypeCible.COMPTE, idCompte,
									"Erreur lors de la creation du compte ", e));
						}
					}
				} else {
					taux = resultat.getDouble("taux");
					if (resultat.wasNull()) {
						type = ICompteASeuil.class;
						try {
							compte = f.creerCompte(type, idCompte, libelleCompte, solde, seuil);
						} catch (BanqueException e) {
							AccesDB.LOG.error(new CustomMessage(CustomMessage.TypeCible.COMPTE, idCompte,
									"Erreur lors de la creation du compte ", e));
						}
					} else {
						type = ICompteASeuilRemunere.class;
						try {
							compte = f.creerCompte(type, idCompte, libelleCompte, solde, seuil, taux);
						} catch (BanqueException e) {
							AccesDB.LOG.error(new CustomMessage(CustomMessage.TypeCible.COMPTE, idCompte,
									"Erreur lors de la creation du compte ", e));
						}
					}
				}
			} else {
				throw new CompteIntrouvableException("Compte introuvable");
			}
		} catch (SQLException e) {
			AccesDB.LOG.error("Erreur lors de la recuperation du compte", e);
		} finally {
			if (resultat != null) {
				try {
					resultat.close();
				} catch (SQLException e) {
					AccesDB.LOG.error("Erreur lors de la fermeture du resultat", e);
				}
			}
			if (ste != null) {
				try {
					ste.close();
				} catch (SQLException e) {
					AccesDB.LOG.error("Erreur lors de la fermeture du statement", e);
				}
			}
		}
		return compte;
	}

	/**
	 * @param cpID
	 * @param args
	 *            (datedeb, datefin, creditdebit -> true: > 0, false < 0)
	 * @return
	 * @throws SQLException
	 * @throws CompteIntrouvableException
	 */
	public List<IOperation> rechercherOp(int cpID, Object... args)
			throws SQLException, CompteIntrouvableException {
		PreparedStatement ste = null;
		ResultSet resultat = null;
		Factory f = Factory.getInstance();
		List<IOperation> listeOperation = null;

		try {
			ICompte compte = this.rechercherCompte(cpID);
			if (compte == null) {
				throw new CompteIntrouvableException("Compte introuvable");
			}
			listeOperation = new ArrayList<IOperation>();
			String requete = "select * from operation where compteId = ?";
			List<Date> listeTrous = new ArrayList<Date>();
			switch (args.length) {
			case 1:
				if (args[0] instanceof Date) {
					requete += " and date >= ? ";
					listeTrous.add((Date) args[0]);
				}
				break;
			case 2:
				if (args[0] instanceof Date) {
					requete += " and date >= ? ";
					listeTrous.add((Date) args[0]);
				}
				if (args[1] instanceof Date) {
					requete += " and date <= ? ";
					listeTrous.add((Date) args[1]);
				}
				break;
			case 3:
				if (args[0] instanceof Date) {
					requete += " and date >= ? ";
					listeTrous.add((Date) args[0]);
				}
				if (args[1] instanceof Date) {
					requete += " and date <= ? ";
					listeTrous.add((Date) args[1]);
				}
				if (args[2] instanceof Boolean) {
					if ((Boolean) args[2]) {
						requete += " and montant > 0 ";
					} else {
						requete += " and montant < 0 ";
					}
				}
				break;
			}
			requete += " order by date DESC";
			ste = AccesDB.connexion.prepareStatement(requete);
			ste.setInt(1, compte.getNumero());
			int i = 0;
			while (i < listeTrous.size()) {
				if (listeTrous.get(i) != null) {
					ste.setTimestamp(i + 2, new Timestamp(listeTrous.get(i).getTime()));
				}
				i++;
			}
			resultat = ste.executeQuery();
			while (resultat.next()) {
				int idOperation = resultat.getInt("id");
				String libelleOperation = resultat.getString("libelle");
				double montant = resultat.getDouble("montant");
				Date dateOp = new Date();
				if (resultat.getTimestamp("date") != null) {
					dateOp.setTime(resultat.getTimestamp("date").getTime());
				}
				IOperation operation = f.creerOperation(idOperation, libelleOperation, montant, dateOp);
				listeOperation.add(operation);
			}
		} catch (SQLException e) {
			AccesDB.LOG.error("Erreur lors de la recuperation des operations", e);
		} finally {
			if (resultat != null) {
				try {
					resultat.close();
				} catch (SQLException e) {
					AccesDB.LOG.error("Erreur lors de la fermeture du resultat", e);
				}
			}
			if (ste != null) {
				try {
					ste.close();
				} catch (SQLException e) {
					AccesDB.LOG.error("Erreur lors de la fermeture du statement", e);
				}
			}
		}

		return listeOperation;
	}

	public void afficherIdOperationComptes(int cpID) throws SQLException, CompteIntrouvableException {
		PreparedStatement ste = null;
		ResultSet resultat = null;

		try {
			ICompte compte = this.rechercherCompte(cpID);
			if (compte == null) {
				throw new CompteIntrouvableException("Compte introuvable");
			}
			String requete = "select * from operation where compteId = ?";
			ste = AccesDB.connexion.prepareStatement(requete);
			ste.setInt(1, compte.getNumero());
			resultat = ste.executeQuery();
			while (resultat.next()) {
				int idOperation = resultat.getInt("id");
				System.out.println(idOperation);
			}
		} catch (SQLException e) {
			AccesDB.LOG.error("Erreur lors de la recuperation de l'operation", e);
		} finally {
			if (resultat != null) {
				try {
					resultat.close();
				} catch (SQLException e) {
					AccesDB.LOG.error("Erreur lors de la fermeture du resultat", e);
				}
			}
			if (ste != null) {
				try {
					ste.close();
				} catch (SQLException e) {
					AccesDB.LOG.error("Erreur lors de la fermeture du statement", e);
				}
			}
		}
	}

	public boolean faireVirement(int cpSrc, int cpDest, double som)
			throws SQLException, BanqueException, IllegalArgumentException, CompteIntrouvableException {
		if (som <= 0) {
			throw new IllegalArgumentException("Somme incorrecte");
		}

		ICompte compteSrc = this.rechercherCompte(cpSrc);
		ICompte compteDest = this.rechercherCompte(cpDest);
		try {
			compteSrc.retirer(som);
		} catch (BanqueException e) {
			throw new BanqueException("Virement impossible");
		}

		PreparedStatement ste = null;
		Factory f = Factory.getInstance();
		Date date = new Date();
		try {
			AccesDB.connexion.setAutoCommit(false);
			String requete = "INSERT INTO operation (libelle, montant, date, compteId) "
					+ "VALUES(?, ?, ?, ?)";
			ste = AccesDB.connexion.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			ste.setString(1, "Virement vers compte " + cpDest);
			ste.setDouble(2, (som * -1));
			ste.setTimestamp(3, new Timestamp(date.getTime()));
			ste.setDouble(4, cpSrc);
			ste.executeUpdate();
			ResultSet resultat = ste.getGeneratedKeys();
			resultat.next();
			if (ste != null) {
				try {
					ste.close();
				} catch (SQLException e) {
					AccesDB.LOG.error("Erreur lors de la fermeture du statement", e);
				}
			}

			requete = "UPDATE compte SET solde = ? WHERE id = ?";
			ste = AccesDB.connexion.prepareStatement(requete);
			ste.setDouble(1, compteSrc.getSolde());
			ste.setInt(2, compteSrc.getNumero());
			ste.executeUpdate();
			if (ste != null) {
				try {
					ste.close();
				} catch (SQLException e) {
					AccesDB.LOG.error("Erreur lors de la fermeture du statement", e);
				}
			}

			requete = "INSERT INTO operation (libelle, montant, date, compteId) " + "VALUES (?, ?, ?, ?)";
			ste = AccesDB.connexion.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			ste.setString(1, "Virement du compte " + cpSrc);
			ste.setDouble(2, som);
			ste.setTimestamp(3, new Timestamp(date.getTime()));
			ste.setDouble(4, cpDest);
			ste.executeUpdate();
			resultat = ste.getGeneratedKeys();
			resultat.next();
			if (ste != null) {
				try {
					ste.close();
				} catch (SQLException e) {
					AccesDB.LOG.error("Erreur lors de la fermeture du statement", e);
				}
			}

			requete = "UPDATE compte SET solde = ? WHERE id = ?";
			ste = AccesDB.connexion.prepareStatement(requete);
			ste.setDouble(1, compteDest.getSolde() + som);
			ste.setInt(2, compteDest.getNumero());
			ste.executeUpdate();

			AccesDB.connexion.commit();
		} catch (SQLException e) {
			try {
				AccesDB.connexion.rollback();
			} catch (SQLException e1) {
				AccesDB.LOG.error("Erreur lors de l'annulation de la sauvegarde de la base", e1);
			}
			AccesDB.LOG.error("Erreur lors du virement", e);
			throw new SQLException(e);
		} finally {
			if (ste != null) {
				try {
					ste.close();
				} catch (SQLException e) {
					AccesDB.LOG.error("Erreur lors de la fermeture du statement", e);
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {

		Properties mesProperties = new Properties();
		// chemin a partir du src
		try (InputStream is = ClassLoader.getSystemResourceAsStream("mesPreferences.properties")) {
			mesProperties.load(is);
		} catch (IOException e) {
			AccesDB.LOG.error("Erreur lors de la recuperation du fichier mesPreferences.properties ", e);
		}
		// File fichier = new
		// File("C:/Users/PC/git/POEC/projetBd/src/mesPreferences.properties");
		// if (fichier.exists() && fichier.canRead()) {
		// try (FileReader fr = new FileReader(fichier)) { // qu'a partir du
		// // java 7
		// mesProperties.load(fr);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// } else {
		// System.err.println("Fichier '" + fichier + "' pas trouve");
		// }

		// Nom du driver pour acceder a la base de donnee.
		// Lire la documentation associee a sa base de donnees pour le connaitre
		final String utilDbDriver = mesProperties.getProperty("utilDb.driver");
		// url d'acces a la base de donnees
		final String utilDbUrl = mesProperties.getProperty("utilDb.url");
		// login d'acces a la base de donnees
		final String utilDbLogin = mesProperties.getProperty("utilDb.login");
		// mot de passe d'acces a la base de donnees
		final String utilDbPassword = mesProperties.getProperty("utilDb.password");

		mesProperties.setProperty("une.nouvelle.clef", "bonjour");
		// pour sauvegarder
		try {
			FileWriter fw = new FileWriter("c:/Temp/Test");
			mesProperties.store(fw, "ajout clef");
		} catch (IOException e2) {
			AccesDB.LOG.error("Erreur lors de la creation du fichier Test ", e2);
		}
		Properties ps = System.getProperties();
		Iterator<Map.Entry<Object, Object>> iter = ps.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<Object, Object> entry = iter.next();
			System.out.println(entry.getKey() + "=" + entry.getValue());
		}

		AccesDB utilDb = null;
		try {
			utilDb = new AccesDB(utilDbDriver);
			utilDb.seConnecter(utilDbLogin, utilDbPassword, utilDbUrl);
			System.out.println("Authentification client");
			utilDb.authentifier("df", "df");
			System.out.println("\nRecuperation des infos de Fargis Denis");
			System.out.println(utilDb.recupererClient("Fargis", "Denis"));
			System.out.println("\nListe des comptes du client 1");
			List<ICompte> lCompte = utilDb.listeCompte(1);
			Iterator<ICompte> iterCompte = lCompte.iterator();
			while (iterCompte.hasNext()) {
				ICompte compte = iterCompte.next();
				System.out.println(compte);
			}
			System.out.println("\nRecuperation des infos du compte 12");
			System.out.println(utilDb.rechercherCompte(12));
			System.out.println("\nRecuperation des infos des operations du compte 12");
			System.out.println(utilDb.rechercherOp(1, 12));
			System.out.println("\nFaire un virement de 50 du compte 15 au 12");
			if (utilDb.faireVirement(15, 12, 50d)) {
				System.out.println("Virement effectue");
			}
			System.out.println("\nTous les noms,prenoms utilisateurs");
			utilDb.afficherNomPrenomUtilisateur();
			System.out.println("\nComptes du client 1");
			utilDb.afficherIdComptesUtilisateur(1);
			System.out.println("\nOperations du comptes 12");
			utilDb.afficherIdOperationComptes(12);

			// En Mapping maintenant
			System.out.println("\nListe des clients");
			List<IClient> lClient = utilDb.listeClient();
			Iterator<IClient> iterClient = lClient.iterator();
			while (iterClient.hasNext()) {
				IClient client = iterClient.next();
				System.out.println(client);
			}


		} catch (SQLException e1) {
			AccesDB.LOG.error("Erreur SQL", e1);
		} catch (ClientIntrouvableException e) {
			AccesDB.LOG.error("Erreur ", e);
		} catch (ChampsVidesException e) {
			AccesDB.LOG.error("Erreur ", e);
		} catch (CompteIntrouvableException e) {
			AccesDB.LOG.error("Erreur ", e);
		} catch (IllegalArgumentException e) {
			AccesDB.LOG.error("Erreur lors du virement ", e);
		} catch (BanqueException e) {
			AccesDB.LOG.error("Erreur lors du virement ", e);
		} finally {
			if (utilDb != null) {
				utilDb.seDeconnecter();
			}
		}
	}
}

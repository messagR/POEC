/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package fr.bd;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import fr.banque.Client;
import fr.banque.Compte;
import fr.banque.CompteASeuil;
import fr.banque.CompteASeuilRemunere;
import fr.banque.CompteRemunere;
import fr.banque.Operation;

/**
 * Cette classe fait un acces a une base de donnees. <br/>
 *
 * L'utilisation de JDBC se fait toujours de la meme maniere :
 * <ul>
 * <li>On charge le driver de la base de donnees</li>
 * <li>On demande au DriverManager un objet Connection</li>
 * <li>On demande a l'objet Connection un objet Statement (ou equivalent)</li>
 * <li>On demande a l'objet Statement d'executer la requete SQL</li>
 * <li>On recupere le resultat de la requete dans un objet ResultSet</li>
 * <li>Apres avoir fait le necessaire avec l'objet ResultSet on
 * <ul>
 * <li>ferme l'objet ResultSet</li>
 * <li>ferme l'objet Statement</li>
 * <li>ferme l'objet Connexion</li>
 * </ul>
 * </li>
 * </ul>
 */
public class AccesBD {

	private Connection cxt;

	/**
	 * Constructeur de la classe qui fournit une connection. <br/>
	 *
	 * C'est dans cette methode qu'est charge le driver. <br/>
	 *
	 * @param aDriverName
	 *            nom du Driver a charger
	 * @throws SQLException
	 *             si une erreur survient
	 */
	public AccesBD(String aDriverName) throws SQLException {
		try {
			Class.forName(aDriverName);
		} catch (Throwable cnf) {
			throw new SQLException("Impossible de charger le driver '" + aDriverName + "'", cnf);
		}
	}

	/**
	 * Méthode de connexion de la base. <br/>
	 *
	 * C'est dans cette methode que l'on recupere un objet Connection
	 *
	 * @param aUrl
	 *            un url
	 * @param aLogin
	 *            un login
	 * @param aPassword
	 *            un mot de passe
	 */
	public void seConnecter(String aUrl, String aLogin, String aPassword) throws SQLException {
		this.cxt = DriverManager.getConnection(aUrl, aLogin, aPassword);
	}

	/**
	 * Méthode de déconnexion de la base. <br/>
	 *
	 * C'est dans cette methode que l'on ferme l'objet Connection
	 */
	public void seDeconnecter() {
		try {
			if (this.cxt != null) {
				this.cxt.close();
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	/**
	 * Methode qui verifie que le login et le password vont bien ensemble. <br/>
	 *
	 * @param unLogin
	 *            un login
	 * @param unMdp
	 *            un mot de passe
	 * @return
	 * 		<ul>
	 *         <li>-1: si un probleme provient du login</li>
	 *         <li>-2: si un probleme provient du mot de passe</li>
	 *         <li>l'id du client si tout se passe bien</li>
	 *         </ul>
	 * @throws SQLException
	 *             si une erreur survient
	 */
	public int authentifier(String unLogin, String unMdp) throws SQLException {
		if ((this.cxt != null) && !this.cxt.isClosed()) {
			PreparedStatement request = null;
			ResultSet resultat = null;
			try {
				// Creation de l'objet de requete
				request = this.cxt.prepareStatement("select id, password from utilisateur where login=?");
				request.setString(1, unLogin);

				// Envoie de la requete et recuperation du resultat
				resultat = request.executeQuery();

				// Parcours du resultat, toujours commencer par un .next
				while (resultat.next()) {
					Integer id = resultat.getInt("id");
					boolean noid = resultat.wasNull();
					String password = resultat.getString("password");
					if (noid) {
						return -1;
					}
					return (password == unMdp) || password.equals(unMdp) ? id : -2;
				}
				return -1;
			} catch (SQLException sql) {
				// On la relance
				throw sql;
			} finally {
				// Tres IMPORTANT, on ferme tout
				if (resultat != null) {
					try {
						resultat.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (request != null) {
					try {
						request.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			throw new SQLException("Connexion invalide!");
		}
	}

	/**
	 * Methode qui affiche le nom et le prenom de tous les utilisateurs.
	 *
	 * @throws SQLException
	 *             si une erreur survient
	 */
	public void afficherNomPrenomUtilisateur() throws SQLException {
		if ((this.cxt != null) && !this.cxt.isClosed()) {
			Statement request = null;
			ResultSet resultat = null;
			try {
				// Creation de l'objet de requete
				request = this.cxt.createStatement();

				// Envoie de la requete et recuperation du resultat
				resultat = request.executeQuery("select nom, prenom from utilisateur");

				// Parcours du resultat, toujours commencer par un .next
				while (resultat.next()) {
					String nom = resultat.getString("nom");
					String prenom = resultat.getString("prenom");
					// ou, en utilisant les id de colonnes [! Commence a 1 !}
					// String nom = resultat.getString(1);
					// String prenom = resultat.getString(2);
					System.out.println(nom + " " + prenom);
				}
			} catch (SQLException sql) {
				// On la relance
				throw sql;
			} finally {
				// Tres IMPORTANT, on ferme tout
				if (resultat != null) {
					try {
						resultat.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (request != null) {
					try {
						request.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			throw new SQLException("Connexion invalide!");
		}
	}

	/**
	 * Methode qui affiche l'id de tous les comptes d'un utilisateur
	 *
	 * @param unUtilisateurId
	 *            l'id d'un utilisateur
	 * @throws SQLException
	 *             si une erreur survient
	 */
	public void afficherIdComptesUtilisateur(int unUtilisateurId) throws SQLException {
		if ((this.cxt != null) && !this.cxt.isClosed()) {
			PreparedStatement request = null;
			ResultSet resultat = null;
			try {
				// Creation de l'objet de requete
				request = this.cxt.prepareStatement(
						"select cp.id from compte as cp, utilisateur as client where client.id=? and cp.utilisateurId=client.id");
				// On pousse les parametres dans la requete
				request.setInt(1, unUtilisateurId);

				// Envoie de la requete et recuperation du resultat
				resultat = request.executeQuery();

				// Parcours du resultat, toujours commencer par un .next
				while (resultat.next()) {
					Integer idCp = resultat.getInt("id");
					System.out.println(idCp);
				}
			} catch (SQLException sql) {
				// On la relance
				throw sql;
			} finally {
				// Tres IMPORTANT, on ferme tout
				if (resultat != null) {
					try {
						resultat.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (request != null) {
					try {
						request.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			throw new SQLException("Connexion invalide!");
		}
	}

	/**
	 * Methode qui affiche l'id de toutes les operations d'un compte
	 *
	 * @param unCompteId
	 *            l'id d'un compte
	 * @throws SQLException
	 *             si une erreur survient
	 */
	public void afficherIdOperationComptes(int unCompteId) throws SQLException {
		if ((this.cxt != null) && !this.cxt.isClosed()) {
			PreparedStatement request = null;
			ResultSet resultat = null;
			try {
				// Creation de l'objet de requete
				request = this.cxt.prepareStatement(
						"select op.id from operation as op, compte as cp where cp.id=? and op.compteId=cp.id");
				// On pousse les parametres dans la requete
				request.setInt(1, unCompteId);

				// Envoie de la requete et recuperation du resultat
				resultat = request.executeQuery();

				// Parcours du resultat, toujours commencer par un .next
				while (resultat.next()) {
					Integer idOp = resultat.getInt("id");
					System.out.println(idOp);
				}
			} catch (SQLException sql) {
				// On la relance
				throw sql;
			} finally {
				// Tres IMPORTANT, on ferme tout
				if (resultat != null) {
					try {
						resultat.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (request != null) {
					try {
						request.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			throw new SQLException("Connexion invalide!");
		}
	}

	/**
	 * Methode qui recupere toutes les operations d'un compte.
	 *
	 * @param unCompteId
	 *            un id de compte
	 * @return la liste de ses operations, une liste vide si aucune
	 * @throws SQLException
	 *             si une erreur survient
	 */
	public List<Operation> selectOperation(int unCompteId) throws SQLException {
		return this.selectOperation(unCompteId, null, null, null);
	}

	/**
	 * Methode qui recupere les operations d'un compte en fonction des critères
	 * indiques.
	 *
	 * @param unCompteId
	 *            un id de compte
	 * @param dateDeb
	 *            date debut
	 * @param dateFin
	 *            date fin
	 * @param creditDebit
	 *            TRUE = credit, FALSE = debit, null = les deux
	 * @return la liste de ses operations, une liste vide si aucune
	 * @throws SQLException
	 *             si une erreur survient
	 */
	public List<Operation> selectOperation(int unCompteId, Date dateDeb, Date dateFin, Boolean creditDebit)
			throws SQLException {
		List<Operation> listeOperation = new ArrayList<Operation>();
		if ((this.cxt != null) && !this.cxt.isClosed()) {
			PreparedStatement request = null;
			ResultSet resultat = null;
			try {
				StringBuilder requete = new StringBuilder();
				requete.append("select * from operation where compteId=?");
				if (dateDeb != null) {
					requete.append(" and date >= ?");
				}
				if (dateFin != null) {
					requete.append(" and date <= ?");
				}
				if (creditDebit != null) {
					if (creditDebit.booleanValue()) {
						requete.append(" and montant >= 0");
					} else {
						requete.append(" and montant <= 0");
					}
				}
				requete.append(" order by date desc");
				// Recuperation de tous les clients
				request = this.cxt.prepareStatement(requete.toString());
				int idParam = 1;
				request.setInt(idParam, unCompteId);
				idParam++;
				if (dateDeb != null) {
					request.setDate(idParam, dateDeb);
					idParam++;
				}
				if (dateFin != null) {
					request.setDate(idParam, dateFin);
					idParam++;
				}
				resultat = request.executeQuery();
				while (resultat.next()) {
					Integer id = resultat.getInt("id");
					String libelle = resultat.getString("libelle");
					double montant = resultat.getDouble("montant");
					Timestamp date = resultat.getTimestamp("date");
					Integer compteId = resultat.getInt("compteId");
					listeOperation.add(new Operation(id, libelle, montant, date, compteId));
				}

			} catch (SQLException sql) {
				// On la relance
				throw sql;
			} finally {
				// Tres IMPORTANT, on ferme tout
				if (resultat != null) {
					try {
						resultat.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (request != null) {
					try {
						request.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			return listeOperation;
		} else {
			throw new SQLException("Connexion invalide!");
		}
	}

	/**
	 * Methode qui recupere tous les comptes d'un utilisateur.
	 *
	 * @param unUtilisateurId
	 *            un id d'utilisateur
	 * @return la liste de ses comptes, une liste vide si aucun
	 * @throws SQLException
	 *             si une erreur survient
	 */
	public List<Compte> selectCompte(int unUtilisateurId) throws SQLException {
		List<Compte> listeCompte = new ArrayList<Compte>();
		if ((this.cxt != null) && !this.cxt.isClosed()) {
			Statement request = null;
			ResultSet resultat = null;
			try {
				// Recuperation de tous les clients
				request = this.cxt.createStatement();
				resultat = request.executeQuery("select * from compte where utilisateurId=" + unUtilisateurId);
				while (resultat.next()) {
					Integer id = resultat.getInt("id");
					// String libelle = resultat.getString("libelle");
					double solde = resultat.getDouble("solde");
					double decouvert = resultat.getDouble("decouvert");
					// Par defaut, le getDouble renvoie 0 si la valeur est null
					// Si on veut tester le null il faut faire
					boolean pasDeDecouver = resultat.wasNull();
					double taux = resultat.getDouble("taux");
					boolean pasDeTaux = resultat.wasNull();
					Compte cpt = null;
					if (pasDeDecouver && pasDeTaux) {
						cpt = new Compte(id, solde);
					} else if (pasDeDecouver && !pasDeTaux) {
						cpt = new CompteRemunere(id, solde, taux);
					} else if (!pasDeDecouver && pasDeTaux) {
						cpt = new CompteASeuil(id, solde, decouvert);
					} else {
						cpt = new CompteASeuilRemunere(id, solde, taux, decouvert);
					}
					cpt.setLibelle(resultat.getString("libelle"));
					listeCompte.add(cpt);
				}

			} catch (SQLException sql) {
				// On la relance
				throw sql;
			} finally {
				// Tres IMPORTANT, on ferme tout
				if (resultat != null) {
					try {
						resultat.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (request != null) {
					try {
						request.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			return listeCompte;
		} else {
			throw new SQLException("Connexion invalide!");
		}
	}

	/**
	 * Methode qui recupere tous les utilisateurs avec leurs comptes.
	 *
	 * @return la liste des utilisateurs avec leurs comptes
	 * @throws SQLException
	 *             si une erreur survient
	 */
	public List<Client> selectUtilisateur() throws SQLException {
		List<Client> listeClient = new ArrayList<Client>();
		if ((this.cxt != null) && !this.cxt.isClosed()) {
			Statement request = null;
			ResultSet resultat = null;
			try {
				// Recuperation de tous les clients
				request = this.cxt.createStatement();
				resultat = request.executeQuery("select * from utilisateur");
				while (resultat.next()) {
					Integer id = resultat.getInt("id");
					String nom = resultat.getString("nom");
					String prenom = resultat.getString("prenom");
					Date dateNaissance = resultat.getDate("dateDeNaissance");
					int age = -1;
					if (dateNaissance != null) {
						// On calcul l'age
						Calendar c = Calendar.getInstance();
						c.setTimeInMillis(System.currentTimeMillis());
						int anneeMaintenant = c.get(Calendar.YEAR);
						c.setTime(dateNaissance);
						int anneeNaissance = c.get(Calendar.YEAR);
						age = anneeMaintenant - anneeNaissance;
					}
					listeClient.add(new Client(id, nom, prenom, age));
				}

				// Recuperation de tous les comptes pour chaque client
				Iterator<Client> iterClient = listeClient.iterator();
				while (iterClient.hasNext()) {
					Client client = iterClient.next();
					List<Compte> listeCpt = this.selectCompte(client.getNumero());
					Iterator<Compte> iterCompte = listeCpt.iterator();
					while (iterCompte.hasNext()) {
						client.ajouterCompte(iterCompte.next());
					}
				}

			} catch (SQLException sql) {
				// On la relance
				throw sql;
			} finally {
				// Tres IMPORTANT, on ferme tout
				if (resultat != null) {
					try {
						resultat.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (request != null) {
					try {
						request.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			return listeClient;
		} else {
			throw new SQLException("Connexion invalide!");
		}
	}

	/**
	 * Methode qui recupere un utilisateur avec ses comptes.
	 *
	 * @param unId
	 *            un id d'utilisateur
	 * @return l'utilisateur ayant l'id donne
	 * @throws SQLException
	 *             si une erreur survient
	 */
	public Client selectUtilisateur(int unId) throws SQLException {
		Client client = null;
		if ((this.cxt != null) && !this.cxt.isClosed()) {
			Statement request = null;
			ResultSet resultat = null;
			try {
				// Recuperation de tous les clients
				request = this.cxt.createStatement();
				resultat = request.executeQuery("select * from utilisateur where id=" + unId);
				while (resultat.next()) {
					Integer id = resultat.getInt("id");
					String nom = resultat.getString("nom");
					String prenom = resultat.getString("prenom");
					Date dateNaissance = resultat.getDate("dateDeNaissance");
					int age = -1;
					if (dateNaissance != null) {
						// On calcul l'age
						Calendar c = Calendar.getInstance();
						c.setTimeInMillis(System.currentTimeMillis());
						int anneeMaintenant = c.get(Calendar.YEAR);
						c.setTime(dateNaissance);
						int anneeNaissance = c.get(Calendar.YEAR);
						age = anneeMaintenant - anneeNaissance;
					}
					client = new Client(id, nom, prenom, age);
				}

				List<Compte> listeCpt = this.selectCompte(unId);
				Iterator<Compte> iterCompte = listeCpt.iterator();
				while (iterCompte.hasNext()) {
					client.ajouterCompte(iterCompte.next());
				}
			} catch (SQLException sql) {
				throw sql;
			} finally {
				// Tres IMPORTANT, on ferme tout
				if (resultat != null) {
					try {
						resultat.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (request != null) {
					try {
						request.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			return client;
		} else {
			throw new SQLException("Connexion invalide!");
		}
	}

	/**
	 * Effectue un virement entre deux comptes.
	 *
	 * @param cptSrc
	 *            le compte source
	 * @param cptDest
	 *            le compte destination
	 * @param unMontant
	 *            le montant qui sera retire du compte source et ajoute au
	 *            compte destination
	 * @throws SQLException
	 *             si une erreur survient
	 */
	public void faireVirement(Integer cptSrc, Integer cptDest, Double unMontant) throws SQLException {
		if ((this.cxt != null) && !this.cxt.isClosed()) {
			this.cxt.setAutoCommit(false);
			Statement request = null;
			try {
				// Recuperation de tous les clients
				request = this.cxt.createStatement();
				request.executeUpdate("update compte set solde=(solde-" + unMontant + ") where id=" + cptSrc);
				request.close();
				request = this.cxt.createStatement();
				request.executeUpdate("update compte set solde=(solde+" + unMontant + ") where id=" + cptDest);
				request.close();
				request = this.cxt.createStatement();
				request.executeUpdate("insert into operation (libelle, montant, date, compteId) values ('Virement',"
						+ (-unMontant) + ",NOW()," + cptSrc + ")");
				request.close();
				request = this.cxt.createStatement();
				request.executeUpdate("insert into operation (libelle, montant, date, compteId) values ('Virement',"
						+ unMontant + ",NOW()," + cptDest + ")");
				this.cxt.commit();
			} catch (SQLException sql) {
				this.cxt.rollback();
				// On la relance
				throw sql;
			} finally {
				this.cxt.setAutoCommit(true);
				if (request != null) {
					try {
						request.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}

		} else {
			throw new SQLException("Connexion invalide!");
		}
	}

	/**
	 * Exemple d'utilisation.
	 *
	 * @param args
	 *            ne sert pas ici
	 */
	public static void main(String[] args) {
		// Nom du driver pour acceder a la base de donnees.
		// Lire la documentation associee a sa base de donnees pour le connaitre
		final String dbDriver = "com.mysql.jdbc.Driver";
		// URL d'access a la base de donnees.
		final String dbUrl = "jdbc:mysql://localhost/banque";
		// Login d'access a la base de donnees.
		final String dbLogin = "root";
		// Mot de passe d'access a la base de donnees.
		final String dbPwd = "";
		AccesBD bd = null;
		try {
			bd = new AccesBD(dbDriver);
			bd.seConnecter(dbUrl, dbLogin, dbPwd);
			System.out.println("Tous les noms,prenoms utilisateurs");
			bd.afficherNomPrenomUtilisateur();
			System.out.println("\nComptes du client 1");
			bd.afficherIdComptesUtilisateur(1);
			System.out.println("\nOperations du comptes 12");
			bd.afficherIdOperationComptes(12);

			// En Mapping maintenant
			System.out.println("\nMapping Objet");
			List<Client> lClient = bd.selectUtilisateur();
			Iterator<Client> iterClient = lClient.iterator();
			while (iterClient.hasNext()) {
				Client client = iterClient.next();
				System.out.println(client);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (bd != null) {
				bd.seDeconnecter();
			}
		}
	}

}
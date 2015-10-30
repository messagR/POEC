/**
 * test
 */
package com.exo.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author PC
 *
 */
public class RunKeep {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 1- chargement du driver en memoire
		// --- trouver le nom du driver
		// --- on cherche le nom d'une classe
		String nomDuDriver = "com.mysql.jdbc.Driver"; // different pour chaques
		// jdbc (connecteur jdbc
		// mysql sous google)
		try {
			Class.forName(nomDuDriver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		// 2- recuperer une connexion a la base
		// --- j'ai besoin du login/password et de l'url pour acceder a la base
		String login = "root";
		String password = "root";
		String url = "jdbc:mysql://localhost:3306/world"; // different pour
		// chaques jdbc (url
		// jdbc mysql sous
		// google)

		Connection connexion = null;
		Statement ste = null;
		ResultSet resultat = null;
		try {
			connexion = DriverManager.getConnection(url, login, password);

			// 3- faire une requete
			String requete = "select * from city";
			ste = connexion.createStatement();
			// 4- recuperer le resultat
			resultat = ste.executeQuery(requete);
			while (resultat.next()) {
				int id = resultat.getInt("ID");
				String nom = resultat.getString("Name");
				String cp = resultat.getString("CountryCode");
				String ville = resultat.getString("District");
				int population = resultat.getInt("Population");
				StringBuffer sb = new StringBuffer();
				sb.append("id : " + id);
				sb.append(" nom : " + nom);
				sb.append(" cp : " + cp);
				sb.append(" ville : " + ville);
				sb.append(" population : " + population);

				System.out.println(sb);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (resultat != null) {
				try {
					resultat.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (ste != null) {
				try {
					ste.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (connexion != null) {
				try {
					connexion.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}

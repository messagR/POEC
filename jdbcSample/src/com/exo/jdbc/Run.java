/**
 * test
 */
package com.exo.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author PC
 *
 */
public class Run {

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
		PreparedStatement ste = null;
		ResultSet resultat = null;
		try {
			connexion = DriverManager.getConnection(url, login, password);
			connexion.setAutoCommit(false); // par defaut a true et si false
											// c'est a nous de gerer les commit
											// et rollback
			connexion.setReadOnly(true); // par defaut false et si true on ne
											// peut pas faire d'insert, update
											// ou delete

			/*
			 *
			 * ATTENTION EN SQL TOUT COMMENCE A 1 !!!
			 *
			 */

			// 3- faire une requete
			// String requete = "select * from city where Name=?";
			String requete = "select * from city where Name like ? and ID > ?";
			ste = connexion.prepareStatement(requete);
			// ste.setString(1, "Breda");
			ste.setString(1, "B%");
			ste.setInt(2, 4000);
			// 4- recuperer le resultat
			resultat = ste.executeQuery();
			ResultSetMetaData mdata = resultat.getMetaData();
			System.out.println(mdata.getColumnType(1) + "=" + Types.INTEGER);
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

				connexion.commit();
				resultat.close();
				ste.close();
				connexion.rollback();

				requete = "select * from city where Name like ? and ID > ?";
				ste = connexion.prepareStatement(requete);
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

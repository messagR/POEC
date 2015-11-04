package fr.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.banque.entity.IClient;
import fr.banque.entity.ICompte;
import fr.banque.entity.ICompteASeuil;
import fr.banque.entity.ICompteRemunere;
import projetBd.AccesDB;

/**
 * Servlet implementation class ServletClient
 */
@WebServlet("/ProjetServlet/ServletClient")
public class ServletClient2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletClient2() {
		super();
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
	}

	/**
	 * @see Servlet#destroy()
	 */
	@Override
	public void destroy() {
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Properties mesProperties = new Properties();
		// chemin a partir du src
		try (InputStream is = ServletClient2.class.getClassLoader().getResourceAsStream("mesPreferences.properties")) {
			mesProperties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Nom du driver pour acceder a la base de donnee.
		// Lire la documentation associee a sa base de donnees pour le connaitre
		final String utilDbDriver = mesProperties.getProperty("utilDb.driver");
		// url d'acces a la base de donnees
		final String utilDbUrl = mesProperties.getProperty("utilDb.url");
		// login d'acces a la base de donnees
		final String utilDbLogin = mesProperties.getProperty("utilDb.login");
		// mot de passe d'acces a la base de donnees
		final String utilDbPassword = mesProperties.getProperty("utilDb.password");

		AccesDB utilDb = null;
		PrintWriter out = response.getWriter();
		try {
			utilDb = new AccesDB(utilDbDriver);
			utilDb.seConnecter(utilDbLogin, utilDbPassword, utilDbUrl);
			StringBuffer sb = new StringBuffer();
			sb.append("<html>\n");
			sb.append("	<head>\n");
			sb.append("		<title>Tous les clients</title>\n");
			sb.append("		<link rel='stylesheet' href='css/styles.css' >\n");
			sb.append("	</head>\n");
			sb.append("	<body>\n");
			sb.append("		<script type='text/javascript' src='JS/jquery.min.js'></script>\n");
			List<IClient> listeClient = utilDb.listeClient();
			if (!listeClient.isEmpty()) {
				sb.append("		<h1>Les clients</h1>\n");
				sb.append("		<table border=1>\n");
				sb.append("			<tr style='background-color:#999;color:#000;'>\n");
				sb.append("				<td>Nom</td>\n");
				sb.append("				<td>Prenom</td>\n");
				sb.append("				<td>Age</td>\n");
				sb.append("				<td>Comptes</td>\n");
				sb.append("			</tr>\n");
				ICompte[] listeCompte = null;
				for (IClient client : listeClient) {
					sb.append("			<tr class='client'>\n");
					sb.append("				<td>").append(client.getNom()).append("</td>\n");
					sb.append("				<td>").append(client.getPrenom()).append("</td>\n");
					sb.append("				<td>").append(client.getAge()).append("</td>\n");
					sb.append("				<td>\n");
					sb.append("					<form action='./ServletCompte2' method='post'>\n");
					sb.append("				  		<input type='hidden' name='id' value='" + client.getNumero()
					+ "'>\n");
					sb.append("				  		<input type='submit' value='Voir ses comptes'>\n");
					sb.append("				 	</form>\n");
					sb.append("				</td>\n");
					sb.append("			</tr>\n");
					sb.append("			<tr class='comptes' style='background-color:#ddd;'><td colspan=4>\n");
					listeCompte = client.getComptes();
					if (listeCompte != null) {
						sb.append("		<h1>Ses comptes</h1>\n");
						sb.append("		<table border=1>\n");
						sb.append("			<tr style='background-color:#999;color:#000;'>\n");
						sb.append("				<td>Solde</td>\n");
						sb.append("				<td>Taux</td>\n");
						sb.append("				<td>Seuil</td>\n");
						sb.append("				<td>Comptes</td>\n");
						sb.append("			</tr>\n");
						for (ICompte compte : listeCompte) {
							if (compte != null) {
								sb.append("			<tr style='background-color:#fff;'>\n");
								sb.append("				<td>").append(compte.getSolde()).append("</td>\n");
								if (compte instanceof ICompteRemunere) {
									ICompteRemunere compteRem = (ICompteRemunere) compte;
									sb.append("				<td>").append(compteRem.getTaux()).append("</td>\n");
								} else {
									sb.append("				<td>Pas de Taux</td>\n");
								}
								if (compte instanceof ICompteASeuil) {
									ICompteASeuil compteASeuil = (ICompteASeuil) compte;
									sb.append("				<td>").append(compteASeuil.getSeuil()).append("</td>\n");
								} else {
									sb.append("				<td>Pas de Seuil</td>\n");
								}
								sb.append("				<td>\n");
								sb.append(
										"					<form action='./ServletOperation2' method='post' name='formulaire'>\n");
								sb.append("				  		<input type='hidden' name='id' value='"
										+ compte.getNumero() + "'>\n");
								sb.append(
										"				  		<div class='operation' style='cursor:pointer;'>Voir ses operations</div>\n");
								sb.append("				 	</form>\n");
								sb.append("				</td>\n");
								sb.append("			</tr>\n");
							}
						}
						sb.append("		</table>\n");
					} else {
						sb.append("		<h1>Pas de compte</h1>\n");
					}
					sb.append("			</td></tr>\n");
				}
				sb.append("		</table>\n");
			} else {
				sb.append("		<h1>Aucun client</h1>\n");
			}
			sb.append("		<script>\n");
			sb.append("			$(document).ready(function(){\n");
			sb.append("				$('.comptes').css('display','none');\n");
			sb.append("				$('.client').css('cursor','pointer');\n");
			sb.append("			});\n");
			sb.append("			$('.client').click(function(){\n");
			sb.append("				$(this).next('.comptes').fadeToggle();\n");
			sb.append("			});\n");
			sb.append("			$('.operation').click(function(){\n");
			sb.append("				$(this).parent().submit();\n");
			sb.append("			});\n");
			sb.append("		</script>\n");
			sb.append("	</body>\n");
			sb.append("</html>");
			out.print(sb.toString());
		} catch (SQLException e1) {
			out.print(e1);
		} finally {
			if (utilDb != null) {
				utilDb.seDeconnecter();
			}
		}
	}

}

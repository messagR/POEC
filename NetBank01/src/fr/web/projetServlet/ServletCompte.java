package fr.web.projetServlet;

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

import fr.banque.entity.ICompte;
import fr.banque.entity.ICompteASeuil;
import fr.banque.entity.ICompteRemunere;
import fr.banque.exception.ClientIntrouvableException;
import projetBd.AccesDB;

/**
 * Servlet implementation class ServletCompte
 */
@WebServlet("/projetServlet/ServletCompte")
public class ServletCompte extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletCompte() {
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
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idClient = Integer.parseInt(request.getParameter("id"));
		Properties mesProperties = new Properties();
		// chemin a partir du src
		try (InputStream is = ServletCompte.class.getClassLoader().getResourceAsStream("mesPreferences.properties")) {
			mesProperties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Nom du driver pour acceder a la base de donnee.
		// Lire la documentation associee a sa base de donnees pour le connaitre
		String utilDbDriver = mesProperties.getProperty("utilDb.driver");
		// url d'acces a la base de donnees
		String utilDbUrl = mesProperties.getProperty("utilDb.url");
		// login d'acces a la base de donnees
		String utilDbLogin = mesProperties.getProperty("utilDb.login");
		// mot de passe d'acces a la base de donnees
		String utilDbPassword = mesProperties.getProperty("utilDb.password");

		AccesDB utilDb = null;
		PrintWriter out = response.getWriter();
		try {
			utilDb = new AccesDB(utilDbDriver);
			utilDb.seConnecter(utilDbLogin, utilDbPassword, utilDbUrl);
			StringBuffer sb = new StringBuffer();
			sb.append(
					"<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n");
			sb.append("<html>\n");
			sb.append("	<head>\n");
			sb.append("		<title>Compte du client n°" + idClient + " en Servlet</title>\n");
			sb.append("		<link rel='stylesheet' href='../css/styles.css' >\n");
			sb.append("	</head>\n");
			sb.append("	<body>\n");
			List<ICompte> listeCompte = null;
			try {
				listeCompte = utilDb.listeCompte(idClient);
				if (!listeCompte.isEmpty()) {
					sb.append("		<h1>Les comptes du client n°" + idClient + " en Servlet</h1>\n");
					sb.append("		<table border=1>\n");
					sb.append("			<tr style='background-color:#999;color:#000;'>\n");
					sb.append("				<td>Solde</td>\n");
					sb.append("				<td>Taux</td>\n");
					sb.append("				<td>Seuil</td>\n");
					sb.append("				<td>Comptes</td>\n");
					sb.append("			</tr>\n");
					for (ICompte compte : listeCompte) {
						sb.append("			<tr>\n");
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
						sb.append("					<form action='./ServletOperation' method='post'>\n");
						sb.append("				  		<input type='hidden' name='id' value='" + compte.getNumero()
						+ "'>\n");
						sb.append(
								"				  		<input type='submit' value='Voir ses operations'>\n");
						sb.append("				 	</form>\n");
						sb.append("				</td>\n");
						sb.append("			</tr>\n");
					}
					sb.append("		</table>\n");
				} else {
					sb.append("		<h1>Le client n°" + idClient + " n'a pas de compte</h1>\n");
				}
			} catch (ClientIntrouvableException e) {
				sb.append("		<h1>Client n°" + idClient + " introuvable</h1>\n");
			}
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

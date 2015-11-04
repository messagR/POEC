package fr.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.banque.entity.IOperation;
import fr.banque.exception.CompteIntrouvableException;
import projetBd.AccesDB;

/**
 * Servlet implementation class ServletCompte
 */
@WebServlet("/ServletOperation2")
public class ServletOperation2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletOperation2() {
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
		Integer idCompte = new Integer(request.getParameter("id"));
		Properties mesProperties = new Properties();
		// chemin a partir du src
		try (InputStream is = ServletOperation2.class.getClassLoader()
				.getResourceAsStream("mesPreferences.properties")) {
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
			sb.append("		<title>Operation du compte n°" + idCompte + "</title>\n");
			sb.append("		<link rel='stylesheet' href='css/styles.css' >\n");
			sb.append("	</head>\n");
			sb.append("	<body>\n");
			List<IOperation> listeOperation = null;
			try {
				listeOperation = utilDb.rechercherOp(idCompte);
				if (!listeOperation.isEmpty()) {
					sb.append("		<h1>Les operations du compte n°" + idCompte + "</h1>\n");
					sb.append("		<table border=1>\n");
					sb.append("			<tr style='background-color:#999;color:#000;'>\n");
					sb.append("				<td>Date</td>\n");
					sb.append("				<td>Libelle</td>\n");
					sb.append("				<td>Montant</td>\n");
					sb.append("			</tr>\n");
					for (IOperation operation : listeOperation) {
						sb.append("			<tr>\n");
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
						sb.append("				<td>").append(sdf.format(operation.getDate())).append("</td>\n");
						sb.append("				<td>").append(operation.getLibelle()).append("</td>\n");
						sb.append("				<td>").append(operation.getMontant()).append(" &euro;</td>\n");
						sb.append("			</tr>\n");
					}
					sb.append("		</table>\n");
				} else {
					sb.append("		<h1>Le compte n°" + idCompte + " n'a pas d'operation</h1>\n");
				}
			} catch (CompteIntrouvableException e) {
				sb.append("		<h1>Compte n°" + idCompte + " introuvable</h1>\n");
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

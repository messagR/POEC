package fr.web.projetServletJSP;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.banque.entity.ICompte;
import fr.banque.exception.ClientIntrouvableException;
import projetBd.AccesDB;

/**
 * Servlet implementation class ServletCompte
 */
@WebServlet("/projetServletJSP/ServletCompte")
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
		Integer idClient = new Integer(request.getParameter("id"));
		List<ICompte> listeCompte = null;
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
		try {
			utilDb = new AccesDB(utilDbDriver);
			utilDb.seConnecter(utilDbLogin, utilDbPassword, utilDbUrl);
			try {
				listeCompte = utilDb.listeCompte(idClient);
			} catch (ClientIntrouvableException e) {
				idClient = null;
			}
		} catch (SQLException e1) {
			request.setAttribute("erreur", "Erreur dans la servlet (" + e1.getMessage() + ")");
		} finally {
			if (utilDb != null) {
				utilDb.seDeconnecter();
			}
		}
		request.setAttribute("ListeCompte", listeCompte);
		request.setAttribute("IdClient", idClient);
		RequestDispatcher dispatcher = request.getRequestDispatcher("Compte.jsp");
		dispatcher.forward(request, response);
		return;
	}

}

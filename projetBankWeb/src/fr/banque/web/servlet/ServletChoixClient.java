package fr.banque.web.servlet;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.banque.entity.IClient;
import projetBd.AccesDB;

/**
 * Servlet implementation class ServletClient
 */
@WebServlet("/ServletChoixClient")
public class ServletChoixClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOG = LogManager.getLogger();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletChoixClient() {
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
		RequestDispatcher dispatcher = request.getRequestDispatcher("clients/liste.jsp");

		Boolean banquier = (Boolean) request.getSession(true).getAttribute("banquier");
		if (!banquier) {
			request.setAttribute("erreur", "Vous avez ete deconnecte");
			ServletChoixClient.LOG.error("Utilisateur deconnecte");
			dispatcher = request.getRequestDispatcher("/login.jsp");
			dispatcher.forward(request, response);
			return;
		}

		FichierProp properties = new FichierProp();

		AccesDB utilDb = null;
		try {
			utilDb = new AccesDB(properties.getUtilDbDriver());
			utilDb.seConnecter(properties.getUtilDbLogin(), properties.getUtilDbPassword(),
					properties.getUtilDbUrl());
			List<IClient> listClient = utilDb.listeClient();
			ServletChoixClient.LOG.info("----->Liste client recupere : {}", listClient);
			request.setAttribute("listClient", listClient);
			request.getSession(true).setAttribute("banquier", true);
		} catch (SQLException e) {
			request.setAttribute("erreur", "Erreur SQL : " + e.getMessage());
			ServletChoixClient.LOG.error("Erreur SQL : " + e.getMessage());
		} finally {
			if (utilDb != null) {
				utilDb.seDeconnecter();
			}
			dispatcher.forward(request, response);
		}
		return;
	}
}

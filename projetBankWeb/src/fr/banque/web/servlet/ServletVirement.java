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

import fr.banque.entity.ICompte;
import fr.banque.exception.ClientIntrouvableException;
import projetBd.AccesDB;

/**
 * Servlet implementation class ServletClient
 */
@WebServlet("/ServletVirement")
public class ServletVirement extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOG = LogManager.getLogger();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletVirement() {
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
		RequestDispatcher dispatcher = request.getRequestDispatcher("/comptes/virement.jsp");

		Integer idClient = (Integer) request.getSession(true).getAttribute("idClient");
		if (idClient == null) {
			request.setAttribute("erreur", "Utilisateur deconnecte");
			ServletVirement.LOG.error("Vous avez ete deconnecte");
			dispatcher = request.getRequestDispatcher("/login.jsp");
			dispatcher.forward(request, response);
			return;
		}

		FichierProp properties = new FichierProp();

		AccesDB utilDb = null;
		List<ICompte> listCompte = null;
		try {
			utilDb = new AccesDB(properties.getUtilDbDriver());
			utilDb.seConnecter(properties.getUtilDbLogin(), properties.getUtilDbPassword(), properties.getUtilDbUrl());
			ServletVirement.LOG.info("----->Recherche les comptes du client n°{}", idClient);
			listCompte = utilDb.listeCompte(idClient);
			ServletVirement.LOG.info("----->Comptes du client n°{} - {}", idClient, listCompte);
		} catch (SQLException e) {
			request.setAttribute("erreur", "Erreur SQL : " + e.getMessage());
			ServletVirement.LOG.error("Erreur SQL : " + e.getMessage());
		} catch (ClientIntrouvableException e) {
			request.setAttribute("erreur", e.getMessage() + " -> Deconnexion");
			ServletVirement.LOG.error(e.getMessage());
			ServletVirement.LOG.trace("TENTATIVE DE FRAUDE : {}", e.getMessage());
			dispatcher = request.getRequestDispatcher("/ServletDeconnexion");
			dispatcher.forward(request, response);
			return;
		} finally {
			if (utilDb != null) {
				utilDb.seDeconnecter();
			}
			request.setAttribute("listCompte", listCompte);
			dispatcher.forward(request, response);
		}
		return;
	}
}

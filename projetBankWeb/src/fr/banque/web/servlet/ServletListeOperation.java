package fr.banque.web.servlet;


import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

import fr.banque.entity.IOperation;
import fr.banque.exception.CompteIntrouvableException;
import projetBd.AccesDB;

/**
 * Servlet implementation class ServletClient
 */
@WebServlet("/ServletListeOperation")
public class ServletListeOperation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOG = LogManager.getLogger();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletListeOperation() {
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
		RequestDispatcher dispatcher = request.getRequestDispatcher("comptes/historique.jsp");

		Integer idClient = (Integer) request.getSession(true).getAttribute("idClient");
		if (idClient == null) {
			request.setAttribute("erreur", "Vous avez ete deconnecte");
			ServletListeOperation.LOG.error("Utilisateur deconnecte");
			dispatcher = request.getRequestDispatcher("/login.jsp");
			dispatcher.forward(request, response);
			return;
		}
		Integer idCompte;
		if (request.getParameter("inCompte") != null) {
			idCompte = new Integer(request.getParameter("inCompte"));
			request.getSession(true).setAttribute("idCompte", idCompte);
		} else {
			idCompte = (Integer) request.getSession(true).getAttribute("idCompte");
		}
		ServletListeOperation.LOG.info("----->Liste des operations du client n°{} pour le compte n°{}", idClient,
				idCompte);
		if (idCompte == null) {
			request.setAttribute("erreur", "Vous n'avez pas de numero de compte");
			ServletListeOperation.LOG.error("Pas de numero de compte");
			dispatcher = request.getRequestDispatcher("ServletListeCompte");
			dispatcher.forward(request, response);
			return;

		}

		FichierProp properties = new FichierProp();

		AccesDB utilDb = null;
		List<IOperation> listOperation = null;
		try {
			utilDb = new AccesDB(properties.getUtilDbDriver());
			utilDb.seConnecter(properties.getUtilDbLogin(), properties.getUtilDbPassword(), properties.getUtilDbUrl());
			ServletListeOperation.LOG.debug("Verifie que le compte n°{} appartient bien au client n°{}", idCompte,
					idClient);
			if (utilDb.verifieCompteUtilisateur(idClient, idCompte)) {
				listOperation = utilDb.rechercherOp(idCompte);
				ServletListeOperation.LOG.info("----->Operations pour le compte n°{} du client n°{} - {}", idCompte,
						idClient, listOperation);
			} else {
				request.setAttribute("erreur", "Vous n'avez pas acces au compte n°" + idCompte + " -> Deconnexion");
				ServletListeOperation.LOG.error("Le client n°{} n'a pas acces au compte n°{}", idClient, idCompte);
				ServletListeOperation.LOG.trace("TENTATIVE DE FRAUDE : Le client n°{} n'a pas acces au compte n°{}",
						idClient, idCompte);
				dispatcher = request.getRequestDispatcher("ServletDeconnexion");
				dispatcher.forward(request, response);
				return;
			}
		} catch (SQLException e) {
			request.setAttribute("erreur", "Erreur SQL : " + e.getMessage());
			ServletListeOperation.LOG.error("Erreur SQL : " + e.getMessage());
		} catch (CompteIntrouvableException e) {
			request.setAttribute("erreur", e.getMessage() + " -> Deconnexion");
			ServletListeOperation.LOG.error(e.getMessage());
			ServletListeOperation.LOG.trace("TENTATIVE DE FRAUDE : {}", e.getMessage());
			dispatcher = request.getRequestDispatcher("ServletDeconnexion");
			dispatcher.forward(request, response);
			return;
		} finally {
			if (utilDb != null) {
				utilDb.seDeconnecter();
			}
			Calendar cal = Calendar.getInstance();
			cal.set(1900, 0, 1, 0, 0, 0);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String dateDebut = sdf.format(new Date(cal.getTimeInMillis()));
			String dateFin = sdf.format(new Date(Calendar.getInstance().getTimeInMillis()));
			request.setAttribute("dateDebut", dateDebut);
			request.setAttribute("dateFin", dateFin);
			request.setAttribute("debit", "checked");
			request.setAttribute("credit", "checked");
			request.setAttribute("listOperation", listOperation);
			dispatcher.forward(request, response);
		}
		return;
	}
}

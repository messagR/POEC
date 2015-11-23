package fr.banque.web.servlet;


import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;
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
@WebServlet("/ServletRechercherOperation")
public class ServletRechercherOperation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOG = LogManager.getLogger();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletRechercherOperation() {
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
		RequestDispatcher dispatcher = request.getRequestDispatcher("/comptes/historique.jsp");

		Integer idClient = (Integer) request.getSession(true).getAttribute("idClient");
		if (idClient == null) {
			request.setAttribute("erreur", "Vous avez ete deconnecte");
			ServletRechercherOperation.LOG.error("Utilisateur deconnecte");
			dispatcher = request.getRequestDispatcher("/login.jsp");
			dispatcher.forward(request, response);
			return;
		}
		Integer idCompte = (Integer) request.getSession(true).getAttribute("idCompte");
		if (idCompte == null) {
			request.setAttribute("erreur", "Vous n'avez pas de numero de compte");
			ServletRechercherOperation.LOG.error("Pas de numero de compte");
			dispatcher = request.getRequestDispatcher("/ServletListeCompte");
			dispatcher.forward(request, response);
			return;

		}

		Calendar cal = Calendar.getInstance();
		String dateD = request.getParameter("inDateDebut");
		cal.set(1900, 0, 1, 0, 0, 0);
		Date dateDebut = new Date(cal.getTimeInMillis());
		if (!("").equals(dateD) && (dateD != null)) {
			cal.set(Integer.valueOf(dateD.split("/")[2]), Integer.valueOf(dateD.split("/")[1]) - 1,
					Integer.valueOf(dateD.split("/")[0]), 0, 0, 0);
			dateDebut = new Date(cal.getTimeInMillis());
		}
		String dateF = request.getParameter("inDateFin");
		Date dateFin = new Date();
		if (!("").equals(dateF) && (dateF != null)) {
			cal.set(Integer.valueOf(dateF.split("/")[2]), Integer.valueOf(dateF.split("/")[1]) - 1,
					Integer.valueOf(dateF.split("/")[0]), 23, 59, 59);
			dateFin = new Date(cal.getTimeInMillis());
		}
		String debit = request.getParameter("inDebit");
		String credit = request.getParameter("inCredit");
		String debitCredit = "en debit et credit";
		if ((debit == null) || (credit == null)) {
			if (debit != null) {
				debitCredit = "en debit";
			} else if (credit != null) {
				debitCredit = "en credit";
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		ServletRechercherOperation.LOG.info(
				"----->Recherche d'operations pour le compte n°{} du client n°{} du {} au {} {}", idCompte, idClient,
				sdf.format(dateDebut), sdf.format(dateFin), debitCredit);

		List<IOperation> listOperation = null;
		// FichierProp properties = new FichierProp();
		AccesDB utilDb = null;
		try {
			// utilDb = new AccesDB(properties.getUtilDbDriver());
			// utilDb.seConnecter(properties.getUtilDbLogin(),
			// properties.getUtilDbPassword(),
			// properties.getUtilDbUrl());
			utilDb = new AccesDB("jdbc/dataSourceProjetBankWeb");
			utilDb.seConnecter();
			if (dateDebut.after(dateFin)) {
				request.setAttribute("erreur", "La date de debut ne doit pas etre superieure a la date de fin");
				ServletRechercherOperation.LOG.error("La date de debut est superieure a la date de fin");
			} else {
				ServletRechercherOperation.LOG.debug("Verifie que le compte n°{} appartient bien au client n°{}",
						idCompte, idClient);
				if (utilDb.verifieCompteUtilisateur(idClient, idCompte)) {
					if ((debit != null) && (credit != null)) {
						listOperation = utilDb.rechercherOp(idCompte, dateDebut, dateFin);
					} else if (debit != null) {
						listOperation = utilDb.rechercherOp(idCompte, dateDebut, dateFin, true);
					} else if (credit != null) {
						listOperation = utilDb.rechercherOp(idCompte, dateDebut, dateFin, false);
					} else {
						listOperation = utilDb.rechercherOp(idCompte);
						request.setAttribute("erreur", "Vous devez faire au moins un choix pour le debit/credit");
						ServletRechercherOperation.LOG.error("Pas de choix en debit/credit");
					}
				} else {
					request.setAttribute("erreur", "Vous n'avez pas acces au compte n°" + idCompte + " -> Deconnexion");
					ServletRechercherOperation.LOG.error("Le client n°{} n'a pas acces au compte n°{}", idClient,
							idCompte);
					ServletRechercherOperation.LOG.trace(
							"TENTATIVE DE FRAUDE : Le client n°{} n'a pas acces au compte n°{}", idClient, idCompte);
					dispatcher = request.getRequestDispatcher("/ServletDeconnexion");
					dispatcher.forward(request, response);
					return;
				}
			}
		} catch (SQLException e) {
			request.setAttribute("erreur", "Erreur SQL : " + e.getMessage());
			ServletRechercherOperation.LOG.error("Erreur SQL : " + e.getMessage());
		} catch (CompteIntrouvableException e) {
			request.setAttribute("erreur", e.getMessage() + " -> Deconnexion");
			ServletRechercherOperation.LOG.error(e.getMessage());
			ServletRechercherOperation.LOG.trace("TENTATIVE DE FRAUDE : {}", e.getMessage());
			dispatcher = request.getRequestDispatcher("/ServletDeconnexion");
			dispatcher.forward(request, response);
			return;
		} catch (NamingException e) {
			request.setAttribute("erreur", e.getMessage());
			ServletRechercherOperation.LOG.error("Erreur : " + e.getMessage());
		} finally {
			if (utilDb != null) {
				utilDb.seDeconnecter();
			}
			request.setAttribute("dateDebut", dateD);
			request.setAttribute("dateFin", dateF);
			if (debit != null) {
				request.setAttribute("debit", "checked");
			} else {
				request.setAttribute("debit", "");
			}
			if (credit != null) {
				request.setAttribute("credit", "checked");
			} else {
				request.setAttribute("credit", "");
			}
			request.setAttribute("listOperation", listOperation);
		}
		dispatcher.forward(request, response);
		return;
	}
}

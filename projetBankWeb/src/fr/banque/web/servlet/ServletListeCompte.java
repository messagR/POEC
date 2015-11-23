package fr.banque.web.servlet;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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

import fr.banque.entity.BeanCompte;
import fr.banque.entity.ICompte;
import fr.banque.entity.ICompteASeuil;
import fr.banque.entity.ICompteRemunere;
import fr.banque.exception.ClientIntrouvableException;
import projetBd.AccesDB;

/**
 * Servlet implementation class ServletClient
 */
@WebServlet("/ServletListeCompte")
public class ServletListeCompte extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOG = LogManager.getLogger();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletListeCompte() {
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
		RequestDispatcher dispatcher = request.getRequestDispatcher("/comptes/liste.jsp");

		Integer idClient = (Integer) request.getSession(true).getAttribute("idClient");
		if (idClient == null) {
			request.setAttribute("erreur", "Vous avez ete deconnecte");
			ServletListeCompte.LOG.error("Utilisateur deconnecte");
			dispatcher = request.getRequestDispatcher("/login.jsp");
			dispatcher.forward(request, response);
			return;
		}

		List<BeanCompte> listCompte = new ArrayList<BeanCompte>();
		// FichierProp properties = new FichierProp();
		AccesDB utilDb = null;
		try {
			// utilDb = new AccesDB(properties.getUtilDbDriver());
			// utilDb.seConnecter(properties.getUtilDbLogin(),
			// properties.getUtilDbPassword(),
			// properties.getUtilDbUrl());
			utilDb = new AccesDB("jdbc/dataSourceProjetBankWeb");
			utilDb.seConnecter();
			ServletListeCompte.LOG.info("----->Recherche les comptes du client n°{}", idClient);
			List<ICompte> listICompte = utilDb.listeCompte(idClient);
			if (!listICompte.isEmpty()) {
				for (ICompte compte : listICompte) {
					BeanCompte beanCompte = new BeanCompte(compte.getNumero(), compte.getLibelle(),
							compte.getSolde());
					if (compte instanceof ICompteRemunere) {
						ICompteRemunere compteRem = (ICompteRemunere) compte;
						beanCompte.setTaux(compteRem.getTaux());
					}
					if (compte instanceof ICompteASeuil) {
						ICompteASeuil compteASeuil = (ICompteASeuil) compte;
						beanCompte.setSeuil(compteASeuil.getSeuil());
					}
					listCompte.add(beanCompte);
				}
			}
		} catch (SQLException e) {
			request.setAttribute("erreur", "Erreur SQL : " + e.getMessage());
			ServletListeCompte.LOG.error("Erreur SQL : " + e.getMessage());
		} catch (ClientIntrouvableException e) {
			request.setAttribute("erreur", e.getMessage() + " -> Deconnexion");
			ServletListeCompte.LOG.error(e.getMessage());
			ServletListeCompte.LOG.trace("TENTATIVE DE FRAUDE : {}", e.getMessage());
			dispatcher = request.getRequestDispatcher("/ServletDeconnexion");
			dispatcher.forward(request, response);
			return;
		} catch (NamingException e) {
			request.setAttribute("erreur", e.getMessage());
			ServletListeCompte.LOG.error("Erreur : " + e.getMessage());
		} finally {
			if (utilDb != null) {
				utilDb.seDeconnecter();
			}
			request.setAttribute("listCompte", listCompte);
		}
		dispatcher.forward(request, response);
		return;
	}
}

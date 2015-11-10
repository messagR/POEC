package fr.banque.web.servlet;


import java.io.IOException;
import java.sql.SQLException;
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

import fr.banque.entity.ICompte;
import fr.banque.exception.ClientIntrouvableException;
import fr.banque.exception.CompteIntrouvableException;
import projetBd.AccesDB;

/**
 * Servlet implementation class ServletClient
 */
@WebServlet("/ServletEffectuerVirement")
public class ServletEffectuerVirement extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOG = LogManager.getLogger();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletEffectuerVirement() {
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
			request.setAttribute("erreur", "Vous avez ete deconnecte");
			ServletEffectuerVirement.LOG.error("Utilisateur deconnecte");
			dispatcher = request.getRequestDispatcher("/login.jsp");
			dispatcher.forward(request, response);
			return;
		}
		Integer cptEmetteur = null;
		if ((request.getParameter("inCmptEme") != null) && !request.getParameter("inCmptEme").equals("")) {
			cptEmetteur = new Integer(request.getParameter("inCmptEme"));
		}
		Integer cptDestinataire = null;
		if ((request.getParameter("inCmptDes") != null) && !request.getParameter("inCmptDes").equals("")) {
			cptDestinataire = new Integer(request.getParameter("inCmptDes"));
		}
		Double montant = 0d;
		if (request.getParameter("inMontant") != null) {
			montant = new Double(request.getParameter("inMontant").replace(",", "."));
		}
		ServletEffectuerVirement.LOG.info(
				"----->Demande de virement du client n°{}, du compte n°{} vers le compte n°{} pour un montant de {} euro",
				idClient, cptEmetteur,
				cptDestinataire, montant);

		List<ICompte> listCompte = null;
		// FichierProp properties = new FichierProp();
		AccesDB utilDb = null;
		try {
			// utilDb = new AccesDB(properties.getUtilDbDriver());
			// utilDb.seConnecter(properties.getUtilDbLogin(),
			// properties.getUtilDbPassword(),
			// properties.getUtilDbUrl());
			utilDb = new AccesDB("jdbc/dataSourceProjetBankWeb");
			utilDb.seConnecter();
			if ((cptEmetteur != null) && (cptDestinataire != null)) {
				if (!cptEmetteur.equals(cptDestinataire)) {
					ServletEffectuerVirement.LOG.info(
							"----->Verification que les comptes n°{} et n°{} appartiennent bien au client n°{}",
							cptEmetteur, cptDestinataire, idClient);
					if (utilDb.verifieCompteUtilisateur(idClient, cptEmetteur)) {
						if (utilDb.verifieCompteUtilisateur(idClient, cptDestinataire)) {
							try {
								utilDb.faireVirement(cptEmetteur, cptDestinataire, montant);
								request.setAttribute("succes", "Le virement a bien ete effectue");
								ServletEffectuerVirement.LOG.info(
										"----->Le virement du client n°{}, du comtpe n°{} vers le comte n°{} pour un montant de {} euro a bien ete effectue",
										idClient, cptEmetteur, cptDestinataire, montant);
							} catch (Exception e) {
								request.setAttribute("erreur",
										"Le virement n'a pas pu etre effectue : " + e.getMessage());
								ServletEffectuerVirement.LOG.info(
										"----->Le virement du client n°{}, du compte n°{} vers le compte n°{} pour un montant de {} euro n'a pas pu etre effectue : {}",
										idClient, cptEmetteur, cptDestinataire, montant, e.getMessage());
							}
						} else {
							request.setAttribute("erreur",
									"Vous n'avez pas acces au compte n°" + cptDestinataire + " -> Deconnexion");
							ServletEffectuerVirement.LOG.error("Le client n°{} n'a pas acces au compte n°{}", idClient,
									cptDestinataire);
							ServletEffectuerVirement.LOG.trace(
									"TENTATIVE DE FRAUDE : Le client n°{} n'a pas acces au compte n°{}", idClient,
									cptDestinataire);
							dispatcher = request.getRequestDispatcher("/ServletDeconnexion");
							dispatcher.forward(request, response);
							return;
						}
					} else {
						request.setAttribute("erreur",
								"Vous n'avez pas acces au compte n°" + cptEmetteur + " -> Deconnexion");
						ServletEffectuerVirement.LOG.error("Le client n°{} n'a pas acces au compte n°{}", idClient,
								cptEmetteur);
						ServletEffectuerVirement.LOG.trace(
								"TENTATIVE DE FRAUDE : Le client n°{} n'a pas acces au compte n°{}", idClient,
								cptEmetteur);
						dispatcher = request.getRequestDispatcher("/ServletDeconnexion");
						dispatcher.forward(request, response);
						return;
					}
				} else {
					request.setAttribute("erreur", "Le compte de destination est le même que le compte emeteur");
					ServletEffectuerVirement.LOG.error("Le compte de destination est le même que le compte emeteur");
				}
			} else {
				request.setAttribute("erreur", "Vous devez choisir les comptes emeteur et destinataire");
				ServletEffectuerVirement.LOG.error("Pas de comptes emeteur et/ou destinataire");
			}
			ServletEffectuerVirement.LOG.info("----->Recherche les comptes du client n°{}", idClient);
			listCompte = utilDb.listeCompte(idClient);
			ServletEffectuerVirement.LOG.info("----->Comptes du client n°{} - {}", idClient, listCompte);
		} catch (SQLException e) {
			request.setAttribute("erreur", "Erreur SQL : " + e.getMessage());
			ServletEffectuerVirement.LOG.error("Erreur SQL : " + e.getMessage());
		} catch (CompteIntrouvableException | IllegalArgumentException
				| ClientIntrouvableException e) {
			request.setAttribute("erreur", e.getMessage() + " -> Deconnexion");
			ServletEffectuerVirement.LOG.error(e.getMessage());
			ServletEffectuerVirement.LOG.trace("TENTATIVE DE FRAUDE : {}", e.getMessage());
			dispatcher = request.getRequestDispatcher("/ServletDeconnexion");
			dispatcher.forward(request, response);
			return;
		} catch (NamingException e) {
			request.setAttribute("erreur", e.getMessage());
			ServletEffectuerVirement.LOG.error("Erreur : " + e.getMessage());
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

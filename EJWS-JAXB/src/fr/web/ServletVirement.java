/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package fr.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.banque.Compte;
import fr.bd.AccesBD;

/**
 * Servlet qui va afficher les historiques d'operation d'un client. <br/>
 */
@WebServlet(urlPatterns = { "/ServletVirement" })
public class ServletVirement extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur.
	 */
	public ServletVirement() {
		super();
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AccesBD bd = null;
		try {
			// Recuperation des clients
			bd = new AccesBD(IServlet.DB_DRIVER);
			bd.seConnecter(IServlet.DB_URL, IServlet.DB_LOGIN, IServlet.DB_PWD);

			// On recupere l'id qui est dans la session
			Integer idUtilisateur = (Integer) request.getSession(true).getAttribute("idUtilisateur");

			if (idUtilisateur == null) {
				// Si il n'est pas la = on n'est pas passe par authentification
				// Part en erreur sur la page login
				request.setAttribute("erreur", "Merci de vous authentifier");
				// On passe la main
				request.getRequestDispatcher("login.jsp").forward(request, response);
				// On fait un return pour etre certain que rien d'autre ne doit
				// arriver
				return;
			} else {
				// Sinon, on cherche les parametres
				String inCmptEme = request.getParameter("inCmptEme");
				String inCmptDes = request.getParameter("inCmptDes");
				String inMontant = request.getParameter("inMontant");
				Integer cmptEme = null;
				try {
					cmptEme = Integer.parseInt(inCmptEme);
				} catch (Exception e1) {
					// On ne fait rien
				}

				Integer cmptDes = null;
				try {
					cmptDes = Integer.parseInt(inCmptDes);
				} catch (Exception e1) {
					// On ne fait rien
				}

				Double montant = null;
				try {
					montant = Double.parseDouble(inMontant);
				} catch (Exception e1) {
					// On ne fait rien
				}

				List<Compte> listeCpt = bd.selectCompte(idUtilisateur);
				// On les places dans la request
				request.setAttribute("listeCpt", listeCpt);
				// On replace l'id et les parametres afin de pouvoir les
				// reafficher
				request.setAttribute("pCmptEme", cmptEme);
				request.setAttribute("pCmptDes", cmptDes);
				request.setAttribute("pMontant", inMontant);

				if ((montant != null) && (montant.doubleValue() <= 0)) {
					request.setAttribute("erreur", "Votre montant doit etre strictement positif.");
					request.getRequestDispatcher("comptes/virement.jsp").forward(request, response);
					return;
				}
				if ((cmptEme != null) && (cmptDes != null) && (montant != null)) {
					bd.faireVirement(cmptEme, cmptDes, montant);
					request.setAttribute("message", "Votre virement s'est bien passé.");
					request.getRequestDispatcher("menu.jsp").forward(request, response);
					// On fait un return pour etre certain que rien d'autre ne
					// doit
					// arriver
					return;
				} else {
					request.getRequestDispatcher("comptes/virement.jsp").forward(request, response);
					return;
				}
			}

		} catch (SQLException e) {
			// Part en erreur la page login
			request.setAttribute("erreur", "Probleme lors du virement (" + e.getMessage() + ")");
			// On passe la main
			request.getRequestDispatcher("comptes/virement.jsp").forward(request, response);
			// On fait un return pour etre certain que rien d'autre ne doit
			// arriver
			return;
		} finally {
			if (bd != null) {
				bd.seDeconnecter();
			}
		}

	}
}

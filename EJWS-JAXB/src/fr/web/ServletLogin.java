/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package fr.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.bd.AccesBD;

/**
 * Servlet qui va gerer le login du client. <br/>
 */
@WebServlet(urlPatterns = { "/ServletLogin" })
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur.
	 */
	public ServletLogin() {
		super();
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AccesBD bd = null;
		try {
			bd = new AccesBD(IServlet.DB_DRIVER);
			bd.seConnecter(IServlet.DB_URL, IServlet.DB_LOGIN, IServlet.DB_PWD);

			// Recupere les parametres qui viennent de la JSP
			String login = request.getParameter("inLogin");
			String pwd = request.getParameter("inPass");
			if ((login == null) || (pwd == null) || login.trim().isEmpty() || pwd.trim().isEmpty()) {
				// Part en erreur sur la page login
				request.setAttribute("erreur", "Vous devez indiquer un login et mot de passe non vide");
				// On passe la main
				request.getRequestDispatcher("login.jsp").forward(request, response);
				// On fait un return pour etre certain que rien d'autre ne doit
				// arriver
				return;
			} else {
				// On verifie que le tout est ok
				int userId = bd.authentifier(login, pwd);
				if (userId > 0) {
					// On place l'utilisateur dans la session avec tous ses
					// comptes
					request.getSession(true).setAttribute("idUtilisateur", Integer.valueOf(userId));
					// On va vers la JSP menu
					request.getRequestDispatcher("menu.jsp").forward(request, response);
					return;
				} else {
					// Part en erreur sur la page login
					request.setAttribute("erreur", "Votre login/pwd n'est pas valide");
					// On passe la main
					request.getRequestDispatcher("login.jsp").forward(request, response);
					// On fait un return pour etre certain que rien d'autre ne
					// doit arriver
					return;

				}
			}

		} catch (SQLException e) {
			// Part en erreur sur la page login
			request.setAttribute("erreur", "Probleme lors de l'authentification (" + e.getMessage() + ")");
			// On passe la main
			request.getRequestDispatcher("login.jsp").forward(request, response);
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

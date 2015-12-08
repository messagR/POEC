/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package fr.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet qui va afficher le menu. <br/>
 */
@WebServlet(urlPatterns = { "/ServletMenu" })
public class ServletMenu extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur.
	 */
	public ServletMenu() {
		super();
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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
		}
		// Sinon, tout va bien, on affiche la JSP menu
		request.getRequestDispatcher("menu.jsp").forward(request, response);
		// On fait un return pour etre certain que rien d'autre ne doit
		// arriver
		return;

	}
}

package fr.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MaPremiereServlet loadOnStartup force l'init au
 * demarrage du serveur _ avec un ordre_
 */
public class MaSecondeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MaSecondeServlet() {
		super();
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Je fais qq chose de complique
		int a = 50 + 12;

		// Je place l'information a dispositin de la JSP (dans la servlet)
		request.setAttribute("maClef", Integer.valueOf(a));

		// scope session (dans une session pour une personne)
		HttpSession session = request.getSession();
		session.setAttribute("uneClef", Integer.valueOf(a));

		// scope application (tant que l'appli tourne pour tout le monde)
		ServletContext sc = request.getServletContext();
		sc.setAttribute("encoreUneClef", Integer.valueOf(a));

		// on perd les attributs :
		// response.sendRedirect("MaJSP01.jsp");

		// Passer la main
		RequestDispatcher dispatcher = request.getRequestDispatcher("MaJSP01.jsp");
		dispatcher.forward(request, response);
		return;
	}
}

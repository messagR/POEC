package fr.banque.web.servlet;


import java.io.IOException;

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

/**
 * Servlet implementation class ServletClient
 */
@WebServlet("/ServletListeClient")
public class ServletListeClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOG = LogManager.getLogger();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletListeClient() {
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
		RequestDispatcher dispatcher = request.getRequestDispatcher("/menu.jsp");

		Boolean banquier = (Boolean) request.getSession(true).getAttribute("banquier");
		if (!banquier) {
			request.setAttribute("erreur", "Vous avez ete deconnecte");
			ServletListeClient.LOG.error("Utilisateur deconnecte");
			dispatcher = request.getRequestDispatcher("/login.jsp");
			dispatcher.forward(request, response);
			return;
		}

		Integer idClient;
		if (request.getParameter("inClient") != null) {
			idClient = new Integer(request.getParameter("inClient"));
			request.getSession(true).setAttribute("idClient", idClient);
		} else {
			idClient = (Integer) request.getSession(true).getAttribute("idClient");
		}
		ServletListeClient.LOG.info("----->Recuperation du client n°{}", idClient);

		dispatcher.forward(request, response);
		return;
	}
}

package fr.banque.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import fr.banque.entity.IClient;
import projetBd.AccesDB;

/**
 * Servlet implementation class WsClients
 */
@WebServlet("/WsClients")
public class WsClients extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WsClients() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// RequestDispatcher dispatcher =
		// request.getRequestDispatcher("clients/liste.jsp");

		// Boolean banquier = (Boolean)
		// request.getSession(true).getAttribute("banquier");
		// if ((banquier == null) || !banquier) {
		// request.setAttribute("erreur", "Vous avez ete deconnecte");
		// dispatcher = request.getRequestDispatcher("/login.jsp");
		// dispatcher.forward(request, response);
		// return;
		// }

		AccesDB utilDb = null;
		try {
			utilDb = new AccesDB("jdbc/dataSourceProjetBankWeb");
			utilDb.seConnecter();
			List<IClient> listIClient = utilDb.listeClient();
			Gson gson = new Gson();
			String flux = gson.toJson(listIClient);
			response.setContentType("application/json");
			PrintWriter pw = response.getWriter();
			pw.write(flux);
			request.setAttribute("listClient", listIClient);
			request.getSession(true).setAttribute("banquier", true);
		} catch (SQLException | NamingException e) {
			request.setAttribute("erreur", "Erreur SQL : " + e.getMessage());
		} finally {
			if (utilDb != null) {
				utilDb.seDeconnecter();
			}
		}
		// dispatcher.forward(request, response);
		return;
	}

}

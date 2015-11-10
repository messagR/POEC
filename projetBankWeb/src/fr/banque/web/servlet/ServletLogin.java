package fr.banque.web.servlet;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

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

import fr.banque.entity.Factory;
import fr.banque.entity.IClient;
import fr.banque.entity.IUtilisateur;
import fr.banque.exception.ChampsVidesException;
import fr.banque.exception.ClientIntrouvableException;
import projetBd.AccesDB;

/**
 * Servlet implementation class ServletClient
 */
@WebServlet("/ServletLogin")
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOG = LogManager.getLogger();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletLogin() {
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
		// pour vérifier les champs text : ' or " = '
		// ou : " or ' = "
		String login = request.getParameter("inLogin");
		String password = request.getParameter("inPass");
		ServletLogin.LOG.info("----->Tentative de connexion d'un utilisateur avec login = {} et mot de passe = {}",
				login,
				password);

		if ((login != null) && (password != null)) {
			if (login.equals("banque") && password.equals("banquier")) {
				ServletLogin.LOG.info("----->Banquier connecte");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/clients/liste.jsp");

				// FichierProp properties = new FichierProp();
				AccesDB utilDb = null;
				try {
					// utilDb = new AccesDB(properties.getUtilDbDriver());
					// utilDb.seConnecter(properties.getUtilDbLogin(),
					// properties.getUtilDbPassword(),
					// properties.getUtilDbUrl());
					utilDb = new AccesDB("jdbc/dataSourceProjetBankWeb");
					utilDb.seConnecter();
					List<IClient> listClient = utilDb.listeClient();
					ServletLogin.LOG.info("----->Liste client recupere : {}", listClient);
					request.setAttribute("listClient", listClient);
					request.getSession(true).setAttribute("banquier", true);
					Factory f = Factory.getInstance();
					IUtilisateur util = f.creerUtilisateur(-1, "ZUNINO", "Angelique", login, password, new Date());
				} catch (SQLException e) {
					request.setAttribute("erreur", "Erreur SQL : " + e.getMessage());
					ServletLogin.LOG.error("Erreur SQL : " + e.getMessage());
					dispatcher = request.getRequestDispatcher("/login.jsp");
				} catch (NamingException e) {
					request.setAttribute("erreur", e.getMessage());
					ServletLogin.LOG.error("Erreur : " + e.getMessage());
					dispatcher = request.getRequestDispatcher("/login.jsp");
				} finally {
					if (utilDb != null) {
						utilDb.seDeconnecter();
					}
					dispatcher.forward(request, response);
				}

			}else{
				RequestDispatcher dispatcher = request.getRequestDispatcher("/menu.jsp");

				// FichierProp properties = new FichierProp();
				AccesDB utilDb = null;
				try {
					// utilDb = new AccesDB(properties.getUtilDbDriver());
					// utilDb.seConnecter(properties.getUtilDbLogin(),
					// properties.getUtilDbPassword(),
					// properties.getUtilDbUrl());
					utilDb = new AccesDB("jdbc/dataSourceProjetBankWeb");
					utilDb.seConnecter();
					IUtilisateur util = utilDb.authentifier(login, password);
					ServletLogin.LOG.info("----->Utilisateur n°{} - {} {} connecte", util.getNumero(), util.getPrenom(),
							util.getNom());
					request.getSession(true).setAttribute("idClient", util.getNumero());
					request.getSession(true).setAttribute("banquier", false);
					Map<String, IUtilisateur> listeConnectes = (Map<String, IUtilisateur>) request.getServletContext()
							.getAttribute("listeConnectes");
					if (listeConnectes == null) {
						listeConnectes = new Hashtable<String, IUtilisateur>();
					}
					listeConnectes.put(request.getSession(true).getId(), util);
					request.getServletContext().setAttribute("listeConnectes", listeConnectes);
				} catch (SQLException e) {
					request.setAttribute("erreur", "Erreur SQL : " + e.getMessage());
					ServletLogin.LOG.error("Erreur SQL : " + e.getMessage());
					dispatcher = request.getRequestDispatcher("/login.jsp");
				} catch (ClientIntrouvableException | ChampsVidesException e) {
					request.setAttribute("erreur", e.getMessage());
					ServletLogin.LOG.error(e.getMessage());
					dispatcher = request.getRequestDispatcher("/login.jsp");
				} catch (NamingException e) {
					request.setAttribute("erreur", e.getMessage());
					ServletLogin.LOG.error("Erreur : " + e.getMessage());
					dispatcher = request.getRequestDispatcher("/login.jsp");
				} finally {
					if (utilDb != null) {
						utilDb.seDeconnecter();
					}
					dispatcher.forward(request, response);
				}
			}
		} else {
			request.setAttribute("erreur", "Vous avez ete deconnecte");
			ServletLogin.LOG.error("Utilisateur deconnecte");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
			dispatcher.forward(request, response);
		}
		return;
	}
}

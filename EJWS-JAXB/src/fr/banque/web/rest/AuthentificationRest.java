/**
 * test
 */
package fr.banque.web.rest;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.banque.Client;
import fr.banque.ListeClient;
import fr.bd.AccesBD;
import fr.web.IServlet;
import fr.xml.ConteneurCompte;

/**
 * @author PC
 *
 */
@Path("/authentification")
public class AuthentificationRest {
	private final static Logger LOG = LogManager.getLogger();

	public AuthentificationRest() {
		// TODO Auto-generated constructor stub
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response authentifierPut(@QueryParam("login") String login, @QueryParam("password") String pwd) {

		Client client;

		if ((login != null) && (pwd != null)) {
			AccesBD bd = null;
			try {
				bd = new AccesBD(IServlet.DB_DRIVER);
				bd.seConnecter(IServlet.DB_URL, IServlet.DB_LOGIN, IServlet.DB_PWD);
				int idClient = bd.authentifier(login, pwd);
				if (idClient > 0) {
					client = bd.selectUtilisateur(idClient);
				} else {
					return Response.status(Status.BAD_REQUEST).entity(null).build();
				}
			} catch (SQLException e) {
				return Response.status(Status.BAD_REQUEST).entity(null).build();
			} finally {
				if (bd != null) {
					bd.seDeconnecter();
				}
			}

		} else {
			return Response.status(Status.BAD_REQUEST).entity(null).build();
		}

		return Response.status(Status.OK).entity(client).build();
	}

	public Client authentifierGet(@PathParam("login") String login, @PathParam("password") String pwd) {

		Client client;

		if ((login != null) && (pwd != null)) {
			AccesBD bd = null;
			try {
				bd = new AccesBD(IServlet.DB_DRIVER);
				bd.seConnecter(IServlet.DB_URL, IServlet.DB_LOGIN, IServlet.DB_PWD);
				int idClient = bd.authentifier(login, pwd);
				if (idClient > 0) {
					client = bd.selectUtilisateur(idClient);
				} else {
					return null;
				}
			} catch (SQLException e) {
				return null;
			} finally {
				if (bd != null) {
					bd.seDeconnecter();
				}
			}

		} else {
			return null;
		}

		return client;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Client authentifier(@QueryParam("login") String login, @QueryParam("password") String pwd,
			@Context HttpServletRequest request)
					throws ServletException, IOException {

		Client client;

		if ((login != null) && (pwd != null)) {
			AccesBD bd = null;
			try {
				bd = new AccesBD(IServlet.DB_DRIVER);
				bd.seConnecter(IServlet.DB_URL, IServlet.DB_LOGIN, IServlet.DB_PWD);
				int idClient = bd.authentifier(login, pwd);
				if (idClient > 0) {
					client = bd.selectUtilisateur(idClient);
				} else {
					return null;
				}
			} catch (SQLException e) {
				AuthentificationRest.LOG.error(e.getMessage());
				return null;
			} finally {
				if (bd != null) {
					bd.seDeconnecter();
				}
			}

		} else {
			return null;
		}

		return client;
	}

	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public List<Client> authentifierPost(@FormParam("login") String login, @FormParam("password") String pwd) {

		List<Client> listeClient = new ArrayList<Client>();

		if ((login != null) && (pwd != null)) {
			AccesBD bd = null;
			try {
				bd = new AccesBD(IServlet.DB_DRIVER);
				bd.seConnecter(IServlet.DB_URL, IServlet.DB_LOGIN, IServlet.DB_PWD);
				int idClient = bd.authentifier(login, pwd);
				if (idClient > 0) {
					listeClient.add(bd.selectUtilisateur(idClient));
				} else {
					return null;
				}
			} catch (SQLException e) {
				return null;
			} finally {
				if (bd != null) {
					bd.seDeconnecter();
				}
			}

		} else {
			return null;
		}

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ListeClient.class, ConteneurCompte.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			ListeClient liste = (ListeClient) jaxbUnmarshaller.unmarshal(new File("c:/fichierClient.xml"));
			for (Client client : liste.getListeClient()) {
				listeClient.add(client);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listeClient;
	}

}

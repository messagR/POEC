/**
 * test
 */
package fr.banque.web.rest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.banque.Client;
import fr.banque.Compte;
import fr.bd.AccesBD;
import fr.web.IServlet;
import net.sf.json.JSONObject;

/**
 * @author PC
 *
 */
@Path("/authentification")
public class AuthentificationRest {
	private final static Logger LOG = LogManager.getLogger();

	/**
	 *
	 */
	public AuthentificationRest() {
		// TODO Auto-generated constructor stub
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response authentifierPut(@QueryParam("login") String login, @QueryParam("password") String pwd) {
		JSONObject obj = new JSONObject();

		if ((login != null) && (pwd != null)) {
			AccesBD bd = null;
			try {
				bd = new AccesBD(IServlet.DB_DRIVER);
				bd.seConnecter(IServlet.DB_URL, IServlet.DB_LOGIN, IServlet.DB_PWD);
				int idClient = bd.authentifier(login, pwd);
				if (idClient > 0) {
					Client client = bd.selectUtilisateur(idClient);
					obj.put("numeroClient", client.getNumero());
					obj.put("nom", client.getNom());
					obj.put("prenom", client.getPrenom());
					obj.put("age", client.getAge());
					Compte[] comptes = client.getComptes();
					List<JSONObject> listCompte = new ArrayList<JSONObject>();
					for (Compte compte : comptes) {
						JSONObject comptesJSON = new JSONObject();
						comptesJSON.put("numeroCompte", compte.getNumero());
						comptesJSON.put("libelle", compte.getLibelle());
						comptesJSON.put("solde", compte.getSolde());
						listCompte.add(comptesJSON);
					}
					obj.put("comptes", listCompte);
				} else {
					obj.put("erreur", "pas un client");
					return Response.status(Status.BAD_REQUEST).entity(obj.toString()).build();
				}
			} catch (SQLException e) {
				obj.put("erreur", e.getMessage());
				return Response.status(Status.BAD_REQUEST).entity(obj.toString()).build();
			} finally {
				if (bd != null) {
					bd.seDeconnecter();
				}
			}

		} else {
			obj.put("erreur", "pas de login et/ou mot de passe");
			return Response.status(Status.BAD_REQUEST).entity(obj.toString()).build();
		}

		return Response.status(Status.OK).entity(obj.toString()).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String authentifierPost(@FormParam("login") String login, @FormParam("password") String pwd) {
		JSONObject obj = new JSONObject();

		if ((login != null) && (pwd != null)) {
			AccesBD bd = null;
			try {
				bd = new AccesBD(IServlet.DB_DRIVER);
				bd.seConnecter(IServlet.DB_URL, IServlet.DB_LOGIN, IServlet.DB_PWD);
				int idClient = bd.authentifier(login, pwd);
				if (idClient > 0) {
					Client client = bd.selectUtilisateur(idClient);
					obj.put("numeroClient", client.getNumero());
					obj.put("nom", client.getNom());
					obj.put("prenom", client.getPrenom());
					obj.put("age", client.getAge());
					Compte[] comptes = client.getComptes();
					List<JSONObject> listCompte = new ArrayList<JSONObject>();
					for (Compte compte : comptes) {
						JSONObject comptesJSON = new JSONObject();
						comptesJSON.put("numeroCompte", compte.getNumero());
						comptesJSON.put("libelle", compte.getLibelle());
						comptesJSON.put("solde", compte.getSolde());
						listCompte.add(comptesJSON);
					}
					obj.put("comptes", listCompte);
				} else {
					obj.put("erreur", "pas un client");
					return obj.toString();
				}
			} catch (SQLException e) {
				obj.put("erreur", e.getMessage());
				return obj.toString();
			} finally {
				if (bd != null) {
					bd.seDeconnecter();
				}
			}

		} else {
			obj.put("erreur", "pas de login et/ou mot de passe");
			return obj.toString();
		}

		return obj.toString();
	}

	@GET
	@Path("{login}/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public String authentifierGet(@PathParam("login") String login, @PathParam("password") String pwd) {
		JSONObject obj = new JSONObject();

		if ((login != null) && (pwd != null)) {
			AccesBD bd = null;
			try {
				bd = new AccesBD(IServlet.DB_DRIVER);
				bd.seConnecter(IServlet.DB_URL, IServlet.DB_LOGIN, IServlet.DB_PWD);
				int idClient = bd.authentifier(login, pwd);
				if (idClient > 0) {
					Client client = bd.selectUtilisateur(idClient);
					obj.put("numeroClient", client.getNumero());
					obj.put("nom", client.getNom());
					obj.put("prenom", client.getPrenom());
					obj.put("age", client.getAge());
					Compte[] comptes = client.getComptes();
					List<JSONObject> listCompte = new ArrayList<JSONObject>();
					for (Compte compte : comptes) {
						JSONObject comptesJSON = new JSONObject();
						comptesJSON.put("numeroCompte", compte.getNumero());
						comptesJSON.put("libelle", compte.getLibelle());
						comptesJSON.put("solde", compte.getSolde());
						listCompte.add(comptesJSON);
					}
					obj.put("comptes", listCompte);
				} else {
					obj.put("erreur", "pas un client");
					return obj.toString();
				}
			} catch (SQLException e) {
				obj.put("erreur", e.getMessage());
				return obj.toString();
			} finally {
				if (bd != null) {
					bd.seDeconnecter();
				}
			}

		} else {
			obj.put("erreur", "pas de login et/ou mot de passe");
			return obj.toString();
		}

		return obj.toString();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String authentifier(@QueryParam("login") String login, @QueryParam("password") String pwd,
			@Context HttpServletRequest request, @Context HttpServletResponse response)
					throws ServletException, IOException {
		JSONObject obj = new JSONObject();

		if ((login != null) && (pwd != null)) {
			AccesBD bd = null;
			try {
				bd = new AccesBD(IServlet.DB_DRIVER);
				bd.seConnecter(IServlet.DB_URL, IServlet.DB_LOGIN, IServlet.DB_PWD);
				int idClient = bd.authentifier(login, pwd);
				if (idClient > 0) {
					Client client = bd.selectUtilisateur(idClient);
					obj.put("numeroClient", client.getNumero());
					obj.put("nom", client.getNom());
					obj.put("prenom", client.getPrenom());
					obj.put("age", client.getAge());
					Compte[] comptes = client.getComptes();
					List<JSONObject> listCompte = new ArrayList<JSONObject>();
					for (Compte compte : comptes) {
						JSONObject comptesJSON = new JSONObject();
						comptesJSON.put("numeroCompte", compte.getNumero());
						comptesJSON.put("libelle", compte.getLibelle());
						comptesJSON.put("solde", compte.getSolde());
						listCompte.add(comptesJSON);
					}
					obj.put("comptes", listCompte);
				} else {
					obj.put("erreur", "pas un client");
					request.setAttribute("erreur", obj.toString());
				}
			} catch (SQLException e) {
				AuthentificationRest.LOG.error(e.getMessage());
				return obj.toString();
			} finally {
				if (bd != null) {
					bd.seDeconnecter();
				}
			}

		} else {
			obj.put("erreur", "pas de login et/ou mot de passe");
			request.setAttribute("erreur", obj.toString());
			return obj.toString();
		}

		request.setAttribute("objJSON", obj);
		return obj.toString();
	}

}

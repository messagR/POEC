<%@page import="fr.web.ServletCompte"%>
<%@page import="java.io.IOException,java.io.InputStream,java.io.PrintWriter,java.sql.SQLException,java.util.List,java.util.Properties"%>
<%@page import="javax.servlet.Servlet,javax.servlet.ServletConfig,javax.servlet.ServletException,javax.servlet.annotation.WebServlet,javax.servlet.http.HttpServlet,javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse"%>
<%@page import="fr.banque.entity.ICompte,fr.banque.entity.ICompteASeuil,fr.banque.entity.ICompteRemunere,fr.banque.exception.ClientIntrouvableException,projetBd.AccesDB"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Insert title here</title>
		<link rel='stylesheet' href='css/styles.css' >
	</head>
	<body>
	<%
		int idClient = Integer.parseInt(request.getParameter("id"));
		Properties mesProperties = new Properties();
		// chemin a partir du src
		try (InputStream is = ServletCompte.class.getClassLoader().getResourceAsStream("mesPreferences.properties")) {
			mesProperties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Nom du driver pour acceder a la base de donnee.
		// Lire la documentation associee a sa base de donnees pour le connaitre
		final String utilDbDriver = mesProperties.getProperty("utilDb.driver");
		// url d'acces a la base de donnees
		final String utilDbUrl = mesProperties.getProperty("utilDb.url");
		// login d'acces a la base de donnees
		final String utilDbLogin = mesProperties.getProperty("utilDb.login");
		// mot de passe d'acces a la base de donnees
		final String utilDbPassword = mesProperties.getProperty("utilDb.password");

		AccesDB utilDb = null;
		try {
			utilDb = new AccesDB(utilDbDriver);
			utilDb.seConnecter(utilDbLogin, utilDbPassword, utilDbUrl);
			List<ICompte> listeCompte = null;
			listeCompte = utilDb.listeCompte(idClient);
			if (!listeCompte.isEmpty()) {
	%>
		<h1>Les comptes du client n°<%=idClient %></h1>
			<table border=1>
				<tr style='background-color:#999;color:#000;'>
					<td>Solde</td>
					<td>Taux</td>
					<td>Seuil</td>
					<td>Comptes</td>
				</tr>
	<%			for (ICompte compte : listeCompte) {%>
				<tr>
					<td><%=compte.getSolde()%></td>
	<%
					if (compte instanceof ICompteRemunere) {
						ICompteRemunere compteRem = (ICompteRemunere) compte;
	%>
					<td><%=compteRem.getTaux() %></td>
	<%				} else {%>
					<td>Pas de Taux</td>
	<%
					}
					if (compte instanceof ICompteASeuil) {
						ICompteASeuil compteASeuil = (ICompteASeuil) compte;
	%>
					<td><%=compteASeuil.getSeuil() %></td>
	<%				} else {%>
					<td>Pas de Seuil</td>
	<%				}%>
					<td>
						<form action='./Operation2.jsp' method='post'>
							<input type='hidden' name='id' value='<%=compte.getNumero() %>'>
							<input type='submit' value='Voir ses operations'>
						</form>
					</td>
				</tr>
	<%			}%>
			</table>
	<%		} else {%>
			<h1>Le client n°<%=idClient %> n'a pas de compte</h1>
	<%		}
		} catch (ClientIntrouvableException e) {
	%>
			<h1>Client n°<%=idClient %> introuvable</h1>
	<%	} catch (SQLException e1) {
			out.write(e1.getMessage());
		} finally {
			if (utilDb != null) {
				utilDb.seDeconnecter();
			}
		}
	%>
	</body>
</html>
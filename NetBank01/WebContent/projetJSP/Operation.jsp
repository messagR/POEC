<%@page import="fr.web.projetServlet.ServletOperation"%>
<%@page import="java.io.IOException,java.io.InputStream,java.io.PrintWriter,java.sql.SQLException,java.text.SimpleDateFormat,java.util.List,java.util.Properties"%>
<%@page import="javax.servlet.Servlet,javax.servlet.ServletConfig,javax.servlet.ServletException,javax.servlet.annotation.WebServlet,javax.servlet.http.HttpServlet,javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse"%>
<%@page import="fr.banque.entity.IOperation,fr.banque.exception.CompteIntrouvableException,projetBd.AccesDB"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<%
		int idCompte = Integer.parseInt(request.getParameter("id"));
	%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Les operations du compte n°<%=idCompte %> en JSP</title>
<link rel='stylesheet' href='../css/styles.css' >
</head>
<body>
	<%
		Properties mesProperties = new Properties();
		// chemin a partir du src
		try (InputStream is = ServletOperation.class.getClassLoader().getResourceAsStream("mesPreferences.properties")) {
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
			List<IOperation> listeOperation = null;
			try {
				listeOperation = utilDb.rechercherOp(idCompte);
				if (!listeOperation.isEmpty()) {
	%>
		<h1>Les operations du compte n°<%=idCompte %> en JSP</h1>
		<table border=1>
			<tr style='background-color:#999;color:#000;'>
				<td>Date</td>
				<td>Libelle</td>
				<td>Montant</td>
			</tr>
	<%
					for (IOperation operation : listeOperation) {
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	%>
			<tr>
				<td><%=sdf.format(operation.getDate()) %></td>
				<td><%=operation.getLibelle() %></td>
				<td><%=operation.getMontant() %> &euro;</td>
			</tr>
	<%				}%>
		</table>
	<%			} else {%>
		<h1>Le compte n°<%=idCompte %> n'a pas d'operation</h1>
	<%
				}
			} catch (CompteIntrouvableException e) {
	%>
		<h1>Compte n°<%=idCompte %> introuvable</h1>
	<%
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			if (utilDb != null) {
				utilDb.seDeconnecter();
			}
		}
	%>

</body>
</html>
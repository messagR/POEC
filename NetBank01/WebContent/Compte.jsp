<%@page import="fr.banque.entity.ICompteASeuil,fr.banque.entity.ICompteRemunere,fr.banque.entity.ICompte,java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="includes/TagLibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Les comptes du client en Servlet et JSP avec tagLibs</title>
		<link rel='stylesheet' href='<c:url value="/css/styles.css" />' >
	</head>
	<body>
		<c:if test="${erreur != null}">
    		<h3><c:out value="${erreur}"/></h3>
    	</c:if>
		<c:if test="${! empty IdClient}">
			<c:if test="${! empty ListeCompte}">
				<h1>Les comptes du client n°<c:out value="${IdClient}"/> en Servlet et JSP avec tagLibs</h1>
				<table border=1>
					<tr style='background-color:#999;color:#000;'>
						<td>Solde</td>
						<td>Taux</td>
						<td>Seuil</td>
						<td>Comptes</td>
					</tr>
					<c:forEach items="${ListeCompte}" var="compte">
						<tr>
							<td><c:out value="${compte.solde}"/></td>
							<c:if test="${! empty compte.taux}">
								<td><c:out value="${compte.taux}"/></td>
							</c:if>
							<c:if test="${empty compte.taux}">
								<td>Pas de Taux</td>
							</c:if>
							<c:if test="${! empty compte.seuil}">
								<td><c:out value="${compte.seuil}"/></td>
							</c:if>
							<c:if test="${empty compte.seuil}">
								<td>Pas de Seuil</td>
							</c:if>
							<td>
								<form action='<c:url value="/ServletOperation" />' method='post'>
									<input type='hidden' name='id' value='<c:out value="${compte.numero}"/>'>
									<input type='submit' value='Voir ses operations'>
								</form>
							</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
			<c:if test="${empty ListeCompte}">
				<h1>Le client n°<c:out value="${IdClient}"/> n'a pas de compte</h1>
			</c:if>
		</c:if>
		<c:if test="${! empty idClient}">
				<h1>Client introuvable</h1>
		</c:if>
	</body>
</html>
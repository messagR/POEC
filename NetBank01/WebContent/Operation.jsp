<%@page import="java.text.SimpleDateFormat,fr.banque.entity.IOperation,java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="includes/TagLibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Les operations du compte en Servlet et JSP avec tagLibs</title>
		<link rel='stylesheet' href='<c:url value="/css/styles.css" />' >
	</head>
	<body>
		<c:if test="${erreur != null}">
    		<h3><c:out value="${erreur}"/></h3>
    	</c:if>
		<c:if test="${! empty IdCompte}">
			<c:if test="${! empty ListeOperation}">
				<h1>Les operations du compte n°<c:out value="${IdCompte}"/> en Servlet et JSP avec tagLibs</h1>
				<table border=1>
					<tr style='background-color:#999;color:#000;'>
						<td>Date</td>
						<td>Libelle</td>
						<td>Montant</td>
					</tr>
					<c:forEach items="${ListeOperation}" var="operation">
						<tr>
							<td><fmt:formatDate value="${operation.date}" type="date" pattern="dd MMM yyy 'à' HH:mm"/></td>
							<td><c:out value="${operation.libelle}"/></td>
							<td><fmt:formatNumber value="${operation.montant}" pattern=".00"/> &euro;</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
			<c:if test="${empty ListeOperation}">
				<h1>Le compte n°<c:out value="${IdCompte}"/> n'a pas d'operation</h1>
			</c:if>
		</c:if>
		<c:if test="${empty IdCompte}">
			<h1>Compte introuvable</h1>
		</c:if>
	</body>
</html>
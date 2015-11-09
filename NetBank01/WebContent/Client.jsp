<%@page import="fr.banque.entity.ICompteASeuil,fr.banque.entity.ICompteRemunere,fr.banque.entity.ICompte,fr.banque.entity.IClient"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="includes/TagLibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Les clients en Servlet et JSP avec tagLibs</title>
		<link rel='stylesheet' href='<c:url value="/css/styles.css" />' >
		<script type='text/javascript' src='<c:url value="/JS/jquery.min.js" />'></script>
	</head>
	<body>
		<c:if test="${erreur != null}">
    		<h3><c:out value="${erreur}"/></h3>
    	</c:if>
		<c:if test="${! empty ListeClient}">
			<h1>Les clients en Servlet et JSP avec tagLibs</h1>
			<table border=1>
				<tr style='background-color:#999;color:#000;'>
					<td>Nom</td>
					<td>Prenom</td>
					<td>Age</td>
					<td>Comptes</td>
				</tr>
				<c:forEach items="${ListeClient}" var="client">
					<tr class='client'>
						<td><c:out value="${client.nom}" /></td>
						<td><c:out value="${client.prenom}" /></td>
						<td><c:out value="${client.age}" /></td>
						<td>
							<form action='<c:url value="/ServletCompte" />' method='post'>
								<input type='hidden' name='id' value='<c:out value="${client.numero}"/>'>
								<input type='submit' value='Voir ses comptes'>
							</form>
						</td>
					</tr>
					<tr class='comptes' style='background-color:#ddd;'>
						<td colspan=4>
							<c:if test="${! empty client.comptes}">
								<h1>Ses comptes</h1>
								<table border=1>
									<tr style='background-color:#999;color:#000;'>
										<td>Solde</td>
										<td>Taux</td>
										<td>Seuil</td>
										<td>Comptes</td>
									</tr>
									<c:forEach items="${client.comptes}" var="compte">
										<c:if test="${compte != null}">
											<tr style='background-color:#fff;'>
												<td><c:out value="${compte.solde}"/></td>
	          									<c:if test="${compte.getClass().name == 'fr.banque.entity.CompteASeuilRemunere' || compte.getClass().name == 'fr.banque.entity.CompteRemunere'}">
													<td><c:out value="${compte.taux}"/></td>
												</c:if> 
												<c:if test="${compte.getClass().name != 'fr.banque.entity.CompteASeuilRemunere' && compte.getClass().name != 'fr.banque.entity.CompteRemunere'}">
													<td>Pas de Taux</td>
												</c:if>
												<c:if test="${compte.getClass().name == 'fr.banque.entity.CompteASeuilRemunere' || compte.getClass().name == 'fr.banque.entity.CompteASeuil'}">
													<td><c:out value="${compte.seuil}"/></td>
												</c:if>
												<c:if test="${compte.getClass().name != 'fr.banque.entity.CompteASeuilRemunere' && compte.getClass().name != 'fr.banque.entity.CompteASeuil'}">
													<td>Pas de Seuil</td>
												</c:if>
												<td>
													<form action='<c:url value="/ServletOperation" />' method='post' name='formulaire'>
														<input type='hidden' name='id' value='<c:out value="${compte.numero}"/>'>
														<div class='operation' style='cursor:pointer;'>Voir ses operations</div>
													</form>
												</td>
											</tr>
										</c:if>
									</c:forEach>
								</table>
							</c:if>
							<c:if test="${empty client.comptes}">
								<h1>Pas de compte</h1>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		<c:if test="${empty ListeClient}">
				<h1>Aucun client</h1>
		</c:if>
		<script>
			$(document).ready(function(){
				$('.comptes').css('display','none');
				$('.client').css('cursor','pointer');
			});
			$('.client').click(function(){
				$(this).next('.comptes').fadeToggle();
			});
			$('.operation').click(function(){
				$(this).parent().submit();
			});
		</script>
	</body>
</html>
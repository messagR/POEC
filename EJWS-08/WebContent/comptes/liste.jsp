<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" buffer="128kb"%>
<%-- On declare le fait que l'on va utiliser la taglib JSTL --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Liste de vos comptes.</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<c:url value="/css/banque.css"/>" rel="stylesheet"
	type="text/css">
</head>

<body class="elBody">

	<form id="frmListeCompte" name="frmListeCompte"
		action="<c:url value="/ServletHistorique"/>" method="post">

		<table border="0" width="100%">
			<tr>
				<td align="center" valign="top"><img
					src="<c:url value="/images/titre.jpg"/>" border="0" height="98"
					alt="" /></td>
			</tr>
			<tr>
				<td><hr></td>
			</tr>
			<tr>
				<td align="center"><c:if test="${!empty listeCpt}">
						<p class="elTitre2">Liste de vos comptes sur Net Banque</p>
						<table border="1" width="60%">
							<tr bgcolor="white">
								<td class="elLibelleTableau">Num&eacute;ro</td>
								<td class="elLibelleTableau">D&eacute;signation</td>
								<td class="elLibelleTableau">Taux</td>
								<td class="elLibelleTableau">D&eacute;couvert
									autoris&eacute;</td>
								<td class="elLibelleTableau">Solde</td>
							</tr>
							<c:forEach items="${listeCpt}" var="compte" varStatus="iter">
								<tr class="elLigneTableau<c:out value="${iter.count%2+1}"/>">
									<td class="elLibelleTableau"><c:url
											value="ServletHistorique" var="urlPourCpt">
											<c:param name="inNumeroCompte" value="${compte.numero}"></c:param>
										</c:url> <a href="<c:out value="${urlPourCpt}"/>"><c:out
												value="${compte.numero}" /></a></td>
									<td class="elLibelleTableau"><c:out
											value="${compte.libelle}" /></td>
									<c:if
										test="${compte.getClass().name =='fr.banque.CompteASeuilRemunere' || compte.getClass().name =='fr.banque.CompteRemunere'}">
										<td class="elLibelleTableau"><c:out
												value="${compte.taux}" /></td>
									</c:if>
									<c:if
										test="${compte.getClass().name !='fr.banque.CompteASeuilRemunere' && compte.getClass().name !='fr.banque.CompteRemunere'}">
										<td class="elLibelleTableau">--</td>
									</c:if>
									<c:if
										test="${compte.getClass().name =='fr.banque.CompteASeuilRemunere' || compte.getClass().name =='fr.banque.CompteASeuil'}">
										<td class="elLibelleTableau"><c:out
												value="${compte.seuil}" /></td>
									</c:if>
									<c:if
										test="${compte.getClass().name !='fr.banque.CompteASeuilRemunere' && compte.getClass().name !='fr.banque.CompteASeuil'}">
										<td class="elLibelleTableau">--</td>
									</c:if>
									<td class="elLibelleTableau"><c:out
											value="${compte.solde}" /></td>
								</tr>
							</c:forEach>
						</table>
					</c:if> <c:if test="${empty listeCpt}">
			Pas de compte pour cet utilisateur.
		</c:if>

					<p>
						<a href="<c:url value="/ServletMenu"/>"> <img
							src="<c:url value="/images/menu.gif"/>" width="98" height="33"
							border="0" alt="" />
						</a>
					</p></td>
			</tr>
		</table>
	</form>

</body>
</html>

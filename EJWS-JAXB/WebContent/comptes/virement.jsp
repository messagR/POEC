<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" 
	buffer="128kb"%>
<%-- On declare le fait que l'on va utiliser la taglib JSTL --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Gestion des Virements</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<c:url value="/css/banque.css"/>" rel="stylesheet"
	type="text/css">
</head>

<body class="elBody">

	<form name="frmVirements" action="<c:url value="/ServletVirement"/>"
		method="post">

		<table border="0" width="100%" align="center">
			<tr>
				<td align="center" valign="top"><img
					src="<c:url value="/images/titre.jpg"/>" border="0" height="98"
					alt="" /></td>
			</tr>
			<tr>
				<td>
					<hr>
				</td>
			</tr>
			<tr>
				<td><c:if test="${!empty requestScope.erreur}">
						<p>
							<c:out value="${requestScope.erreur}" />
						</p>
					</c:if> <c:if test="${!empty requestScope.message}">
						<p>
							<c:out value="${requestScope.message}" />
						</p>
					</c:if></td>
			</tr>
			<tr>
				<td><br />
				<br />
					<table width="60%" border="1" align="center">
						<tr>
							<td align="left">&nbsp;<img
								src="<c:url value="/images/puce.gif"/>" width="13" height="18"
								alt="" />&nbsp;Comptes &eacute;metteurs
							</td>
							<td><select name="inCmptEme" id="inCmptEme">
									<option value="">&nbsp;&nbsp;&nbsp;--&nbsp;Choix&nbsp;--&nbsp;&nbsp;&nbsp;</option>
									<c:forEach items="${listeCpt}" var="compte">
										<option value="<c:out value="${compte.numero}"/>"
											<c:if test="${!empty pCmptEme && pCmptEme.equals(compte.numero)}">selected="selected"</c:if>>
											<c:out value="${compte.libelle}" />
										</option>
									</c:forEach>
							</select></td>
						</tr>
						<tr>
							<td align="left">&nbsp;<img
								src="<c:url value="/images/puce.gif"/>" width="13" height="18"
								alt="" />&nbsp;Comptes destinataires
							</td>
							<td><select name="inCmptDes" id="inCmptDes">
									<option value="">&nbsp;&nbsp;&nbsp;--&nbsp;Choix&nbsp;--&nbsp;&nbsp;&nbsp;</option>
									<c:forEach items="${listeCpt}" var="compte">
										<option value="<c:out value="${compte.numero}"/>"
											<c:if test="${!empty pCmptDes && pCmptDes.equals(compte.numero)}">selected="selected"</c:if>>
											<c:out value="${compte.libelle}" />
										</option>
									</c:forEach>
							</select></td>
						</tr>
						<tr>
							<td align="left">&nbsp;<img
								src="<c:url value="/images/puce.gif"/>" width="13" height="18"
								alt="" />&nbsp;Montant du virement
							</td>
							<td><input type="text" name="inMontant"
								value="<c:out value="${requestScope.pMontant}"/>" size="12" /></td>
						</tr>
					</table>
					<p>&nbsp;</p>
					<table width="200" border="0" align="center">
						<tr>
							<td><a href="javascript:frmVirements.submit()"> <img
									src="<c:url value="/images/bouton-validez.gif"/>" width="98"
									height="33" border="0" alt="" />
							</a></td>
							<td><a href="<c:url value="/ServletMenu"/>"> <img
									src="<c:url value="/images/bouton-annuler.gif"/>" width="98"
									height="34" border="0" alt="" />
							</a></td>
						</tr>
					</table></td>
			</tr>
		</table>
	</form>
</body>
</html>

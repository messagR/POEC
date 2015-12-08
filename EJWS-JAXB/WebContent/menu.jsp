<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" buffer="128kb"%>
<%-- On declare le fait que l'on va utiliser la taglib JSTL --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>NetBanque Menu</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<c:url value="/css/banque.css"/>" rel="stylesheet">
</head>

<body class="elBody">
	<table border="0" width="100%">
		<tr>
			<td align="center" valign="top"><img
				src="<c:url value="/images/image-bienvenue.jpg"/>" border="0"
				height="98" alt="" /></td>
		</tr>
		<tr>
			<td>
				<hr>
			</td>
		</tr>
		<tr>
			<td align="center">
				<table>
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
						<td>
							<table width="100%" border="1">
								<tr class="elLigneTableau1">
									<td width="446" class="elCelluleTableau3"><img
										src="<c:url value="/images/puce.gif"/>" width="13" height="18"
										alt="" />&nbsp; <a href="<c:url value="/ServletCompte"/>">
											Liste de vos comptes</a></td>
								</tr>
								<tr class="elLigneTableau2">
									<td width="446" class="elCelluleTableau3"><img
										src="<c:url value="/images/puce.gif"/>" width="13" height="18"
										alt="" />&nbsp; <a href="<c:url value="/ServletVirement"/>">
											Virement </a></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>

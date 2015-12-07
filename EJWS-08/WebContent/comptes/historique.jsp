<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" buffer="128kb"%>
<%-- On declare le fait que l'on va utiliser la taglib JSTL --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Historique de vos operations.</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<c:url value="/css/banque.css"/>" rel="stylesheet"
	type="text/css">
<link href="<c:url value="/css/calendar.css"/>" rel="stylesheet"
	type="text/css">
<script language="JavaScript"
	src="<c:url value="/librairie/Calendarcode.js"/>"
	type="text/javascript">
   // Inclusion du calendrier
  </script>
</head>

<body class="elBody">

	<div id="popupcalendar" class="text">&nbsp;</div>
	<form id="frmListeOperations" name="frmListeOperations"
		action="<c:url value="/ServletHistorique"/>" method="post">

		<input type="hidden" name="inNumeroCompte"
			value="<c:out value="${requestScope.pIdCpt}"/>" />

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
				<td align="center">
					<p style="">
						<!-- Erreur -->
					</p>
					<p>&nbsp;</p>
					<p style="">
						Historique de vos op&eacute;rations effectu&eacute;es sur le
						compte n&deg;
						<c:out value="${requestScope.pIdCpt}" />
					</p>
					<p style="">Crit&egrave;res de recherche :</p>
					<table width="70%" border="1">
						<tr>
							<td width="446" bgcolor="#ffffff" class="elLibelleTableau">
								Date</td>
							<td width="138" bgcolor="#ffffff" class="elLibelleTableau">
								Type</td>
						</tr>
						<tr>
							<td width="460" bgcolor="#fae6a0" class="elLibelleTableau">
								Du <input type="text" id="inDateDebut" name="inDateDebut"
								value="<c:out value="${requestScope.pDateDebut}"/>" /> <a
								class="so-BtnLink"
								onclick="showCalendar('frmListeOperations','inDateDebut','IMG_DATE_DEBUT');return false;">
									<img align="MIDDLE" border="0" name="IMG_DATE_DEBUT"
									id="IMG_DATE_DEBUT"
									src="<c:url value="/images/date_icon.gif"/>" WIDTH="14"
									HEIGHT="14" alt="" />
							</a> &nbsp;&nbsp; Au <input type="text" name="inDateFin"
								id="inDateFin" value="<c:out value="${requestScope.pDateFin}"/>" />
								<a class="so-BtnLink"
								onclick="showCalendar('frmListeOperations','inDateFin','IMG_DATE_FIN');return false;">
									<img align="MIDDLE" border="0" name="IMG_DATE_FIN"
									id="IMG_DATE_FIN" src="<c:url value="/images/date_icon.gif"/>"
									WIDTH="14" HEIGHT="14" alt="" />
							</a>

							</td>
							<td width="138" bgcolor="#fae6a0" class="elLibelleTableau">
								<p>
									Credit <input type="checkbox" name="inCredit"
										<c:if test="${empty requestScope.pCrediDebit || requestScope.pCrediDebit}">checked="checked"</c:if> />
								</p>
								<p>
									Debit <input type="checkbox" name="inDebit"
										<c:if test="${empty requestScope.pCrediDebit || !requestScope.pCrediDebit}">checked="checked"</c:if> />
								</p>
							</td>
						</tr>
					</table>
					<p>
						<a href="javascript:frmListeOperations.submit()"> <img
							src="<c:url value="/images/rechercher.gif"/>" width="98"
							height="33" border="0" alt="" />
						</a>
					</p>
					<p>&nbsp;</p> <c:if test="${!empty listeOpt}">
						<table border="1" width="70%">
							<tr bgcolor="white">
								<td class="elLibelleTableau">Date</td>
								<td class="elLibelleTableau">Libell&eacute;</td>
								<td class="elLibelleTableau">Montant</td>
							</tr>
							<c:forEach items="${listeOpt}" var="operation" varStatus="iter">
								<tr class="elLigneTableau<c:out value="${iter.count%2+1}"/>">
									<td class="elLibelleTableau"><fmt:formatDate
											value="${operation.date}" pattern="dd/MM/yyyy HH:mm:ss" /></td>
									<td class="elLibelleTableau"><c:out
											value="${operation.libelle}" /></td>
									<td class="elLibelleTableau"><c:out
											value="${operation.montant}" /></td>
								</tr>
							</c:forEach>
						</table>
					</c:if> <c:if test="${empty listeOpt}">
			Pas d'opération pour ce compte.
		</c:if>

					<p>&nbsp;</p>
					<table width="150">
						<tr>
							<td><a href="<c:url value="/ServletCompte"/>"> <img
									src="<c:url value="/images/liste-comptes.gif"/>" width="103"
									height="33" border="0" alt="" />
							</a></td>
							<td>&nbsp;</td>
							<td><a href="<c:url value="/ServletMenu"/>"> <img
									src="<c:url value="/images/menu.gif"/>" width="98" height="33"
									border="0" alt="" />
							</a></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>

</body>
</html>

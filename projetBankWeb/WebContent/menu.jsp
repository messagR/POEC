<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/includes/TagLibs.jsp" %>
<!doctype html>
 <html lang="fr">
	<head>
		<meta charset="UTF-8"> <!-- UTF-8 pour IE sinon peut importe la casse -->
		<TITLE>Ma banque - Menu</TITLE>
		
		<jsp:include page="/includes/css.jsp" />
		
	</head>
	<body>
		<jsp:include page="/includes/scripts.jsp" />
<!------------------------------ ENTETE ------------------------------>
		<header>
		 	<h1>MA BANQUE PERSO</h1>
		</header>
<!------------------------------ /ENTETE ----------------------------->
<!------------------------------ CORPS ------------------------------>
		<div class="contenu">
			<div class="container">
				<div class="row">
					<div class="twelve columns">
					<jsp:include page="/includes/messages.jsp" />
					</div>
				</div>
				<div class="row">
					<div class="twelve columns">
							<ul>
		                      	<li>
	  								<form id="frmListeCompte" name="frmListeCompte" action="<c:url value="/ServletListeCompte"/>" method="post">
		                      			<a href="javascript:frmListeCompte.submit()"> Liste de vos comptes</a>
				          			</form>
		                      	</li>
		                   		<li>
	  								<form id="frmVirement" name="frmVirement" action="<c:url value="/ServletVirement" />" method="post">
		                      			<a href="javascript:frmVirement.submit()"> Virement</a>
				          			</form>
		                   		</li>
		                   		<li>
	  								 Nombre de client connecte : 
			                   		<c:if test="${empty listeConnectes}">0</c:if>
			                   		<c:if test="${!empty listeConnectes}"><c:out value="${listeConnectes.size()}" /></c:if>
		                   		</li>
								<c:if test="${banquier}">
			                   		<li>
		  								<form id="frmChangerClient" name="frmChangerClient" action="<c:url value="/ServletChoixClient" />" method="post">
			                      			<a href="javascript:frmChangerClient.submit()"> Changer de client</a>
					          			</form>
			                   		</li>
								</c:if>
		                   		<li>
	  								<form id="frmDeconnexion" name="frmDeconnexion" action="<c:url value="/ServletDeconnexion" />" method="post">
		                      			<a href="javascript:frmDeconnexion.submit()"> Deconnexion</a>
				          			</form>
		                   		</li>
							</ul>
					</div>
					<div class="three columns">&nbsp;</div>
				</div> 
			</div><!-- fin du container -->
		</div>
<!------------------------------ /CORPS ----------------------------->
		<jsp:include page="/includes/pied.jsp" />
	</body>
 </html>

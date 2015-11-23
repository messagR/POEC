<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/includes/TagLibs.jsp" %>
<!doctype html>
 <html lang="fr">
	<head>
		<meta charset="UTF-8"> <!-- UTF-8 pour IE sinon peut importe la casse -->
		<TITLE>Ma banque</TITLE>
		
		<jsp:include page="/includes/css.jsp" />
		
	</head>
	<body>
	<!-- jmeter:page_index -->
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
  						<form id="frmAuthentification" name="frmAuthentification" action="<c:url value="/ServletAccueil"/>" method="post">
  							<a href="javascript:frmAuthentification.submit()">S'authentifier</a>
			          	</form>
			          	<br/>
                   		 Nombre de client connecte : 
                   		 <c:if test="${empty listeConnectes}">0</c:if>
                   		 <c:if test="${!empty listeConnectes}"><c:out value="${listeConnectes.size()}" /></c:if>
			        </div>
				</div>
			</div><!-- fin du container -->
		</div>
<!------------------------------ /CORPS ----------------------------->
		<jsp:include page="/includes/pied.jsp" />
	</body>
 </html>

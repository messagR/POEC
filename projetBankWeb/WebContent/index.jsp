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
  						<form id="frmAuthentification" name="frmAuthentification" action="<c:url value="/login.jsp"/>" method="post">
  							<a href="javascript:frmAuthentification.submit()">S'authentifier</a>
			          	</form>
			        </div>
				</div>
			</div><!-- fin du container -->
		</div>
<!------------------------------ /CORPS ----------------------------->
		<jsp:include page="/includes/pied.jsp" />
	</body>
 </html>

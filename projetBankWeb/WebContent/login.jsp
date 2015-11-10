<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/includes/TagLibs.jsp" %>
<!doctype html>
 <html lang="fr">
	<head>
		<meta charset="UTF-8"> <!-- UTF-8 pour IE sinon peut importe la casse -->
		<TITLE>Ma banque - Authentification</TITLE>
		
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
  				<form id="frmLogin" name="frmLogin" action="<c:url value="/ServletLogin"/>" method="post">
					<div class="row">
						<div class="twelve columns">
						 	<h2>Veuillez entrer votre nom d'utilisateur et votre mot de passe</h2>
							<jsp:include page="/includes/messages.jsp" />
						</div>
					</div>
					<div class="row">
						<div class="two columns">&nbsp;</div>
						<div class="four columns">
						 	Nom d'utilisateur
						</div>
						<div class="four columns">  
							<input type="text" name="inLogin" id="nom"/>
						</div>
						<div class="two columns">&nbsp;</div>
					</div>
					<div class="row">
						<div class="two columns">&nbsp;</div>
						<div class="four columns">
						 	Mot de passe
						</div>
						<div class="four columns">  
							<input type="password" name="inPass" id="mdp"/> 
						</div>
						<div class="two columns">&nbsp;</div>
					</div> 
					<div class="row">
						<div class="twelve columns">
	                        <input type="button" width="98" height="33" id="valider" value="Valider"/>
						</div>
					</div>
				</form>
			</div><!-- fin du container -->
		</div>
<!------------------------------ /CORPS ----------------------------->
		<jsp:include page="/includes/pied.jsp" />
		<script>	
			
			$('#valider').click(function(){
				if($('#nom').val() == "" || $('#mdp').val() == ""){
					var n = noty({
					    layout: 'center',
						type: 'warning', 
						text: 'Vous devez renseigner votre nom d\'utilisateur et votre mot de passe',
					    animation: {
					        open: {height: 'toggle'},
					        close: {height: 'toggle'}
					    }
					});
				}else{
					frmLogin.submit();
				}
			});
					
		</script>
	</body>
 </html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/includes/TagLibs.jsp" %>
<!doctype html>
 <html lang="fr">
	<head>
		<meta charset="UTF-8"> <!-- UTF-8 pour IE sinon peut importe la casse -->
		<TITLE>Ma banque - Liste des clients</TITLE>
		
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
						<h2>Liste des clients</h2>
						<jsp:include page="/includes/messages.jsp" />
				    </div>
				</div>
				<div class="row">
					<div class="twelve columns">
						<c:if test="${! empty listClient}">
						<form id="frmListeClient" name="frmListeClient" action="<c:url value="/ServletListeClient" />" method="post">
							<input type="hidden" id="inClient" name="inClient"/>
								<table id="table_id" class="compact cell-border stripe row-border hover">
	    							<thead>
										<tr>
											<td>Num&eacute;ro</td>
											<td>Nom</td>
											<td>Prenom</td>
										</tr>
									</thead>
									<c:if test="${! empty listClient}">
										<tbody>
											<c:forEach items="${listClient}" var="client" varStatus="status">
												<tr onClick="choixClient(<c:out value="${client.numero}"/>); return false;" style="cursor:pointer;">
													<td><c:out value="${client.numero}"/></td>
													<td><c:out value="${client.nom}"/></td>
													<td><c:out value="${client.prenom}"/></td>
												</tr>
											</c:forEach>
										</tbody>
									</c:if>
								</table>
							</form>
						</c:if>
						<c:if test="${empty listClient}">
							<h3>Il n'y a pas de client</h3>
						</c:if>
					</div>
				</div>
				<div class="row">
					<div class="twelve columns">
  						<form id="frmMenu" name="frmLogin" action="<c:url value="/login.jsp" />" method="post">
	                        <input type="button" width="98" height="33" value="Retour a l'authentification" onClick="frmLogin.submit()"/>
			          	</form>
          			</div>
				</div>
			</div><!-- fin du container -->
		</div>
<!------------------------------ /CORPS ----------------------------->
		<jsp:include page="/includes/pied.jsp" />
		<script>		
			$(document).ready( function () {
			    $('#table_id').DataTable({
				    columns: [
				              { data: 'numero' },
				              { data: 'nom' },
				              { data: 'prenom' }
				          ]
			    });
			});
			
			function choixClient(num){
				$("#inClient").val(num);
				$("#frmListeClient").submit();
			}
		</script>
	</body>
 </html>
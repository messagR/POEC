<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/includes/TagLibs.jsp" %>
<!doctype html>
 <html lang="fr">
	<head>
		<meta charset="UTF-8"> <!-- UTF-8 pour IE sinon peut importe la casse -->
		<TITLE>Ma banque - Liste de vos comptes</TITLE>
		
		<jsp:include page="/includes/css.jsp" />
		
	</head>
	<body>
		<!-- jmeter:page_liste_comptes -->
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
						<h2>Liste de vos comptes</h2>
						<jsp:include page="/includes/messages.jsp" />
				    </div>
				</div>
				<div class="row">
					<div class="twelve columns">
						<c:if test="${! empty listCompte}">
						<form id="frmListeCompte" name="frmListeCompte" action="<c:url value="/ServletListeOperation" />" method="post">
							<input type="hidden" id="inCompte" name="inCompte"/>
								<table id="table_id" class="compact cell-border stripe row-border hover">
	    							<thead>
										<tr>
											<td>Num&eacute;ro</td>
											<td>D&eacute;signation</td>
											<td>Taux</td>
											<td>D&eacute;couvert autoris&eacute;</td>
											<td>Solde</td>
										</tr>
									</thead>
									<c:if test="${! empty listCompte}">
										<tbody>
											<c:forEach items="${listCompte}" var="compte" varStatus="status">
												<tr onClick="rechercheOp(<c:out value="${compte.numero}"/>); return false;" style="cursor:pointer;">
													<td>
														<c:out value="${compte.numero}"/>
													</td>
													<td><c:out value="${compte.libelle}"/></td>
													<c:if test="${! empty compte.taux}">
														<td><c:out value="${compte.taux}"/></td>
													</c:if>
													<c:if test="${empty compte.taux}">
														<td>--</td>
													</c:if>
													<c:if test="${! empty compte.seuil}">
														<td><c:out value="${compte.seuil}"/></td>
													</c:if>
													<c:if test="${empty compte.seuil}">
														<td>--</td>
													</c:if>
													<td><fmt:formatNumber value="${compte.solde}" minFractionDigits="2" maxFractionDigits="2"/></td>
												</tr>
											</c:forEach>
										</tbody>
									</c:if>
								</table>
							</form>
						</c:if>
						<c:if test="${empty listCompte}">
							<script>
								var n = noty({
								    layout: 'center',
									type: 'info', 
									text: 'Vous n\'avez pas de compte',
								    animation: {
								        open: {height: 'toggle'},
								        close: {height: 'toggle'}
								    }
								});
							</script>
						</c:if>
					</div>
				</div>
				<div class="row">
					<div class="twelve columns">
  						<form id="frmMenu" name="frmMenu" action="<c:url value="/ServletMenu" />" method="post">
	                        <input type="button" width="98" height="33" value="Menu" onClick="frmMenu.submit()"/>
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
				              { data: 'libelle' },
				              { data: 'taux' },
				              { data: 'seuil' },
				              { data: 'solde' }
				          ]
			    });
			});
			
			function rechercheOp(num){
				$("#inCompte").val(num);
				$("#frmListeCompte").submit();
			}
		</script>
	</body>
 </html>
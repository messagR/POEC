<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/includes/TagLibs.jsp" %>
<!doctype html>
 <html lang="fr">
	<head>
		<meta charset="UTF-8"> <!-- UTF-8 pour IE sinon peut importe la casse -->
		<TITLE>Ma banque - Gestion des Virements</TITLE>
		
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
						<h2>Gestion des Virements</h2>
						<jsp:include page="/includes/messages.jsp" />
				    </div>
				</div>
				<div class="row">
					<div class="twelve columns">
						<form name="frmVirements" action="<c:url value="/ServletEffectuerVirement"/>" method="post">
							<table border=1>
	    						<thead>
									<tr>
										<td style="padding:10px;">Comptes &eacute;metteurs</td>
										<td style="padding:10px;">Comptes destinataires</td>
										<td style="padding:10px;">Montant du virement</td>
									</tr>
								</thead>
								<tr>
									<td style="padding:10px;">
										<c:if test="${! empty listCompte}">
											<select name="inCmptEme" id="inCmptEme" style="margin-bottom:0px;">
												<option value="">&nbsp;&nbsp;&nbsp;--&nbsp;Choix&nbsp;--&nbsp;&nbsp;&nbsp;</option>
												<c:forEach items="${listCompte}" var="compte">
													<option value="<c:out value="${compte.numero}"/>"><c:out value="${compte.libelle}"/></option>
												</c:forEach>
											</select>
										</c:if>
										<c:if test="${empty listCompte}">
											<h3>Vous n'avez pas de compte</h3>
										</c:if>
									</td>
									<td style="padding:10px;">
										<c:if test="${! empty listCompte}">
											<select name="inCmptDes" id="inCmptDes" style="margin-bottom:0px;">
												<option value="">&nbsp;&nbsp;&nbsp;--&nbsp;Choix&nbsp;--&nbsp;&nbsp;&nbsp;</option>
												<c:forEach items="${listCompte}" var="compte">
													<option value="<c:out value="${compte.numero}"/>"><c:out value="${compte.libelle}"/></option>
												</c:forEach>
											</select>
										</c:if>
										<c:if test="${empty listCompte}">
											<h3>Vous n'avez pas de compte</h3>
										</c:if>
									</td>
									<td style="padding:10px;">
										<input type="text" id="inMontant" name="inMontant" value="<fmt:formatNumber value="0.00" minFractionDigits="2" maxFractionDigits="2"/>" size="12" style="margin-bottom:0px;"/>
									</td>
								</tr>
							</table>
						</form>						
					</div>
				</div>
				<div class="row">
					<div class="twelve columns">
						<form id="frmAnnule" name="frmAnnule" action="<c:url value="/ServletVirement" />" method="post">
	                   		<input type="button" width="98" height="33" value="Valider" id="valider"/>&nbsp;&nbsp;&nbsp;
							<input type="button" width="98" height="33" value="Annuler" onClick="annuler()"/>
	          			</form>
  						<form id="frmMenu" name="frmMenu" action="<c:url value="/menu.jsp" />" method="post">
	                        <input type="button" width="98" height="33" value="Menu" onClick="frmMenu.submit()"/>
			          	</form>
	         		</div>
				</div>
			</div><!-- fin du container -->
		</div>
<!------------------------------ /CORPS ----------------------------->
		<jsp:include page="/includes/pied.jsp" />
		<script>	
			
			$('#valider').click(function(){

				if ($("#inCmptEme").val() == "") {
					var n = noty({
					    layout: 'center',
						type: 'warning', 
						text: 'Vous devez choisir un compte emetteur',
					    animation: {
					        open: {height: 'toggle'},
					        close: {height: 'toggle'}
					    }
					});
				}else{
					if($("#inCmptDes").val() == ""){
						var n = noty({
						    layout: 'center',
							type: 'warning', 
							text: 'Vous devez choisir un compte destinataire',
						    animation: {
						        open: {height: 'toggle'},
						        close: {height: 'toggle'}
						    }
						});
					}else{
						if($("#inCmptDes").val() == $("#inCmptEme").val()){
							var n = noty({
							    layout: 'center',
								type: 'warning', 
								text: 'Le compte emetteur et le compte destinaire doivent etre different',
							    animation: {
							        open: {height: 'toggle'},
							        close: {height: 'toggle'}
							    }
							});
						}else{
							if(parseFloat ($("#inMontant").val()) <= 0){
								var n = noty({
								    layout: 'center',
									type: 'warning', 
									text: 'Le montant doit etre superieur a 0',
								    animation: {
								        open: {height: 'toggle'},
								        close: {height: 'toggle'}
								    }
								});
							}else{alert($("#inMontant").val());
								frmVirements.submit();
							}
						}
					}
				}
			});
			
			function annuler(){
				$('#inCmptEme').val("");
				$('#inCmptDes').val("");
				$('#inMontant').val("0,00");
			}
					
		</script>
	</body>
 </html>
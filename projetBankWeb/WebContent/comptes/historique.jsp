<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/includes/TagLibs.jsp" %>
<!doctype html>
 <html lang="fr">
	<head>
		<meta charset="UTF-8"> <!-- UTF-8 pour IE sinon peut importe la casse -->
		<TITLE>Ma banque - Historique de vos operations</TITLE>
		
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
						<h2>Historique de vos op&eacute;rations effectu&eacute;es sur le compte n&deg; <c:out value="${idCompte}" /></h2>
						<jsp:include page="/includes/messages.jsp" />
				    </div>
				</div>
				<form id="frmListeOperations" name="frmListeOperations" action="<c:url value="/ServletRechercherOperation"/>" method="post">
					<div class="row">
						<div class="twelve columns">
	        				<p>Crit&egrave;res de recherche :</p>
							<table border=1>
								<thead>
									<tr>
										<td style="padding:10px;">Date</td>
										<td style="padding:10px;">Type</td>
									</tr>
								</thead>
								<tr>
									<td style="padding:5px 10px;">
									Du <input type="text" id="inDateDebut" name="inDateDebut" placeholder="JJ/MM/AAAA" style="margin-right:5px; margin-bottom:0px;" value="<c:out value="${dateDebut}"/>"/>
									&nbsp;au <input type="text" name="inDateFin" id="inDateFin" placeholder="JJ/MM/AAAA" style="margin-right:5px; margin-bottom:0px;" value="<c:out value="${dateFin}"/>"/>
									</td>
									<td style="padding:5px 10px;">
							            Credit <input type="checkbox" name="inCredit"  id="inCredit" <c:out value="${credit}"/> style="margin-bottom:0px;"/><br/>
							            Debit <input type="checkbox" name="inDebit" id="inDebit" <c:out value="${debit}"/> style="margin-bottom:0px;"/>
									</td>
								</tr>
							</table>
					    </div>
					</div>
					<div class="row">
						<div class="twelve columns">
		                    <input type="button" width="98" height="33" id="rechercher" value="Rechercher"/>
					    </div>
					</div>
				</form>
				<br/>
				<div class="row">
					<div class="twelve columns">
						<c:if test="${! empty listOperation}">
							<table id="table_id" class="compact cell-border stripe row-border hover">
    							<thead>
									<tr>
										<td>Date</td>
										<td>Libell&eacute;</td>
										<td>Montant</td>
									</tr>
    							</thead>
								<c:if test="${! empty listOperation}">
									<tbody>
										<c:forEach items="${listOperation}" var="operation" varStatus="status">
											<tr>
												<td><fmt:formatDate value="${operation.date}" type="date" pattern="dd MMM yyy"/></td>
												<td><c:out value="${operation.libelle}"/></td>
												<td><fmt:formatNumber value="${operation.montant}" minFractionDigits="2" maxFractionDigits="2"/> &euro;</td>
											</tr>
										</c:forEach>
									</tbody>
								</c:if>
							</table>
						</c:if>
						<c:if test="${empty listOperation}">
							<script>
								var n = noty({
								    layout: 'center',
									type: 'info', 
									text: 'Vous n\'avez pas d\'operation sur ce compte pour ces options',
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
  						<form id="frmListeCompte" name="frmListeCompte" action="<c:url value="/ServletListeCompte" />" method="post">
		                    <input type="button" width="98" height="33" value="Liste des comptes" onClick="frmListeCompte.submit()"/>
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
		
			$(document).ready( function () {
			    $('#table_id').DataTable(); 
				
				$( "#inDateDebut" ).datepicker({
					changeMonth: true,
					changeYear: true,
					showOn: "button",
					buttonImage: "<c:url value="/images/calendar.jpg"/>",
					buttonImageOnly: true,
					buttonText: "Select date",
					maxDate: 0,
					showWeek: true,
					firstDay: 1,
					dateFormat: "dd/mm/yy",
					onClose: function( selectedDate ) {
						$( "#inDateFin" ).datepicker( "option", "minDate", selectedDate );
					}
				});
				$( "#inDateFin" ).datepicker({
					changeMonth: true,
					changeYear: true,
					showOn: "button",
					buttonImage: "<c:url value="/images/calendar.jpg"/>",
					buttonImageOnly: true,
					buttonText: "Select date",
					maxDate: 0,
					showWeek: true,
					firstDay: 1,
					dateFormat: "dd/mm/yy",
					onClose: function( selectedDate ) {
						$( "#inDateDebut" ).datepicker( "option", "maxDate", selectedDate );
					}
				});
				
				$("#inDateDebut").mask("99/99/9999",{placeholder:"JJ/MM/AAAA"});
				$("#inDateFin").mask("99/99/9999",{placeholder:"JJ/MM/AAAA"});
			});
			
			$('#rechercher').click(function(){

				if ($.datepicker.parseDate('dd/mm/yy', $("#inDateDebut").val()) > $.datepicker.parseDate('dd/mm/yy', $("#inDateFin").val())) {
					var n = noty({
					    layout: 'center',
						type: 'warning', 
						text: 'La date de debut est superieure a la date de fin',
					    animation: {
					        open: {height: 'toggle'},
					        close: {height: 'toggle'}
					    }
					});
				}else{
					if($('input:checked').length == 0){
						var n = noty({
						    layout: 'center',
							type: 'warning', 
							text: 'Vous devez faire au moins un choix pour le debit/credit',
						    animation: {
						        open: {height: 'toggle'},
						        close: {height: 'toggle'}
						    }
						});
					}else{
						frmListeOperations.submit();
					}
				}
			});
			
		</script>
	</body>
 </html>
<%@page import="fr.banque.entity.ICompteASeuil,fr.banque.entity.ICompteRemunere,fr.banque.entity.ICompte,fr.banque.entity.IClient"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Insert title here</title>
		<link rel='stylesheet' href='css/styles.css' >
		<script type='text/javascript' src='JS/jquery.min.js'></script>
	</head>
	<body>
	<%
		List<IClient> listeClient = (List<IClient>) request.getAttribute("ListeClient");
		if(listeClient != null){
	%>
		<h1>Les clients</h1>
		<table border=1>
			<tr style='background-color:#999;color:#000;'>
				<td>Nom</td>
				<td>Prenom</td>
				<td>Age</td>
				<td>Comptes</td>
			</tr>
	<%
				ICompte[] listeCompte = null;
				for (IClient client : listeClient) {
	%>
			<tr class='client'>
				<td><%=client.getNom() %></td>
				<td><%=client.getPrenom() %></td>
				<td><%=client.getAge() %></td>
				<td>
					<form action='./ServletCompte' method='post'>
						<input type='hidden' name='id' value='<%=client.getNumero() %>'>
						<input type='submit' value='Voir ses comptes'>
					</form>
				</td>
			</tr>
			<tr class='comptes' style='background-color:#ddd;'>
				<td colspan=4>
	<%
					listeCompte = client.getComptes();
					if (listeCompte != null) {
	%>
					<h1>Ses comptes</h1>
					<table border=1>
						<tr style='background-color:#999;color:#000;'>
							<td>Solde</td>
							<td>Taux</td>
							<td>Seuil</td>
							<td>Comptes</td>
						</tr>
	<%
						for (ICompte compte : listeCompte) {
							if (compte != null) {
	%>
						<tr style='background-color:#fff;'>
							<td><%= compte.getSolde() %></td>
	<%
								if (compte instanceof ICompteRemunere) {
									ICompteRemunere compteRem = (ICompteRemunere) compte;
	%>
									<td><%=compteRem.getTaux() %></td>
	<%							} else {%>
							<td>Pas de Taux</td>
	<%
								}
								if (compte instanceof ICompteASeuil) {
									ICompteASeuil compteASeuil = (ICompteASeuil) compte;
	%>
							<td><%=compteASeuil.getSeuil() %></td>
	<%							} else {%>
							<td>Pas de Seuil</td>
	<%							}%>
							<td>
								<form action='./ServletOperation' method='post' name='formulaire'>
									<input type='hidden' name='id' value='<%=compte.getNumero() %>'>
									<div class='operation' style='cursor:pointer;'>Voir ses operations</div>
								</form>
							</td>
						</tr>
	<%
							}
						}
	%>
					</table>
	<%	 			} else {%>
					<h1>Pas de compte</h1>
	<%				}%>
				</td>
			</tr>
	<%			}%>
		</table>
	<%		} else {%>
		<h1>Aucun client</h1>
	<%	
			}
	%>
		<script>
			$(document).ready(function(){
				$('.comptes').css('display','none');
				$('.client').css('cursor','pointer');
			});
			$('.client').click(function(){
				$(this).next('.comptes').fadeToggle();
			});
			$('.operation').click(function(){
				$(this).parent().submit();
			});
		</script>
	</body>
</html>
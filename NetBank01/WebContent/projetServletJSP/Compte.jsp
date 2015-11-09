<%@page import="fr.banque.entity.ICompteASeuil,fr.banque.entity.ICompteRemunere,fr.banque.entity.ICompte,java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<%
		Integer idClient = (Integer)request.getAttribute("IdClient");
	%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Les comptes du client n°<%=idClient %> en Servlet et JSP</title>
		<link rel='stylesheet' href='../css/styles.css' >
	</head>
	<body>
	<%
		String erreur = (String) request.getAttribute("erreur");
		if(erreur!=null){
	%>
      	<h3><%=erreur %></h3>
	<%
		}
		if(idClient != null){
			List<ICompte> listeCompte = (List<ICompte>) request.getAttribute("ListeCompte");
			if(!listeCompte.isEmpty()){
	%>
		<h1>Les comptes du client n°<%=idClient %> en Servlet et JSP</h1>
			<table border=1>
				<tr style='background-color:#999;color:#000;'>
					<td>Solde</td>
					<td>Taux</td>
					<td>Seuil</td>
					<td>Comptes</td>
				</tr>
	<%			for (ICompte compte : listeCompte) {%>
				<tr>
					<td><%=compte.getSolde()%></td>
	<%
					if (compte instanceof ICompteRemunere) {
						ICompteRemunere compteRem = (ICompteRemunere) compte;
	%>
					<td><%=compteRem.getTaux() %></td>
	<%				} else {%>
					<td>Pas de Taux</td>
	<%
					}
					if (compte instanceof ICompteASeuil) {
						ICompteASeuil compteASeuil = (ICompteASeuil) compte;
	%>
					<td><%=compteASeuil.getSeuil() %></td>
	<%				} else {%>
					<td>Pas de Seuil</td>
	<%				}%>
					<td>
						<form action='./ServletOperation' method='post'>
							<input type='hidden' name='id' value='<%=compte.getNumero() %>'>
							<input type='submit' value='Voir ses operations'>
						</form>
					</td>
				</tr>
	<%			}%>
			</table>
	<%			} else {%>
			<h1>Le client n°<%=idClient %> n'a pas de compte</h1>
	<%			}%>
	<%		} else {%>
			<h1>Client introuvable</h1>
	<%		}%>
	</body>
</html>
<%@page import="java.text.SimpleDateFormat,fr.banque.entity.IOperation,java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel='stylesheet' href='css/styles.css' >
</head>
<body>
	<%
		Integer idCompte = (Integer)request.getAttribute("IdCompte");
		if(idCompte != null){
			List<IOperation> listeOperation = (List<IOperation>) request.getAttribute("ListeOperation");
			if(!listeOperation.isEmpty()){
	%>
		<h1>Les operations du compte n°<%=idCompte %></h1>
		<table border=1>
			<tr style='background-color:#999;color:#000;'>
				<td>Date</td>
				<td>Libelle</td>
				<td>Montant</td>
			</tr>
	<%
					for (IOperation operation : listeOperation) {
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	%>
			<tr>
				<td><%=sdf.format(operation.getDate()) %></td>
				<td><%=operation.getLibelle() %></td>
				<td><%=operation.getMontant() %> &euro;</td>
			</tr>
	<%				}%>
		</table>
	<%			} else {%>
		<h1>Le compte n°<%=idCompte %> n'a pas d'operation</h1>
	<%
				}
			}else{
	%>
		<h1>Compte introuvable</h1>
	<%		}%>

</body>
</html>
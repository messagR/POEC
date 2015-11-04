<%@page import="java.util.Enumeration"%>
<%@page import="java.text.SimpleDateFormat,java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	
	<%	
		Integer valeur = (Integer) request.getAttribute("maClef");
		Integer valeurSession = (Integer) session.getAttribute("uneClef");
		Integer valeurAppli = (Integer) application.getAttribute("encoreUneClef");
		Enumeration en = request.getAttributeNames();
		while(en.hasMoreElements()){
			String clef = (String) en.nextElement();
	%>
		Clef=<%=clef %>, valeur=<%=request.getAttribute(clef) %><br/>	
	<%	
		}
	%>
	Resultat = <%=valeur %><br/><br/>
	<%
		for(int i=0; i<10; i++){
			out.write("Bonjour "+ i +"<br/>\n");
		}
	%>
	<br/><br/>
	<% for(int i=0; i<10; i++){ %>
		Bonjour <% out.write(String.valueOf(i)); %><br/>
		Bonjour <%= i %><br/>
	<%}%>
	<br/><br/>
	Mon adresse IP : <%= request.getRemoteAddr() %> <br/>
	<% 
		//commentaire java invisible cote client
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
	%>
	<%-- commentaire JSP invisible cote client --%>
	<%= sdf.format(new Date()) %><br/>
	<!-- commentaire html visible cote client -->

</body>
</html>
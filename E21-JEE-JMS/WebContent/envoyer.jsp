<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form method="post" action="<c:url value="/JmsProducer"/>">
	Message:
	<textarea name="message" rows="10" cols="50"></textarea><br/><br/>
	<input type="submit" value="Envoyer">
</form><br/><br/>
<a href="<c:url value="/JmsConsumer"/>">Recevoir les messages</a><br/>
<a href="http://localhost:8161/">URL d'administration Active MQ	(admin/admin)</a>
</body>
</html>
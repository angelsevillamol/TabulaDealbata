<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="customerBean" scope="session" class="es.uco.pw.display.javabean.CustomerBean"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Algo no ha salido bien :(</title>
</head>
<body>

if (customerBean == null || customerBean.getEmailUser()=="") {
	// Usuario no logado -> Se invoca al controlador de la funcionalidad
%>
<a href="/WebContent/mvc/controller/loginController.jsp">Acceder</a>
<% } else { %>
	¡¡Bienvenido <jsp:getProperty property="emailUser" name="customerBean"/>!! 
    <a href="/WebContent/mvc/controller/registroController.jsp">Acceder</a>
<% } %>

</body>
</html>
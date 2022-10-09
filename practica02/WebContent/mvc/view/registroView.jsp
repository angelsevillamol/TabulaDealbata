<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="customerBean" scope="session" class="es.uco.pw.display.javabean.CustomerBean"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registrar/title>
</head>
<body>
<%
/* Posibles flujos:
	1) customerBean no está logado -> Se redirige al index.jsp (no debería estar aquí pero hay que comprobarlo)
	2) customerBean no está logado:
		a) Hay parámetros en el request  -> procede del controlador /con mensaje 
		b) No hay parámetros en el request -> procede del controlador /sin mensaje
	*/
String nextPage = "../controller/registroController.jsp";
String messageNextPage = request.getParameter("message");
if (messageNextPage == null) messageNextPage = "";

if (customerBean == null ) {
	//No debería estar aquí -> flujo salta a index.jsp
	nextPage = "../../index.jsp";
} else {
%>
<%= messageNextPage %><br/><br/>
<form method="post" action="../controller/registroController.jsp">
    <label for="id">ID: </label>
	<input type="number" name="id" value="John">
	<label for="name">Nombre: </label>
	<input type="text" name="name" value="John">
    <label for="secondname">Apellidos: </label>
	<input type="text" name="secondname" value="Doe"><br/>
    <label for="date">Fecha de nacimiento: </label>
	<input type="text" name="date" value="30/12/1999"><br/><br/>
	<label for="email">Email: </label>
	<input type="text" name="email" value="john.doe@email.com">	
    <label for="passwd">Passwd: </label>
	<input type="password" name="passwd" value="Doe">
	<br/>
	<input type="submit" value="Registrar">
</form>
<form action="../controller/loginController.jsp">
    <input type="submit" value="Desconectar" />
</form>
<%
}
%>

</body>
</html>
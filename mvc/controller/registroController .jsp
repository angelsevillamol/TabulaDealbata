<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="es.uco.pw.business.user.User,es.uco.pw.data.dao.DAOUser" %>
<jsp:useBean  id="customerBean" scope="session" class="es.uco.pw.display.javabean.CustomerBean"></jsp:useBean>
<%
/* Posibles flujos:
	1) customerBean no está logado (== null) -> Se redirige al index.jsp
	2) customerBean está logado:
		a) Hay parámetros en el request  -> procede de la vista 
		b) No hay parámetros en el request -> procede de otra funcionalidad o index.jsp
	*/
//Caso 1: Por defecto, vuelve al index
String nextPage = "../../index.jsp";
String mensajeNextPage = "";
//Caso 2
if (customerBean != null ) {
    int id = request.getParameter("id");
	String nameUser = request.getParameter("name");
	String secondNameUser = request.getParameter("secondname");
    String dateUser = request.getParameter("date");
    String emailUser = request.getParameter("email");
    String passwdUser = request.getParameter("passwd");

	//Caso 2.a: Hay parámetros -> procede de la VISTA
	if (nameUser != null) {
		//Se accede a bases de datos para obtener el usuario
		DAOUser userDAO = new DAOUser();
        ArrayList<Boolean> intereses = null
		User user = new User(id, nameUser, secondNameUser, dateUser, emailUser, intereses);
        userDao.save(user, passwdUser);

		//Se realizan todas las comprobaciones necesarias del dominio
		//Aquí sólo comprobamos que exista el usuario
		if (user != null && user.getEmail().equalsIgnoreCase(emailUser)) {
			// Usuario válido		
%>
<jsp:setProperty property="emailUser" value="<%=emailUser%>" name="customerBean"/>
<%
		} else {
			// Usuario no válido
			nextPage = "../view/loginView.jsp";
			mensajeNextPage = "El usuario que ha indicado no existe o no es v&aacute;lido";
		}
	//Caso 2.b -> se debe ir a la vista por primera vez
	} else {
		nextPage = "../view/loginView.jsp";		
	}
}
%>
<jsp:forward page="<%=nextPage%>">
	<jsp:param value="<%=mensajeNextPage%>" name="message"/>
</jsp:forward>
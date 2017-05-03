<%-- 
    Document   : tablaHorario
    Created on : 20-mar-2017, 22:32:38
    Author     : Estela
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
         <table>
            <thead>
                <tr>
                    <th>Hora</th>
                    <th>Asignatura</th>
                    <th>Profesor</th>
                    <th>Aula</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="celda" items="${horario.horario}">
                    <tr>
                        <td>${celda.hora}</td>
                        <td>                            
                            <c:if test="${celda.modificacion}=='cancelada'">
                                <b> CANCELADA:  </b>
                            </c:if>
                            <c:if test="${celda.modificacion}=='recuperada'">
                                 Recuperacion: 
                            </c:if>
                            ${celda.nombreAsignatura}
                        </td>
                        <td>
                            <p>${celda.profesor.nombre} ${celda.profesor.apellidos}</p>
                            <p>${celda.profesor.email}</p>
                        </td>
                        <td>${celda.infoAula.nombreAula}      ${celda.infoAula.edificio}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>

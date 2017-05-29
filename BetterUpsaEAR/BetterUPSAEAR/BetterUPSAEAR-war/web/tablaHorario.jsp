<%-- 
    Document   : tablaHorario
    Created on : 20-mar-2017, 22:32:38
    Author     : Estela
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!--%@page contentType="text/css" %-->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="../../general.css" rel="stylesheet" type="text/css" >        
        <title>JSP Page</title>
    </head>
    
    <body>
        <div id="contenido">
            <div class="tabla">
              <h1>Bienvenid@ ${horario.nombreUsuario}</h1>  
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
                            <td class='${celda.modificacion}'>  
                                ${celda.hora}</td>
                            <td class='${celda.modificacion}'>                            
                                <c:if test="${celda.modificacion}=='cancelada'">
                                    <b> CANCELADA:  </b>
                                </c:if>
                                <c:if test="${celda.modificacion}=='recuperada'">
                                     Recuperacion: 
                                </c:if>
                                <span class="canceladaRecuperacion">${celda.modificacion}<br/></span>
                                ${celda.nombreAsignatura}
                            </td>
                            <td class='${celda.modificacion}'>  
                                <p>${celda.profesor.nombre} ${celda.profesor.apellidos}</p>
                                <p>${celda.profesor.email}</p>
                            <td class='${celda.modificacion}'>  
                            <td class='${celda.modificacion}'>  
                                ${celda.infoAula.nombreAula}      ${celda.infoAula.edificio}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            </div>
        </div>
    </body>
</html>

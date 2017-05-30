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
              
              <c:if test="${horario.examen=='true'}">
                  <h2>EXAMENES</h2>
              </c:if>
             <table>
                <thead>
                    <tr>
                        <th>Hora</th>
                        <th>Asignatura</th>  
                        <c:if test="${horario.alumno=='true'}">
                            <th>Profesor</th>
                        </c:if>
                        <th>Curso</th>
                        <th>Aula</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="celda" items="${horario.horario}">
                        <tr>
                            <td class='${celda.modificacion}'>  
                                ${celda.hora}</td>
                            <td class='${celda.modificacion}'>                                                            
                                ${celda.nombreAsignatura}
                                <span class="canceladaRecuperacion"><br/>${celda.modificacion}</span>
                            </td>
                            <c:if test="${horario.alumno=='true'}">
                                <td class='${celda.modificacion}'>  
                                    <p><b>${celda.profesor.nombre} ${celda.profesor.apellidos}</b></p>
                                    <p>${celda.profesor.email}</p>
                                </td>
                            </c:if>
                            <td>
                                ${celda.curso}
                            </td>
                            <td class='${celda.modificacion}'>
                                <c:if test="${horario.alumno=='false'}">
                                    <br>
                                    <div class="tabulador">
                                        <br>
                                    </div>
                                </c:if>
                                ${celda.infoAula.nombreAula}   ${celda.infoAula.edificio}
                                <c:if test="${horario.alumno=='false'}">
                                    <br><br><br>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            </div>
        </div>
    </body>
</html>

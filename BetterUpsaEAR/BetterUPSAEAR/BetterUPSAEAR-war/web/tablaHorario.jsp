<%-- 
    Document   : tablaHorario
    Created on : 20-mar-2017, 22:32:38
    Author     : Estela
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<fmt:setLocale value="es"/>
<!--%@page contentType="text/css" %-->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="general.css" rel="stylesheet" type="text/css" >        
        <title>JSP Page</title>
    </head>
    
    <body>
        <div id="contenido">
            
            
            <table class="cabecera">
                    <tr>
                        <th class="foto"> 
                            <figure>
                                <img class="perfil" width="50%" src="fotos/${horario.id}.jpg"/> 
                            <figcaption> ${(horario.alumno=='true')? 'Alumno' : 'Profesor' }</figcaption>
                            </figure>
                                
                        </th>
                          
                         <th class="carnet">
                            
                             <span class="nombreUser"> ${horario.nombreUsuario}</span>
                        <br/>                        
                        <div class="titulacion">Ingeniería Informática
                        
                        <span class="fecha">
                        <fmt:formatDate pattern="EEEE, dd 'de' MMMM" value="${horario.currentHora}"/>
                        </span>
                        </div>
                             
                        </th>
                          
                   </tr>
            </table>
            
            <div class="tabla">
              
              
              
              <c:if test="${horario.examen=='true'}">
                  <div class="examen">Exámenes</div>
              </c:if>
              <c:if test="${horario.examen=='false'}">
                  <div class="examen">Horario de clases</div>
              </c:if>
            <table class="horario">
                <thead>
                    <tr class="tr">
                        <th class="palabras">   Hora</th>
                        <th  class="palabras">   Asignatura</th>  
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
                                <span class="hora">${celda.hora}</span></td>
                           
                            <td class='${celda.modificacion}'>                                                            
                                <span class="asignatura">${celda.nombreAsignatura}</span>
                                <span class="canceladaRecuperacion"><br/>${celda.modificacion}</span>
                            </td>
                            <c:if test="${horario.alumno=='true'}">
                                <td class='${celda.modificacion}'>  
                                    <span class="nombreProf"><b>${celda.profesor.nombre}  ${celda.profesor.apellidos}</b></span>
                                    <span class="emailProf"><br> ${celda.profesor.email}</span>
                                </td>
                            </c:if>
                            <td class='${celda.modificacion}'>
                                ${celda.curso}
                            </td>
                            <td class='${celda.modificacion}'>
                                <c:if test="${horario.alumno=='false'}">
                                    
                                    <div class="tabulador">
                                        <br>
                                    </div>
                                </c:if>
                                    ${celda.infoAula.nombreAula}  <br> ${celda.infoAula.edificio}
                                <c:if test="${horario.alumno=='false'}">
                                    
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            </div>
            
                <img  class="escudo" src="escudo.png"/>
            
        </div>
    </body>
    
</html>

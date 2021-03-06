/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upsa.better.ear.jaxrs;

import es.upsa.better.ear.beans.CeldaHorario;
import es.upsa.better.ear.beans.Horario;
import es.upsa.better.ear.beans.Profesor;
import es.upsa.better.ear.beans.Usuario;
import es.upsa.better.ear.cdi.Logic;
import es.upsa.better.ear.exception.GeneralException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

@Path("horario")
@RequestScoped
public class HorariosResource
{
    @EJB
    private Logic logic;
    
    @Context
    private HttpServletRequest request;
    
    @Context 
    private HttpServletResponse response;
    
    @Context
    private UriInfo uriInfo;
    
    @GET
    @Produces(MediaType.TEXT_HTML)
    public void getHtml() throws ServletException, IOException 
    {
        request.getRequestDispatcher("/saludo.jsp").forward(request, response);
    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.TEXT_HTML)
    public Horario getHorario(@PathParam("id") String id) throws GeneralException 
    {
        Usuario usuario = logic.findUsuario(id);
        if(usuario instanceof Profesor)
        {
            Horario horario = logic.findHorarioProf(usuario);
            return horario;
        
        }else{
            Horario horario = logic.findHorario(usuario);
            return horario;
        }              
    }

}

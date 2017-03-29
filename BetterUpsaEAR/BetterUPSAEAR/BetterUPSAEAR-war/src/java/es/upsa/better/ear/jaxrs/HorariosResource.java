/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upsa.better.ear.jaxrs;

import es.upsa.better.ear.beans.CeldaHorario;
import es.upsa.better.ear.beans.Horario;
import es.upsa.better.ear.beans.Usuario;
import es.upsa.better.ear.cdi.Logic;
import es.upsa.better.ear.exception.GeneralException;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
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

@RequestScoped
@Path("horarios")
public class HorariosResource
{
    @EJB
    private Logic logic;
    
    @Context
    private HttpServletRequest request;
    
    @Context 
    private HttpServletResponse response;
    
    @GET
    @Path("{id}")
    @Produces(MediaType.TEXT_HTML)
    public Collection<CeldaHorario> getHorario(@PathParam("id") String id) throws GeneralException 
    {
        Usuario usuario = logic.findUsuario(id);
        Collection<CeldaHorario> horario = logic.findHorario(usuario);
        return horario;
    }

}

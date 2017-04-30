/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upsa.better.ear.providers;

import es.upsa.better.ear.beans.Horario;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Estela
 */
@Provider
@Produces(MediaType.TEXT_HTML)
public class Horario2HTML implements MessageBodyWriter<Horario>
{
    @Context
    private HttpServletRequest request;
    
    @Context
    private HttpServletResponse response;   
    
    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) 
    {
        return (type==Horario.class)&& (mediaType.equals(MediaType.TEXT_HTML_TYPE));
    }

    @Override
    public long getSize(Horario t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) 
    {
        return -1;
    }

    @Override
    public void writeTo(Horario t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException 
    {
        try
        {
            request.setAttribute("horario", t);
            request.getRequestDispatcher("/tablaHorario.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(Horario2HTML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upsa.better.ear.ejbs;

import es.upsa.better.ear.beans.CeldaHorario;
import es.upsa.better.ear.beans.Usuario;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author cxb0105
 */
public class DAOBean implements DAO
{
    @Override
    public void selectHorario(Usuario usuario, Date currentFecha) 
    {
        Collection<CeldaHorario> clases = new ArrayList();
        
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upsa.better.ear.ejbs;

import es.upsa.better.ear.beans.Usuario;
import java.sql.Date;

/**
 *
 * @author cxb0105
 */
public interface DAO 
{
    public void selectHorario(Usuario usuario, Date currentFecha);
}

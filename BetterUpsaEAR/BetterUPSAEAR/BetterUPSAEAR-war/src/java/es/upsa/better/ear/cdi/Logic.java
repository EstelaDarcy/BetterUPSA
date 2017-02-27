/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upsa.better.ear.cdi;

import es.upsa.better.ear.beans.Usuario;
import es.upsa.better.ear.beans.CeldaHorario;
import java.sql.Date;
import java.util.Collection;

/**
 *
 * @author cxb0105
 */
public interface Logic 
{
    public Usuario selectUsuario();
    public Collection<CeldaHorario> selectHorario(Date fecha, Usuario usuario);
    public void updateHorario();
}

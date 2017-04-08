/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upsa.better.ear.cdi;

import es.upsa.better.ear.beans.Usuario;
import es.upsa.better.ear.beans.Horario;
import es.upsa.better.ear.exception.GeneralException;

/**
 *
 * @author cxb0105
 */
public interface Logic 
{
    public Usuario findUsuario(String id);
    public Horario findHorario(Usuario usuario) throws GeneralException;
    public void updateHorario();
}

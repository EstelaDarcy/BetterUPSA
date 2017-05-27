/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upsa.better.ear.cdi;

import es.upsa.better.ear.beans.Usuario;
import es.upsa.better.ear.beans.Horario;
import es.upsa.better.ear.exception.GeneralException;

public interface Logic 
{
    public Usuario findUsuario(String id) throws GeneralException;
    public Horario findHorario(Usuario usuario) throws GeneralException;
    public Horario findHorarioProf(Usuario usuario) throws GeneralException;
    public void updateHorario();
}

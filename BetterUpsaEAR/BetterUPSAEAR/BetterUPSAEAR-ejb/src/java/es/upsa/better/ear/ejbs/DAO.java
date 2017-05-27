/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upsa.better.ear.ejbs;

import es.upsa.better.ear.beans.CeldaHorario;
import es.upsa.better.ear.beans.Horario;
import es.upsa.better.ear.beans.Usuario;
import es.upsa.better.ear.exception.GeneralException;
import java.sql.Date;
import java.util.Collection;

public interface DAO 
{
    public Horario selectHorario(Usuario usuario) throws GeneralException;
    public Usuario selectUsuario(String id) throws GeneralException;
    public Horario selectHorarioProf(Usuario usuario) throws GeneralException;
}

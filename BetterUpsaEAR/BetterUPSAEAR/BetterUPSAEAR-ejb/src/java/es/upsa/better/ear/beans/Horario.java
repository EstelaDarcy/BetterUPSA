/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upsa.better.ear.beans;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Estela
 */
public class Horario 
{
    Collection<CeldaHorario> horario = new ArrayList();

    public Collection<CeldaHorario> getHorario() {
        return horario;
    }

    public void setHorario(Collection<CeldaHorario> horario) {
        this.horario = horario;
    }    
}

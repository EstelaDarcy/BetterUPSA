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
    ArrayList<CeldaHorario> horario = new ArrayList();

    public ArrayList<CeldaHorario> getHorario() {
        return horario;
    }

    public void setHorario(ArrayList<CeldaHorario> horario) {
        this.horario = horario;
    }
    
    public void addCeldas(ArrayList<CeldaHorario> clases)
    {
        //horario.addAll(clases);
        for(CeldaHorario clase : clases)
        {
            horario.add(clase);
        }
    }
    
    public void addVacio()
    {
        CeldaHorario vacio = new CeldaHorario();
        
        vacio.setNombreAsignatura("NO IENES CLASE HOY ");
        
        horario.add(vacio);        
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upsa.better.ear.beans;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Estela
 */
public class Horario 
{
    ArrayList<CeldaHorario> horario = new ArrayList();
    private String nombreUsuario;
    private boolean examen;
    private boolean alumno;
    private Date currentHora;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCurrentHora() {
        return currentHora;
    }

    public void setCurrentHora(Date currentHora) {
        this.currentHora = currentHora;
    }

    public boolean isAlumno() {
        return alumno;
    }

    public void setAlumno(boolean alumno) {
        this.alumno = alumno;
    }

    public boolean isExamen() {
        return examen;
    }

    public void setExamen(boolean examen) {
        this.examen = examen;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

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
        
        vacio.setNombreAsignatura("NO TIENES CLASE HOY ");
        
        horario.add(vacio);        
    }
    
    public void addExamenVacio()
    {
        CeldaHorario vacio = new CeldaHorario();
        
        vacio.setNombreAsignatura("NO TIENES NINGÚN EXAMEN HOY ");
        
        horario.add(vacio);        
    }
}

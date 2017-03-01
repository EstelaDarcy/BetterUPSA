/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upsa.better.ear.beans;

import java.util.HashMap;

/**
 *
 * @author cxb0105
 */
public class CeldaHorario 
{
    private String idAsig;
    private String nombreAsignatura;
    private double hora;
    private String modificacion;
    private HashMap<String, Profesor> nombreProf;
    private String nombreAula;
    private String tipo;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public HashMap<String, Profesor> getNombreProf() {
        return nombreProf;
    }

    public void setNombreProf(HashMap<String, Profesor> nombreProf) {
        this.nombreProf = nombreProf;
    }
    private String edificio;
    private boolean examen;

    public boolean isExamen() {
        return examen;
    }

    public void setExamen(boolean examen) {
        this.examen = examen;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }

    public double getHora() {
        return hora;
    }

    public void setHora(double hora) {
        this.hora = hora;
    }

    public String getModificacion() {
        return modificacion;
    }

    public String getIdAsig() {
        return idAsig;
    }

    public void setIdAsig(String idAsig) {
        this.idAsig = idAsig;
    }
    
    public void setModificacion(String modificacion) {
        this.modificacion = modificacion;
    }

    public String getNombreAula() {
        return nombreAula;
    }

    public void setNombreAula(String nombreAula) {
        this.nombreAula = nombreAula;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }    
}

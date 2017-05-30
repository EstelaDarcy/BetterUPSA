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
    private String nombreAsignatura;
    private String hora;
    private String modificacion;
    private Profesor profesor;    
    private String tipoAsig;    
    private Aula infoAula;
    private String curso;

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public Aula getInfoAula() {
        return infoAula;
    }

    public void setInfoAula(Aula infoAula) {
        this.infoAula = infoAula;
    }
        
    public String getTipoAsig() {
        return tipoAsig;
    }

    public void setTipoAsig(String tipoAsig) {
        this.tipoAsig = tipoAsig;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }
    
    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getModificacion() {
        return modificacion;
    }
    
    public void setModificacion(String modificacion) {
        this.modificacion = modificacion;
    }    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upsa.better.ear.beans;

/**
 *
 * @author Estela
 */
public class AsigProf 
{
    private String idAsig;
    private String nombreAsig;
    private String teoria;
    private String curso;

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public AsigProf() {
    }

    public AsigProf(String idAsig, String nombreAsig, String teoria, String curso) {
        this.idAsig = idAsig;
        this.nombreAsig = nombreAsig;
        this.teoria = teoria;
        this.curso = curso;
    }

    public String getIdAsig() {
        return idAsig;
    }

    public void setIdAsig(String idAsig) {
        this.idAsig = idAsig;
    }

    public String getNombreAsig() {
        return nombreAsig;
    }

    public void setNombreAsig(String nombreAsig) {
        this.nombreAsig = nombreAsig;
    }

    public String getTeoria() {
        return teoria;
    }

    public void setTeoria(String teoria) {
        this.teoria = teoria;
    }
    
}

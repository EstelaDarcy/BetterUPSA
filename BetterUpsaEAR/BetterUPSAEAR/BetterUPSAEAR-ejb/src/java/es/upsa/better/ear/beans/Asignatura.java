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
public class Asignatura 
{
    private String idAsig;
    private String nombreAsig;

    public Asignatura(String idAsig, String nombreAsig) {
        this.idAsig = idAsig;
        this.nombreAsig = nombreAsig;
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
    
}

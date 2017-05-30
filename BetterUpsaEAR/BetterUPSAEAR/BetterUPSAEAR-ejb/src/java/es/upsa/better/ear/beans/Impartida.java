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
public class Impartida 
{
    private String teoria;
    private String idAsig;

    public Impartida() {
    }

    public Impartida(String teoria, String idAsig) {
        this.teoria = teoria;
        this.idAsig = idAsig;
    }

    public String getTeoria() {
        return teoria;
    }

    public void setTeoria(String teoria) {
        this.teoria = teoria;
    }

    public String getIdAsig() {
        return idAsig;
    }

    public void setIdAsig(String idAsig) {
        this.idAsig = idAsig;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upsa.better.ear.exception;

/**
 *
 * @author Estela
 */
public class GeneralException extends Exception
{
    public GeneralException() {
    }

    public GeneralException(String string) {
        super(string);
    }

    public GeneralException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public GeneralException(Throwable thrwbl) {
        super(thrwbl);
    }

    public GeneralException(String string, Throwable thrwbl, boolean bln, boolean bln1) {
        super(string, thrwbl, bln, bln1);
    }    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upsa.better.ear.ejbs;

import es.upsa.better.ear.beans.CeldaHorario;
import es.upsa.better.ear.beans.Usuario;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 *
 * @author cxb0105
 */

@Resource(name = "jdbc/database", type = DataSource.class, lookup = "jdbc/horario")

public class DAOBean implements DAO
{
    @Resource(name="jdbc/database")
    private DataSource dataSource;
    
    @Override
    public void selectHorario(Usuario usuario, Date currentFecha) 
    {
        Collection<CeldaHorario> clases = new ArrayList();
        String dia = getDayOfTheWeek(currentFecha);
        
        try(Connection connection = dataSource.getConnection();
            PreparedStatement psSelect = connection.prepareStatement(""))
        {
            
        } catch (SQLException ex) 
        {
            Logger.getLogger(DAOBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Para obtener el dia de a semana cogiendo la fecha del día, principalmente para la tabla de horarios
    public String getDayOfTheWeek(Date d)
    {
        String diaSemana;
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(d);
        int dia = cal.get(Calendar.DAY_OF_WEEK);
        
        switch(dia)
        {
            case 1:
                diaSemana = "domingo";
            case 2:
                diaSemana = "lunes";
            case 3:
                diaSemana = "martes";
            case 4:
                diaSemana = "miercoles";
            case 5:
                diaSemana = "jueves";
            case 6:
                diaSemana = "viernes";
            case 7:
                diaSemana = "sabado";
            default:
                diaSemana = "error";
        }
        return diaSemana;
    }
}

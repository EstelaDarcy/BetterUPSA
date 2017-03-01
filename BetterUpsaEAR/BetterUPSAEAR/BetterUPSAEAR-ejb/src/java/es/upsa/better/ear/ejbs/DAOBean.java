/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upsa.better.ear.ejbs;

import es.upsa.better.ear.beans.Asignatura;
import es.upsa.better.ear.beans.CeldaHorario;
import es.upsa.better.ear.beans.Usuario;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
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
        ArrayList<String> asignaturas = new ArrayList();
        HashMap<String, Asignatura> asigSemestre = new HashMap();
        String idSemetre;
        
        try(Connection connection = dataSource.getConnection();
            Statement stSelect = connection.createStatement();         
            /*para obtener en que semestre estamos*/
            ResultSet rs1 = stSelect.executeQuery("SELECT IDSEMESTRE, FECHAINICLASE, FECHAFINCLASE, FECHAINIEXAM, FECHAFINEXAM "
                                                 + " FROM SEMESTRES");
            /*obtengo las asignaturas en las que se matriculo el alumno*/    
            PreparedStatement psSelect = connection.prepareStatement("SELECT IDASIG "
                                                                    + " FROM MATRICULAS "
                                                                   + " WHERE EXPEDIENTE = ?");
            /*selecciono asignaturas matriculadas en ese semestre*/    
            PreparedStatement psSeAsig = connection.prepareStatement("SELECT NOMBREASIGNATURA, IDASIG"
                                                                  + "   FROM ASIGNATURAS"
                                                                  + "  WHERE IDASIG=? AND IDSEMESTRE=?")  
            )
                                                 
        {           
            psSelect.setString(1, usuario.getIdentificador());
            //OBTENGO LAS ASIGNATURAS EN LAS QUE ESTA MATRICULADO EL ALUMNO
            try(ResultSet rs2 = psSelect.executeQuery())
            {
                if ( rs2.next() )
                {                
                    do
                    {
                        asignaturas.add(rs2.getString(1));
                    }while(rs2.next());
                }
            }
                        
            if ( rs1.next() )
            {                
                do
                {
                    if(currentFecha.compareTo(rs1.getDate(2))>=0 && currentFecha.compareTo(rs1.getDate(5))<=0)
                    {
                        idSemetre = rs1.getString(1);
                                            
                        //recorro asignaturas para saber cuales son las de este semestre
                        for (String asignatura : asignaturas) 
                        {
                            psSeAsig.clearParameters();
                            psSeAsig.setString(1, asignatura);
                            psSeAsig.setString(2, idSemetre);
                            
                            try(ResultSet rsAsig = psSeAsig.executeQuery())
                            {
                                if(rsAsig.next())
                                {
                                    asigSemestre.put(rsAsig.getString(2), new Asignatura(rsAsig.getString(2), rsAsig.getString(1)));
                                }//ya tengo todas las asignaturas de este cuatrimestre
                            }
                        }
                        
                        //Compruebo si debo buscar en el horario
                        if(currentFecha.compareTo(rs1.getDate(2))>=0 && currentFecha.compareTo(rs1.getDate(3))<=0)
                        {/* x>0 despues, x=0 eq, x<0 antes*/
                        }
                        //O BUSCAR EN EXAMENES
                        if(currentFecha.compareTo(rs1.getDate(4))>=0 && currentFecha.compareTo(rs1.getDate(5))<=0)
                        {      //me kede aki                  
//                            while(asigSemestre.)
//                            {
//                                
//                            }
                        }
                    }
                }while(rs1.next());
            }
        } catch (SQLException sqlException) 
        {
            Logger.getLogger(DAOBean.class.getName()).log(Level.SEVERE, null, sqlException);
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
        {/*1 -> D, 2 -> L, 3 -> M, 4 -> X, 5 -> J, 6 -> V, 7 -> S*/
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upsa.better.ear.ejbs;


import es.upsa.better.ear.beans.Asignatura;
import es.upsa.better.ear.beans.Aula;
import es.upsa.better.ear.beans.CeldaHorario;
import es.upsa.better.ear.beans.Horario;
import es.upsa.better.ear.beans.Profesor;
import es.upsa.better.ear.beans.Usuario;
import es.upsa.better.ear.exception.GeneralException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.sql.DataSource;

/**
 *
 * @author cxb0105
 */

@Resource(name = "jdbc/database", type = DataSource.class, lookup = "jdbc/horario")

@Stateless
@Local(DAO.class)
public class DAOBean implements DAO
{
    @Resource(name="jdbc/database")
    private DataSource dataSource;
    
    @Override
    public Collection<CeldaHorario> selectHorario(Usuario usuario) throws GeneralException
    {
        LocalDate ahora = LocalDate.now();
        Date currentFecha = java.sql.Date.valueOf(ahora);
        Collection<CeldaHorario> clases = new ArrayList();
        String dia = getDayOfTheWeek(currentFecha);
        ArrayList<String> asignaturas = new ArrayList();
        ArrayList<Asignatura> asigSemestre = new ArrayList();
        String idSemetre="";
        boolean examenes=false;
        String idAula;
        String idAsig;
        String diaSemana;
        String hora;
       
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
                                                                  + "  WHERE IDASIG=? AND IDSEMESTRE=?");                
       /*----------------------------------------------------------------------------------------------------------------*/         
            /*obtengo datos de la celda del horario del examen*/
            PreparedStatement psSExam = connection.prepareStatement("SELECT IDAULA, HORAEXAM, TEORIA "
                                                                  + "  FROM EXAMENES "
                                                                  + " WHERE IDASIG=? AND FECHAEXAM=?");                 
            /*selecciono las clases del dia*/
            PreparedStatement psHorario = connection.prepareStatement("SELECT HORA, TEORIA, IDAULA "
                                                                    + "  FROM HORARIOS "
                                                                    + " WHERE LOWER(DIA)=? AND IDASIG=?");            
            /*Compruebo si hay cambios en esa hora NO LA UTILIZO*/
            PreparedStatement psCambioH = connection.prepareStatement("SELECT HORANUEVA, TIPO, IDAULA "
                                                                    + "  FROM CAMBIOSHORA "
                                                                    + " WHERE FECHANUEVA=? AND IDASIG=?");
            
            /*Selecciono las horas añadidas*/
            PreparedStatement psHCancel = connection.prepareStatement("SELECT TEORIA "
                                                                    + "    FROM CAMBIOSHORA "
                                                                    + "   WHERE LOWER(TIPO)='cancelada' AND HORANUEVA=? AND FECHANUEVA=? AND IDASIG=?");
                
            PreparedStatement psHAnnadida = connection.prepareStatement("SELECT HORANUEVA, TEORIA, IDAULA "
                                                                      + "  FROM CAMBIOSHORA "
                                                                      + " WHERE IDASIG=? AND FECHANUEVA=? AND LOWER(TIPO)='recuperada'");
            )
                                                 
        {           
            diaSemana = getDayOfTheWeek(currentFecha);
            
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
             
            /*obtengo los datos de este semestre*/
            while ( rs1.next() )
            {                
                //Compruebo en que semestre estamos
                if(currentFecha.compareTo(rs1.getDate(2))>=0 && currentFecha.compareTo(rs1.getDate(5))<=0)
                {
                    idSemetre = rs1.getString(1);

                    //Compruebo si debo buscar en examenes
                    if(currentFecha.compareTo(rs1.getDate(4))>=0 && currentFecha.compareTo(rs1.getDate(5))<=0)
                    {/* x>0 despues, x=0 eq, x<0 antes*/
                        examenes = true;
                    }                        
                }                
            }
            
            //recorro asignaturas matriculaadas, para saber cuales son las de este semestre 
            for (String asignatura : asignaturas) 
            {
                psSeAsig.clearParameters();
                psSeAsig.setString(1, asignatura);
                psSeAsig.setString(2, idSemetre);

                try(ResultSet rsAsig = psSeAsig.executeQuery())
                {
                    if(rsAsig.next())
                    {/*obtengo las asignaturas en las que esta matriculado este semestre*/
                        asigSemestre.add(new Asignatura(rsAsig.getString(2), rsAsig.getString(1)));
                    }//ya tengo todas las asignaturas de este cuatrimestre
                }
            }
    /*-----------------------------------------------------------------------------------------------------------------*/            
            
                /*OBTENGO LOS DATOS DE LAS CELDAS DEL DIA*/
                for(Asignatura asig :asigSemestre )
                {                   
                    idAsig = asig.getIdAsig();
                                        
                    /*EXAMENES*/
                    if(examenes==true)
                    {
                        CeldaHorario celda = new CeldaHorario();
                        psSExam.clearParameters();
                        psSExam.setString(1, asig.getIdAsig());
                        psSExam.setString(2, dia);

                        try(ResultSet rsDia = psSExam.executeQuery())
                        {
                            if(rsDia.next())
                            {
                                idAula = rsDia.getString(1);
                                celda.setNombreAsignatura(asig.getNombreAsig());
                                celda.setHora(rsDia.getString(2));
                                celda.setTipoAsig(rsDia.getString(rsDia.getString(3)));
                                celda.setModificacion("");
                                
                                //Hay que sacar los datos del aula                    
                                Aula aula = getInfoAula(idAula, connection);
                                celda.setInfoAula(aula);

                                //obtener id prof con id asignatura
                                Profesor profesor = getInfoProf(idAsig, connection, currentFecha, celda.getTipoAsig().toLowerCase());
                                celda.setProfesor(profesor);
                                
                                clases.add(celda);
                            }
                        }
                    }else{/*ES HORARIO NORMAL*/      
                        /*pasar a minusculas toLowerCase(); java*/
                        /*pasar a minusculas lower()        SQL*/
                                                
                        psHorario.clearParameters();
                        psHorario.setString(1, diaSemana);
                        psHorario.setString(2, asig.getIdAsig());
                        
                        try(ResultSet rsHorario = psHorario.executeQuery())
                        {
                            if(rsHorario.next())
                            {//PUEDE HABER VARIAS UNA ASIGNTURA EN VARIAS HORAS EL MISMO DIA
                                do
                                {   
                                    CeldaHorario celda = new CeldaHorario();
                                    celda.setNombreAsignatura(asig.getNombreAsig());
                                    celda.setHora(rsHorario.getString(1));
                                    celda.setTipoAsig(rsHorario.getString(2));
                                    idAula=rsHorario.getString(3);
                                    hora = rsHorario.getString(1);
                                    
                                    /*ESA HORA HA SIDO CANCELADA???*/
                                    psHCancel.setString(1, hora);
                                    psHCancel.setString(3, idAsig);
                                    psHCancel.setDate(2, currentFecha);
                                    
                                    try(ResultSet rsCancel = psHCancel.executeQuery())
                                    {
                                        if(rsCancel.next())
                                        {
                                            celda.setModificacion("cancelada");
                                        }
                                        else
                                        {
                                            celda.setModificacion("");
                                        }
                                    }
                                            
                                    //Hay que sacar los datos del aula                    
                                    Aula aula = getInfoAula(idAula, connection);
                                    celda.setInfoAula(aula);

                                    //obtener id prof con id asignatura
                                    Profesor profesor = getInfoProf(idAsig, connection, currentFecha, celda.getTipoAsig());
                                    celda.setProfesor(profesor);

                                    clases.add(celda);
                                }while(rsHorario.next());
                            }
                        }
                        
                        psHAnnadida.clearParameters();
                        psHAnnadida.setDate(1, currentFecha);
                        psHAnnadida.setString(2, asig.getIdAsig());
                        
                        //Compruebo si hay alguna asignatura que se recupere
                        try(ResultSet rsAdd = psHAnnadida.executeQuery())
                        {
                            while(rsAdd.next())
                            {//PUEDE HABER VARIAS UNA ASIGNTURA EN VARIAS HORAS EL MISMO DIA                                   
                                    CeldaHorario celda = new CeldaHorario();
                                    celda.setNombreAsignatura(asig.getNombreAsig());
                                    celda.setHora(rsAdd.getString(1));
                                    celda.setTipoAsig(rsAdd.getString(2));
                                    idAula=rsAdd.getString(3);     
                                    celda.setModificacion("recuperada");
                                                                                                             
                                    //Hay que sacar los datos del aula                    
                                    Aula aula = getInfoAula(idAula, connection);
                                    celda.setInfoAula(aula);

                                    //obtener id prof con id asignatura
                                    Profesor profesor = getInfoProf(idAsig, connection, currentFecha, celda.getTipoAsig());
                                    celda.setProfesor(profesor);

                                    clases.add(celda);                                
                            }
                        }
                    }                             
                } 
            
        } catch (SQLException sqlException) 
        {
            throw new GeneralException(sqlException);
        }
        return clases;            
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
                break;
            case 2:
                diaSemana = "lunes";
                break;
            case 3:
                diaSemana = "martes";
                break;
            case 4:
                diaSemana = "miercoles";
                break;
            case 5:
                diaSemana = "jueves";
                break;
            case 6:
                diaSemana = "viernes";
                break;
            case 7:
                diaSemana = "sabado";
                break;
            default:
                diaSemana = "error";
                break;
        }
        return diaSemana;
    }
    
    public Aula getInfoAula(String idAula, Connection connection) throws SQLException
    {
        Aula aula = new Aula();
        
        /*obtengo los datos de aula a mostrar*/ 
        try(PreparedStatement psAula = connection.prepareStatement("SELECT NOMBREAU, EDIFICIO "
                                                                 + "  FROM AULAS "
                                                                 + " WHERE IDAULA=?")    
           )
        {
            psAula.setString(1, idAula);
            try(ResultSet rsAula = psAula.executeQuery())
            {
                if(rsAula.next())
                {
                    aula.setNombreAula(rsAula.getString(1));                            
                    aula.setEdificio(rsAula.getString(2));
                }
            }
        }
        
        return aula;
    }
    
    public Profesor getInfoProf(String idAsig, Connection connection, Date currentFecha, String teorica) throws SQLException
    {
        Profesor profesor = new Profesor();
        String idProf;
        
        try(/*obtengo los id de profesores*/
            PreparedStatement psImpartidas = connection.prepareStatement("SELECT IDPROF, lower(TEORIA), FECHAINI, FECHAFIN "
                                                                       + "  FROM IMPARTIDAS "
                                                                       + " WHERE IDASIG=?");
            
            /*obtengo los nombres de los profesores*/
            PreparedStatement psProf = connection.prepareStatement("SELECT NOMBREPROF, APELLIDOSPROF, EMAIL "
                                                                 + "  FROM PROFESORES "
                                                                 + " WHERE IDPROF=?");)
        {
            psImpartidas.clearParameters();
            psImpartidas.setString(1, idAsig);
            try(ResultSet rsImpartida = psImpartidas.executeQuery())
            {
                if(rsImpartida.next())
                {
                    do{ /*Compruebo si es teoria o practica o si es una asignatura a medias*/                                                                                                /* x>0 despues, x=0 eq, x<0 antes*/
                        if(teorica.equals(rsImpartida.getString(2)) || (currentFecha.compareTo(rsImpartida.getDate(3))>=0 && currentFecha.compareTo(rsImpartida.getDate(4))<=0))
                        {
                            idProf = rsImpartida.getString(1);

                            //obtener profesores con el id prof
                            psProf.setString(1, idProf);
                            try(ResultSet rsProf = psProf.executeQuery())
                            {
                                if(rsProf.next())
                                {                                    
                                    profesor.setNombre(rsProf.getString(1));
                                    profesor.setApellidos(rsProf.getString(2));
                                    profesor.setEmail(rsProf.getString(3));                                    
                                }                                        
                            }
                        }
                    }while(rsImpartida.next());
                }
            }
        }        
        return profesor;
    }
    
    
}

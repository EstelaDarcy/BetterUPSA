/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upsa.better.ear.ejbs;


import es.upsa.better.ear.beans.Alumno;
import es.upsa.better.ear.beans.AsigProf;
import es.upsa.better.ear.beans.Asignatura;
import es.upsa.better.ear.beans.Aula;
import es.upsa.better.ear.beans.CeldaHorario;
import es.upsa.better.ear.beans.Horario;
import es.upsa.better.ear.beans.Impartida;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.sql.DataSource;


@Resource(name = "jdbc/database", type = DataSource.class, lookup = "jdbc/horario")

@Stateless
@Local(DAO.class)
public class DAOBean implements DAO
{
    @Resource(name="jdbc/database")
    private DataSource dataSource;
    
    @Override
    public Horario selectHorario(Usuario usuario) throws GeneralException
    {
        LocalDate ahora = LocalDate.now();
        Date currentFecha = java.sql.Date.valueOf(ahora);
        ArrayList<CeldaHorario> clases = new ArrayList();        
        ArrayList<String> asignaturasMatriculadas = new ArrayList();
        ArrayList<Asignatura> asigSemMatriculadas = new ArrayList();
        String idSemetre="";
        Horario horario = new Horario();
        boolean examenes=false;
        String diaSemana;        
        
        horario.setNombreUsuario(usuario.getNombre() + " " + usuario.getApellidos());
        horario.setCurrentHora(currentFecha);
        horario.setId(usuario.getIdentificador());
        
        horario.setAlumno(true);
        diaSemana = getDayOfTheWeek(currentFecha); //obengo en qué dia de la semana estoy
        
        try(Connection connection = dataSource.getConnection();
            Statement stSelect = connection.createStatement();         
            /*para obtener en que semestre estamos*/
            ResultSet rs1 = stSelect.executeQuery("SELECT IDSEMESTRE, FECHAINICLASE, FECHAFINCLASE, FECHAINIEXAM, FECHAFINEXAM "
                                                 + " FROM SEMESTRES")                                        
        )                                      
        {                         
            /*obtengo los datos de este semestre*/
            while ( rs1.next() )
            {                
                //Compruebo en que semestre estamos
                if(currentFecha.compareTo(rs1.getDate(2))>=0 && currentFecha.compareTo(rs1.getDate(5))<=0)
                {
                  
                    idSemetre = rs1.getString(1);
                    
                    //Compruebo si debo buscar en examenes
                    if(currentFecha.compareTo(rs1.getDate(4))>=0 /*&& currentFecha.compareTo(rs1.getDate(5))<=0*/)
                    {/* x>0 despues, x=0 eq, x<0 antes*/
                        examenes = true;
                    }                        
                }                
            }
            
            asignaturasMatriculadas = getAsignaturasMatriculadas(connection, usuario.getIdentificador());
            asigSemMatriculadas = getAsigMatriculadasSemestre(connection, asignaturasMatriculadas, idSemetre);            
    /*-----------------------------------------------------------------------------------------------------------------*/            
           horario.setExamen(examenes);
            /*OBTENGO LOS DATOS DE LAS CELDAS DEL DIA*/
            /*EXAMENES*/
            if(examenes==true)
            {
                clases = getInfoExamenes(connection, asigSemMatriculadas, currentFecha);                                               
            }else{/*ES HORARIO NORMAL*/    
                clases = getInfoClases(connection, asigSemMatriculadas, currentFecha, diaSemana);                
            } 
            
        } catch (SQLException sqlException) 
        {
            throw new GeneralException(sqlException);
        }
//        //horario.setHorario(clases);
        if(!clases.isEmpty())
        {
            Collections.sort(clases, new Comparator<CeldaHorario>() 
            {
                @Override
                public int compare(CeldaHorario t, CeldaHorario t1) 
                {
                    return t.getHora().compareTo(t1.getHora());                    
                }
            });
            //horario.getHorario().addAll(clases);
            horario.addCeldas(clases);            
        }else
        {
            if(examenes==true)
            {
                horario.addExamenVacio();
            }else
            {
                horario.addVacio();
            }            
        }
        return horario;            
    }
    
    //Para obtener el dia de la semana cogiendo la fecha del día, principalmente para la tabla de horarios
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
                diaSemana = "miercoles";
                //diaSemana = "viernes";
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
    
    //Obtengo la información de un aula con el idAula
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
    
    //Obtengo la información de UN PROFESOR que imparte la clase para un alumno 
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
                        if(teorica.equals(rsImpartida.getString(2)) && (currentFecha.compareTo(rsImpartida.getDate(3))>=0 && currentFecha.compareTo(rsImpartida.getDate(4))<=0))
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
    
    //Obtengo las asignaturas en las que esta matriculado el ALUMNO
    public ArrayList<String> getAsignaturasMatriculadas (Connection connection, String id) throws SQLException
    {
        ArrayList<String> asignaturasMatriculadas = new ArrayList();
        
        try(/*obtengo las asignaturas en las que se matriculo el alumno*/    
            PreparedStatement psSelectAsigMatriculadas = connection.prepareStatement("SELECT IDASIG "
                                                                                    + " FROM MATRICULAS "
                                                                                   + " WHERE EXPEDIENTE = ?");
            )
        {
            psSelectAsigMatriculadas.setString(1, id);
            //OBTENGO LAS ASIGNATURAS EN LAS QUE ESTA MATRICULADO EL ALUMNO
            try(ResultSet rs2 = psSelectAsigMatriculadas.executeQuery())
            {
                if ( rs2.next() )
                {                
                    do
                    {
                        asignaturasMatriculadas.add(rs2.getString(1));
                    }while(rs2.next());
                }
            }
        }        
        return asignaturasMatriculadas;
    }
       
    //Obtengo las asignaturas de ese cautrimestre en las que el alumno esta matriculado
    public ArrayList<Asignatura> getAsigMatriculadasSemestre(Connection connection, ArrayList<String> asignaturasMatriculadas, String idSemestre) throws SQLException
    {
        ArrayList<Asignatura> asigSemMatriculadas = new ArrayList();
        
        try(/*selecciono asignaturas matriculadas en ese semestre*/    
            PreparedStatement psSeAsig = connection.prepareStatement("SELECT NOMBREASIGNATURA, IDASIG, CURSO"
                                                                  + "   FROM ASIGNATURAS"
                                                                  + "  WHERE IDASIG=? AND IDSEMESTRE=?");
            )
        {
            //recorro asignaturas matriculaadas, para saber cuales son las de este semestre 
            for (String asignatura : asignaturasMatriculadas) 
            {
                psSeAsig.clearParameters();
                psSeAsig.setString(1, asignatura);
                psSeAsig.setString(2, idSemestre);

                try(ResultSet rsAsig = psSeAsig.executeQuery())
                {
                    if(rsAsig.next())
                    {/*obtengo las asignaturas en las que esta matriculado este semestre*/
                        asigSemMatriculadas.add(new Asignatura(rsAsig.getString(2), rsAsig.getString(1), rsAsig.getString(3)));
                    }//ya tengo todas las asignaturas de este cuatrimestre
                }
            }
        }        
        return asigSemMatriculadas;
    }
    
    //Informacion de todos los examenes del dia
    public ArrayList<CeldaHorario> getInfoExamenes(Connection connection, ArrayList<Asignatura> asigSemMatriculadas, Date currentFecha) throws SQLException
    {
        String idAsig, idAula;
        ArrayList<CeldaHorario> clases = new ArrayList();
        
        try(/*obtengo datos de la celda del horario del examen*/
            PreparedStatement psSExam = connection.prepareStatement("SELECT IDAULA, HORAEXAM, TEORIA "
                                                                  + "  FROM EXAMENES "
                                                                  + " WHERE IDASIG=? AND FECHAEXAM=?");)
        {
            for(Asignatura asig :asigSemMatriculadas )
            {                   
                idAsig = asig.getIdAsig();  
                                
                CeldaHorario celda = new CeldaHorario();
                celda.setCurso(asig.getCurso());
                psSExam.clearParameters();
                psSExam.setString(1, idAsig);
                psSExam.setDate(2, currentFecha);

                try(ResultSet rsDia = psSExam.executeQuery())
                {
                    if(rsDia.next())
                    {
                        idAula = rsDia.getString(1);
                        celda.setNombreAsignatura(asig.getNombreAsig());
                        celda.setHora(rsDia.getString(2));
                        celda.setTipoAsig(rsDia.getString(3));
                        //celda.setModificacion("");

                        //Hay que sacar los datos del aula                    
                        Aula aula = getInfoAula(idAula, connection);
                        celda.setInfoAula(aula);

                        //obtener id prof con id asignatura
                        Profesor profesor = getInfoProf(idAsig, connection, currentFecha, celda.getTipoAsig().toLowerCase());
                        celda.setProfesor(profesor);

                        clases.add(celda);
                    }
                }
            }
        }        
        return clases;
    }
    
    //Informcion de todas las clases del dia
    public ArrayList<CeldaHorario> getInfoClases(Connection connection, ArrayList<Asignatura> asigSemMatriculadas, Date currentFecha, String diaSemana) throws SQLException
    {
        String idAsig, idAula;
        String hora;  
        ArrayList<CeldaHorario> clases = new ArrayList();
    
        try( /*selecciono las clases del dia*/
            PreparedStatement psHorario = connection.prepareStatement("SELECT HORA, TEORIA, IDAULA "
                                                                    + "  FROM HORARIOS "
                                                                    + " WHERE LOWER(DIA)=? AND IDASIG=?");
             /*Selecciono las horas añadidas*/
            PreparedStatement psHCancel = connection.prepareStatement("SELECT TEORIA "
                                                                    + "    FROM CAMBIOSHORA "
                                                                    + "   WHERE LOWER(TIPO)='cancelada' AND HORANUEVA=? AND FECHANUEVA=? AND IDASIG=?");
            /*Compruebo si hoy tiene alguna clase extra*/    
            PreparedStatement psHAnnadida = connection.prepareStatement("SELECT HORANUEVA, TEORIA, IDAULA "
                                                                      + "  FROM CAMBIOSHORA "
                                                                      + " WHERE IDASIG=? AND FECHANUEVA=? AND LOWER(TIPO)='recuperada'");               
            )
        {
            for(Asignatura asig :asigSemMatriculadas )
            {                   
                idAsig = asig.getIdAsig();
                                  
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
                            celda.setCurso(asig.getCurso());
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
                psHAnnadida.setDate(2, currentFecha);
                psHAnnadida.setString(1, asig.getIdAsig());

                //Compruebo si hay alguna asignatura que se recupere
                try(ResultSet rsAdd = psHAnnadida.executeQuery())
                {
                    while(rsAdd.next())
                    {//PUEDE HABER VARIAS UNA ASIGNTURA EN VARIAS HORAS EL MISMO DIA                                   
                            CeldaHorario celda = new CeldaHorario();
                            celda.setNombreAsignatura(asig.getNombreAsig());
                            celda.setHora(rsAdd.getString(1));
                            celda.setCurso(asig.getCurso());
                            celda.setTipoAsig(rsAdd.getString(2));
                            idAula=rsAdd.getString(3);     
                            celda.setModificacion("recuperada");

                            //Hay que sacar los datos del aula                    
                            Aula aula = getInfoAula(idAula, connection);
                            celda.setInfoAula(aula);
                            String tipo = celda.getTipoAsig();
                            //obtener id prof con id asignatura
                            Profesor profesor = getInfoProf(asig.getIdAsig(), connection, currentFecha, celda.getTipoAsig());
                            celda.setProfesor(profesor);

                            clases.add(celda);                                
                    }
                }
            }
        }
        
        return clases;
    }

    //Obtengo los datos del usuario, ya sea alumno o profesor, con el id
    @Override
    public Usuario selectUsuario(String id) throws GeneralException 
    {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement selectAlumno = connection.prepareStatement("SELECT NOMBREAL, APELLIDOSAL "
                                                                       + "  FROM ALUMNOS "
                                                                       + " WHERE EXPEDIENTE=?");
            PreparedStatement selectProfesor = connection.prepareStatement("SELECT NOMBREPROF, APELLIDOSPROF, EMAIL "
                                                                         + "  FROM PROFESORES "
                                                                         + " WHERE IDPROF=?");    )
        {
            selectAlumno.setString(1, id);
            try(ResultSet rsAlumno = selectAlumno.executeQuery())
            {
                if(rsAlumno.next())
                {
                    Alumno alumno = new Alumno();
                    alumno.setIdentificador(id);
                    alumno.setNombre(rsAlumno.getString(1));
                    alumno.setApellidos(rsAlumno.getString(2));
                    
                    return alumno;
                }
                selectProfesor.setString(1, id);
                try(ResultSet rsProfesor = selectProfesor.executeQuery())
                {
                    if(rsProfesor.next())
                    {
                        Profesor profesor = new Profesor();
                        profesor.setIdentificador(id);
                        profesor.setNombre(rsProfesor.getString(1));
                        profesor.setApellidos(rsProfesor.getString(2));
                        profesor.setEmail(rsProfesor.getString(3));
                        
                        return profesor;
                    }
                }                
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(DAOBean.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        throw new GeneralException();
    }

    @Override
    public Horario selectHorarioProf(Usuario usuario) throws GeneralException 
    {
        LocalDate ahora = LocalDate.now();
        Date currentFecha = java.sql.Date.valueOf(ahora);
        ArrayList<CeldaHorario> clases = new ArrayList();        
        ArrayList<Impartida> asignaturasImpartidas = new ArrayList();
        ArrayList<AsigProf> asigSemMatriculadas = new ArrayList();
        String idSemetre="";
        Horario horario = new Horario();
        boolean examenes=false;
        String diaSemana; 
        
        diaSemana = getDayOfTheWeek(currentFecha); //obengo en qué dia de la semana estoy
        
        try(Connection connection = dataSource.getConnection();
            Statement stSelect = connection.createStatement();         
            /*para obtener en que semestre estamos*/
            ResultSet rs1 = stSelect.executeQuery("SELECT IDSEMESTRE, FECHAINICLASE, FECHAFINCLASE, FECHAINIEXAM, FECHAFINEXAM "
                                                 + " FROM SEMESTRES")                                        
        )                                      
        {                         
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
            horario.setNombreUsuario(usuario.getNombre()+ " " + usuario.getApellidos());
            asignaturasImpartidas = getAsigImpartidas(connection, usuario.getIdentificador(), currentFecha);
            asigSemMatriculadas = getAsigProf(connection, asignaturasImpartidas, idSemetre);
            //Array de AsigProf
            horario.setAlumno(false);
            horario.setExamen(examenes);
            horario.setCurrentHora(currentFecha);
            horario.setId(usuario.getIdentificador());
            
            if(examenes==true)
            {
                clases = getInfoExamenProf(connection, asigSemMatriculadas, currentFecha);                                               
            }else{/*ES HORARIO NORMAL*/    
                clases = getInfoClasesProf(connection, asigSemMatriculadas, currentFecha, diaSemana);                
            }       
            
            clases = getTutorias(usuario.getIdentificador(), diaSemana, connection, clases);
        } catch (SQLException ex) {
            Logger.getLogger(DAOBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(!clases.isEmpty())
        {
            //horario.getHorario().addAll(clases);
            Collections.sort(clases, new Comparator<CeldaHorario>() 
            {
                @Override
                public int compare(CeldaHorario t, CeldaHorario t1) 
                {
                    return t.getHora().compareTo(t1.getHora());                    
                }
            });
            horario.addCeldas(clases);            
        }else
        {
            if(examenes==true)
            {
                horario.addExamenVacio();
            }else
            {
                horario.addVacio();
            }            
        }         
        return horario;
    }    
    
    //OBTENGO LAS ASIGNATURAS QUE IMPARTE ESE PREOFESOR
    public ArrayList<Impartida> getAsigImpartidas(Connection connection, String id, Date currentFecha) throws SQLException
    {
        ArrayList<Impartida> asignaturasImpartidas = new ArrayList();
        
        try(/*obtengo las asignaturas en las que se matriculo el alumno*/    
            PreparedStatement psSelectAsigImpartidas = connection.prepareStatement("SELECT IDASIG, TEORIA, FECHAINI, FECHAFIN "
                                                                                    + " FROM IMPARTIDAS "
                                                                                   + " WHERE IDPROF = ?");
            )
        {
            psSelectAsigImpartidas.setString(1, id);
            //OBTENGO LAS ASIGNATURAS EN LAS QUE ESTA MATRICULADO EL ALUMNO
            try(ResultSet rs2 = psSelectAsigImpartidas.executeQuery())
            {
                while ( rs2.next() )
                {                
                    if(currentFecha.compareTo(rs2.getDate(3))>=0 && currentFecha.compareTo(rs2.getDate(4))<=0)
                    {
                        Impartida impartida = new Impartida();
                        
                        impartida.setIdAsig(rs2.getString(1));
                        impartida.setTeoria(rs2.getString(2));
                        asignaturasImpartidas.add(impartida);
                    }
                }
            }
        }        
        return asignaturasImpartidas;
    }
    
    //LAS TUTORIAS DE ESA SEMANA DE ESE PROFESOR
    public ArrayList<CeldaHorario> getTutorias(String idProf, String diaSemana, Connection connection, ArrayList<CeldaHorario> clases) throws SQLException
    {
        String idT;
        String idAula;
        try( /*selecciono las tutorias del dia*/
            PreparedStatement psTutoria = connection.prepareStatement("SELECT IDT, HORAT, DESPACHO "
                                                                    + "  FROM TUTORIAS "
                                                                    + " WHERE LOWER(DIASEMANA)=? AND IDPROF=?");
            /*PreparedStatement psCambioTutoria = connection.prepareStatement("SELECT HORANUEVA, TIPO"
                                                                          + "  FROM CAMBIOST "
                                                                          + " WHERE IDT=? AND IDPROF=? AND DIANUEVO=?");
            PreparedStatement psTutoriaAnadida = connection.prepareStatement("SELECT HORANUEVA, DIANUEVO, TIPO"
                                                                           + "  FROM CAMBIOST "
                                                                           + " WHERE IDPROF=?");*/
                )
        {
            psTutoria.setString(1, diaSemana);
            psTutoria.setString(2, idProf);
            
            try(ResultSet rsTutoria = psTutoria.executeQuery())
            {
                while(rsTutoria.next())
                {
                    CeldaHorario celda = new CeldaHorario();
                    
                    idT = rsTutoria.getString(1);
                    celda.setHora(rsTutoria.getString(2));
                    idAula = rsTutoria.getString(3);
                    celda.setNombreAsignatura("Tutoría");                    
                    celda.setTipoAsig("tutoria");
                    
//                    psCambioTutoria.setString(1, idT);
//                    psCambioTutoria.setString(2, idProf);
//                    psCambioTutoria.setString(3, diaSemana);
//                    try(ResultSet rsCambioT = psCambioTutoria.executeQuery())
//                    {
//                        
//                    }
                        
                    Aula aula = getInfoAula(idAula, connection);
                    celda.setInfoAula(aula); 
                    
                    clases.add(celda);
                }
            }
        }
        return clases;                
    }
    
    //LAS ASIGNATURAS QUE TENDRA EL PROFESOR EN SU HORARIO
    public ArrayList<AsigProf> getAsigProf(Connection connection, ArrayList<Impartida> asignaturasImpartidas, String idSemestre) throws SQLException
    {
        ArrayList<AsigProf> asigSemMatriculadas = new ArrayList();
        
        try(/*selecciono asignaturas matriculadas en ese semestre*/    
            PreparedStatement psSeAsig = connection.prepareStatement("SELECT NOMBREASIGNATURA, CURSO"
                                                                  + "   FROM ASIGNATURAS"
                                                                  + "  WHERE IDASIG=? AND IDSEMESTRE=?");
            )
        {
            //recorro asignaturas matriculaadas, para saber cuales son las de este semestre 
            for (Impartida asignatura : asignaturasImpartidas) 
            {
                psSeAsig.clearParameters();
                psSeAsig.setString(1, asignatura.getIdAsig());
                psSeAsig.setString(2, idSemestre);

                try(ResultSet rsAsig = psSeAsig.executeQuery())
                {
                    if(rsAsig.next())
                    {/*obtengo las asignaturas que da el profesor este semestre*/
                        AsigProf asigProf = new AsigProf();
                        
                        asigProf.setTeoria(asignatura.getTeoria());
                        asigProf.setIdAsig(asignatura.getIdAsig());
                        asigProf.setNombreAsig(rsAsig.getString(1));
                        asigProf.setCurso(rsAsig.getString(2));
                        
                        asigSemMatriculadas.add(asigProf);
                    }//ya tengo todas las asignaturas de este cuatrimestre
                }
            }
        }        
        return asigSemMatriculadas;
    }
    
    public ArrayList<CeldaHorario> getInfoExamenProf(Connection connection, ArrayList<AsigProf> asigSemMatriculadas, Date currentFecha) throws SQLException
    {
        String idAsig, idAula;
        ArrayList<CeldaHorario> clases = new ArrayList();
        
        try(/*obtengo datos de la celda del horario del examen*/
            PreparedStatement psSExam = connection.prepareStatement("SELECT IDAULA, HORAEXAM "
                                                                  + "  FROM EXAMENES "
                                                                  + " WHERE IDASIG=? AND FECHAEXAM=? AND TEORIA=?");)
        {
            for(AsigProf asig :asigSemMatriculadas )
            {                   
                idAsig = asig.getIdAsig();  
                                
                CeldaHorario celda = new CeldaHorario();
                psSExam.clearParameters();
                psSExam.setString(1, idAsig);
                psSExam.setDate(2, currentFecha);
                psSExam.setString(3, asig.getTeoria());

                try(ResultSet rsDia = psSExam.executeQuery())
                {
                    if(rsDia.next())
                    {
                        idAula = rsDia.getString(1);
                        celda.setNombreAsignatura(asig.getNombreAsig());
                        celda.setHora(rsDia.getString(2));
                        celda.setTipoAsig(asig.getTeoria());
                        celda.setCurso(asig.getCurso());
                        celda.setModificacion("");

                        //Hay que sacar los datos del aula                    
                        Aula aula = getInfoAula(idAula, connection);
                        celda.setInfoAula(aula);

                        clases.add(celda);
                    }
                }
            }
        }         
        return clases;
    }
    
    public ArrayList<CeldaHorario> getInfoClasesProf(Connection connection, ArrayList<AsigProf> asigSemMatriculadas, Date currentFecha, String diaSemana) throws SQLException
    {         
        ArrayList<CeldaHorario> clases = new ArrayList();
        String idAsig, idAula;
        String hora; 
    
        try( /*selecciono las clases del dia*/
            PreparedStatement psHorario = connection.prepareStatement("SELECT HORA, IDAULA "
                                                                    + "  FROM HORARIOS "
                                                                    + " WHERE LOWER(DIA)=? AND IDASIG=? AND TEORIA=?");
             /*Selecciono las horas añadidas*/
            PreparedStatement psHCancel = connection.prepareStatement("SELECT IDAULA "
                                                                    + "    FROM CAMBIOSHORA "
                                                                    + "   WHERE LOWER(TIPO)='cancelada' AND HORANUEVA=? AND FECHANUEVA=? AND IDASIG=? AND TEORIA=?");
            /*Compruebo si hoy tiene alguna clase extra*/    
            PreparedStatement psHAnnadida = connection.prepareStatement("SELECT HORANUEVA, IDAULA "
                                                                      + "  FROM CAMBIOSHORA "
                                                                      + " WHERE IDASIG=? AND FECHANUEVA=? AND LOWER(TIPO)='recuperada' AND TEORIA=?");               
            )
        {
            for(AsigProf asig :asigSemMatriculadas )
            {                   
                idAsig = asig.getIdAsig();
                                                                
                psHorario.clearParameters();
                psHorario.setString(1, diaSemana);
                psHorario.setString(2, asig.getIdAsig());
                psHorario.setString(3, asig.getTeoria());

                try(ResultSet rsHorario = psHorario.executeQuery())
                {
                    if(rsHorario.next())
                    {//PUEDE HABER VARIAS UNA ASIGNTURA EN VARIAS HORAS EL MISMO DIA
                        do
                        {   
                            CeldaHorario celda = new CeldaHorario();
                            celda.setNombreAsignatura(asig.getNombreAsig());
                            celda.setHora(rsHorario.getString(1));
                            celda.setTipoAsig(asig.getTeoria());
                            idAula=rsHorario.getString(2);
                            hora = rsHorario.getString(1);

                            /*ESA HORA HA SIDO CANCELADA???*/
                            psHCancel.setString(1, hora);
                            psHCancel.setString(3, idAsig);
                            psHCancel.setDate(2, currentFecha);
                            psHCancel.setString(4, asig.getTeoria());

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
                            celda.setCurso(asig.getCurso());
                            //Hay que sacar los datos del aula                    
                            Aula aula = getInfoAula(idAula, connection);
                            celda.setInfoAula(aula);

                            clases.add(celda);
                        }while(rsHorario.next());
                    }
                }
                psHAnnadida.clearParameters();
                psHAnnadida.setString(1, asig.getIdAsig());
                psHAnnadida.setDate(2, currentFecha);
                psHAnnadida.setString(3, asig.getTeoria());
                
                //Compruebo si hay alguna asignatura que se recupere
                try(ResultSet rsAdd = psHAnnadida.executeQuery())
                {
                    while(rsAdd.next())
                    {//PUEDE HABER VARIAS UNA ASIGNTURA EN VARIAS HORAS EL MISMO DIA                                   
                            CeldaHorario celda = new CeldaHorario();
                            celda.setNombreAsignatura(asig.getNombreAsig());
                            celda.setHora(rsAdd.getString(1));
                            celda.setTipoAsig(asig.getTeoria());
                            idAula=rsAdd.getString(2);     
                            celda.setModificacion("recuperada");
                            celda.setCurso(asig.getCurso());

                            //Hay que sacar los datos del aula                    
                            Aula aula = getInfoAula(idAula, connection);
                            celda.setInfoAula(aula);
                            
                            clases.add(celda);                                
                    }
                }
            }
        }        
        return clases;
    }
}
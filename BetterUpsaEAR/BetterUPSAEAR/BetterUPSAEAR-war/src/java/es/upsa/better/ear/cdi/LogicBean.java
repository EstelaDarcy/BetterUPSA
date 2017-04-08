package es.upsa.better.ear.cdi;

import es.upsa.better.ear.beans.CeldaHorario;
import es.upsa.better.ear.beans.Horario;
import es.upsa.better.ear.beans.Usuario;
import es.upsa.better.ear.ejbs.DAO;
import es.upsa.better.ear.exception.GeneralException;
import java.sql.Date;
import java.util.Collection;
import javax.ejb.EJB;
import javax.ejb.EJBs;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

@EJBs(@EJB(name="ejb/dao", beanInterface = DAO.class, lookup = "java:app/BetterUPSAEAR-ejb/DaoBean!es.upsa.better.ear.DAO"))

@Stateless
@Local(Logic.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class LogicBean implements Logic
{
    @EJB(name="ejb/dao")
    private DAO dao;
    
    @Override
    public Usuario findUsuario(String id) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    @Named("horario")
    @RequestScoped
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Horario findHorario(Usuario usuario) throws GeneralException
    {
        return dao.selectHorario(usuario);
    }

    @Override
    public void updateHorario() 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    
}

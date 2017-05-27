package es.upsa.better.ear.cdi;

import es.upsa.better.ear.beans.Horario;
import es.upsa.better.ear.beans.Usuario;
import es.upsa.better.ear.ejbs.DAO;
import es.upsa.better.ear.exception.GeneralException;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBs;
import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

@EJB(name="ejb/dao", beanInterface = DAO.class, lookup = "java:app/BetterUPSAEAR-ejb/DAOBean!es.upsa.better.ear.ejbs.DAO")

@Stateless
@Local(Logic.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class LogicBean implements Logic
{
    @EJB(name="ejb/dao")
    private DAO dao;
    
    @Resource
    private SessionContext sessionContext;
    
    @Override
    public Usuario findUsuario(String id) throws GeneralException
    {
        return dao.selectUsuario(id);
    }

    
    //@Named("horario")
//    @RequestScoped
//    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public Horario findHorario(Usuario usuario) throws GeneralException
    {
        return dao.selectHorario(usuario);
    }

    @Override
    public void updateHorario() 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    

    @Override
    public Horario findHorarioProf(Usuario usuario) throws GeneralException 
    {
        return dao.selectHorarioProf(usuario);
    }
}

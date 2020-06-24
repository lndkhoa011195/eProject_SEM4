/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ASUS
 */
@Stateless
public class EmployeeFacade extends AbstractFacade<Employee> implements EmployeeFacadeLocal {

    @PersistenceContext(unitName = "com.mycompan_stroeHKTTAdmin_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmployeeFacade() {
        super(Employee.class);
    }
    @Override
    public int checkUserPass(String user, String pass) {
        Query q=em.createQuery("SELECT e FROM Employee e WHERE e.username = :user AND e.password = :pass");
        q.setParameter("user",user);
        q.setParameter("pass", pass);
        if (q.getResultList().size()>0){
            return 1;
        }
        return 0;
    }

    @Override
    public Employee findAccByUserPass(String user, String pass) {
        Query q=em.createQuery("SELECT e FROM Employee e WHERE e.username = :user AND e.password = :pass");
        q.setParameter("user",user);
        q.setParameter("pass", pass);
        if (q.getResultList().size()>0){
            return (Employee) q.getSingleResult();
        }
        
        return null;
    }

    @Override
    public List<Employee> findByUserPass(String user, String pass) {
        return null;
    }
    
}

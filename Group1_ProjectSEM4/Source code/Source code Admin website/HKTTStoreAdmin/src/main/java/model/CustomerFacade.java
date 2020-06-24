/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ASUS
 */
@Stateless
public class CustomerFacade extends AbstractFacade<Customer> implements CustomerFacadeLocal {

    @PersistenceContext(unitName = "com.mycompan_stroeHKTTAdmin_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomerFacade() {
        super(Customer.class);
    }

    @Override
    public int checkEmail(String email) {
        Query q=em.createQuery("SELECT c FROM Customer c WHERE c.email = :emailQ");
        q.setParameter("emailQ",email);
        return q.getResultList().size();
    }

    @Override
    public int checkPhone(String phone) {
        Query q=em.createQuery("SELECT c FROM Customer c WHERE c.phone = :phoneQ");
        q.setParameter("phoneQ",phone);
        return q.getResultList().size();
    }
    
    
    
    
}

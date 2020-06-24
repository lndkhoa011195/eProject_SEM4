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
public class ManufacturerFacade extends AbstractFacade<Manufacturer> implements ManufacturerFacadeLocal {

    @PersistenceContext(unitName = "com.mycompan_stroeHKTTAdmin_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ManufacturerFacade() {
        super(Manufacturer.class);
    }

    @Override
    public int checkNameManu(String nameManu) {
        Query q=em.createQuery("SELECT m FROM Manufacturer m WHERE m.name = :nameManu");
        q.setParameter("nameManu",nameManu);
        return q.getResultList().size();
    }

    @Override
    public int checkEmail(String email) {
        Query q=em.createQuery("SELECT m FROM Manufacturer m WHERE m.email = :nameEmail");
        q.setParameter("nameEmail",email);
        return q.getResultList().size();
    }

    @Override
    public int checkPhone(String phone) {
        Query q=em.createQuery("SELECT m FROM Manufacturer m WHERE m.phone = :namePhone");
        q.setParameter("namePhone",phone);
        return q.getResultList().size();
    }
    
    
    
    
    
    
    
}

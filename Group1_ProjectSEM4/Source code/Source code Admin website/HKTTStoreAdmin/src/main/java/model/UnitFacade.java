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
public class UnitFacade extends AbstractFacade<Unit> implements UnitFacadeLocal {

    @PersistenceContext(unitName = "com.mycompan_stroeHKTTAdmin_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UnitFacade() {
        super(Unit.class);
    }

    @Override
    public int checkNameUnit(String nameUnit) {
        Query q=em.createQuery("SELECT u FROM Unit u WHERE u.name = :nameUnit");
        q.setParameter("nameUnit", nameUnit);
        return q.getResultList().size();
    }
    
    
}

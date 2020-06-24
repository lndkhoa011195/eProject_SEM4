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
public class CartFacade extends AbstractFacade<Cart> implements CartFacadeLocal {

    @PersistenceContext(unitName = "com.mycompan_stroeHKTTAdmin_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CartFacade() {
        super(Cart.class);
    }

    @Override
    public List<Cart> getCartByCus(int id) {
        Query q=em.createQuery("SELECT c FROM Cart c WHERE c.customerId.id = :id");
        q.setParameter("id",id);
        return q.getResultList();
    }
    
    
    
}

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
public class OrderDetailFacade extends AbstractFacade<OrderDetail> implements OrderDetailFacadeLocal {

    @PersistenceContext(unitName = "com.mycompan_stroeHKTTAdmin_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrderDetailFacade() {
        super(OrderDetail.class);
    }

    @Override
    public List<OrderDetail> getOrderDetailByPro(int id) {
        Query q=em.createQuery("SELECT o FROM OrderDetail o WHERE o.productId.id = :id");
        q.setParameter("id",id);
        return q.getResultList();
    }

    @Override
    public List<OrderDetail> getOrderDetailByOrder(int id) {
        Query q=em.createQuery("SELECT o FROM OrderDetail o WHERE o.orderId.id = :id");
        q.setParameter("id",id);
        return q.getResultList();
    }
    
    
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


/**
 *
 * @author ASUS
 */
@Stateless
public class OrdersFacade extends AbstractFacade<Orders> implements OrdersFacadeLocal {

    @PersistenceContext(unitName = "com.mycompan_stroeHKTTAdmin_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrdersFacade() {
        super(Orders.class);
    }

    @Override
    public List<Orders> getOrderByCus(int id) {
        Query q = em.createQuery("SELECT o FROM Orders o WHERE o.customerId.id = :id");
        q.setParameter("id", id);
        return q.getResultList();
    }

    @Override
    public List<Orders> getOrderByDate(String from, String to) {
        Query q = em.createQuery("SELECT o FROM Orders o WHERE o.orderDate >= :from AND o.orderDate < :to AND o.status > :keyStatus");
//        Date date1 = null;
//        Date date2 = null;
//        try {
//            date1 = DateFormat.getDateInstance().parse("2020/1/1");
//        } catch (ParseException ex) {
//            Logger.getLogger(OrdersFacade.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        try {
//            date2 = DateFormat.getDateInstance().parse(to);
//        } catch (ParseException ex) {
//            Logger.getLogger(OrdersFacade.class.getName()).log(Level.SEVERE, null, ex);
//        }

        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = null, date2 = null;
        try {
            date1 = formatter1.parse(from);
        } catch (ParseException ex) {
            Logger.getLogger(OrdersFacade.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            date2 = formatter2.parse(to);
        } catch (ParseException ex) {
            Logger.getLogger(OrdersFacade.class.getName()).log(Level.SEVERE, null, ex);
        }

        q.setParameter("from", date1);
        q.setParameter("to", date2);
        q.setParameter("keyStatus",0);
        return q.getResultList();
    }

    @Override
    public List<Orders> getOrderBySelect() {
        Query q = em.createQuery("SELECT o FROM Orders o ORDER BY o.id DESC");

        return q.getResultList();
    }

    @Override
    public int CountOrderDetailCurrent() {
        Query q = em.createQuery("SELECT o FROM Orders o WHERE o.orderDate > :cDate");

        Date dNow = new Date();
//        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
//        Date date1 = null;
//        try {
//            date1 = formatter1.parse(dNow);
//        } catch (ParseException ex) {
//            Logger.getLogger(OrdersFacade.class.getName()).log(Level.SEVERE, null, ex);
//        }

        
        q.setParameter("cDate", dNow);
        return q.getResultList().size();
    }

    @Override
    public int CountByStatus(int parameter) {
        Query q = em.createQuery("SELECT o FROM Orders o WHERE o.status = :status");
        q.setParameter("status",parameter);
        return q.getResultList().size();
    }
    
    

}

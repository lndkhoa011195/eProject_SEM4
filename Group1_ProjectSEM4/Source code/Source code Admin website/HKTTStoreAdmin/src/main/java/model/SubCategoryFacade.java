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
public class SubCategoryFacade extends AbstractFacade<SubCategory> implements SubCategoryFacadeLocal {

    @PersistenceContext(unitName = "com.mycompan_stroeHKTTAdmin_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SubCategoryFacade() {
        super(SubCategory.class);
    }
    @Override
    public List<SubCategory> findByCateID(String id) {
        Query q=em.createQuery("SELECT s FROM SubCategory s WHERE s.categoryId.id = :id");
        q.setParameter("id",Integer.parseInt(id));
        
        return q.getResultList();
    }

    @Override
    public int checknameSub(String nameSub) {
        Query q=em.createQuery("SELECT s FROM SubCategory s WHERE s.name = :nameSub");
        q.setParameter("nameSub",nameSub);
        
        return q.getResultList().size();
        
    }
    
    
    
}

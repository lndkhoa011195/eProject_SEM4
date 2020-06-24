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
public class ProductFacade extends AbstractFacade<Product> implements ProductFacadeLocal {

    @PersistenceContext(unitName = "com.mycompan_stroeHKTTAdmin_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductFacade() {
        super(Product.class);
    }

    @Override
    public List<Product> getProductByManu(int id) {
        Query q=em.createQuery("SELECT p FROM Product p WHERE p.manufacturerId.id = :id");
        q.setParameter("id", id);
        return q.getResultList();
    }

    @Override
    public List<Product> getProductByUnit(int id) {
        Query q=em.createQuery("SELECT p FROM Product p WHERE p.unitId.id = :id");
        q.setParameter("id", id);
        return q.getResultList();
    
    }

    @Override
    public List<Product> getProductBySubCate(int id) {
        Query q=em.createQuery("SELECT p FROM Product p WHERE p.subCategoryId.id = :id");
        q.setParameter("id", id);
        return q.getResultList();
    }

    @Override
    public List<Product> getProductByCate(int id) {
        Query q=em.createQuery("SELECT p FROM Product p WHERE p.categoryId.id = :id");
        q.setParameter("id", id);
        return q.getResultList();
    }

    @Override
    public int checkNameProduct(String nameProduct) {
        Query q=em.createQuery("SELECT p FROM Product p WHERE p.name = :nPro");
        q.setParameter("nPro",nameProduct.trim());
        return q.getResultList().size();
        
    }
    
    
    
    
    
    
    
    
    
}

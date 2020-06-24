/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ASUS
 */
@Local
public interface ProductFacadeLocal {

    void create(Product product);

    void edit(Product product);

    void remove(Product product);

    Product find(Object id);

    List<Product> findAll();

    List<Product> findRange(int[] range);

    int count();

    List<Product> getProductByManu(int id);

    List<Product> getProductByUnit(int id);

    List<Product> getProductBySubCate(int id);

    List<Product> getProductByCate(int id);

    int checkNameProduct(String nameProduct);
    
    
}

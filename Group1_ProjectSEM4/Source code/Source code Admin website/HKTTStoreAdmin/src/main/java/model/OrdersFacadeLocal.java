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
public interface OrdersFacadeLocal {

    void create(Orders orders);

    void edit(Orders orders);

    void remove(Orders orders);

    Orders find(Object id);

    List<Orders> findAll();

    List<Orders> findRange(int[] range);

    int count();

    List<Orders> getOrderByCus(int id);

    List<Orders> getOrderByDate(String from, String to);

    List<Orders> getOrderBySelect();

    int CountOrderDetailCurrent();

    int CountByStatus(int parameter);
    
}

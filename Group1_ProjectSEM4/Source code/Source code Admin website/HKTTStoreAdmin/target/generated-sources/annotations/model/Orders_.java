package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Customer;
import model.OrderDetail;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-23T20:29:06")
@StaticMetamodel(Orders.class)
public class Orders_ { 

    public static volatile CollectionAttribute<Orders, OrderDetail> orderDetailCollection;
    public static volatile SingularAttribute<Orders, String> shipNote;
    public static volatile SingularAttribute<Orders, Customer> customerId;
    public static volatile SingularAttribute<Orders, String> orderCode;
    public static volatile SingularAttribute<Orders, Integer> id;
    public static volatile SingularAttribute<Orders, String> shipName;
    public static volatile SingularAttribute<Orders, Date> orderDate;
    public static volatile SingularAttribute<Orders, String> shipPhone;
    public static volatile SingularAttribute<Orders, String> shipAddress;
    public static volatile SingularAttribute<Orders, Integer> status;

}
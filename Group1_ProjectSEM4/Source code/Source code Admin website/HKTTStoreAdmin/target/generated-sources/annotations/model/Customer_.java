package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Cart;
import model.Orders;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-23T20:29:06")
@StaticMetamodel(Customer.class)
public class Customer_ { 

    public static volatile CollectionAttribute<Customer, Cart> cartCollection;
    public static volatile SingularAttribute<Customer, String> password;
    public static volatile SingularAttribute<Customer, String> address;
    public static volatile SingularAttribute<Customer, Integer> loginAttemptCount;
    public static volatile SingularAttribute<Customer, String> phone;
    public static volatile SingularAttribute<Customer, String> name;
    public static volatile SingularAttribute<Customer, Date> modifiedDate;
    public static volatile SingularAttribute<Customer, Integer> id;
    public static volatile CollectionAttribute<Customer, Orders> ordersCollection;
    public static volatile SingularAttribute<Customer, Integer> isActive;
    public static volatile SingularAttribute<Customer, String> email;

}
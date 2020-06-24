package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.CartDetail;
import model.Customer;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-23T20:29:06")
@StaticMetamodel(Cart.class)
public class Cart_ { 

    public static volatile CollectionAttribute<Cart, CartDetail> cartDetailCollection;
    public static volatile SingularAttribute<Cart, Customer> customerId;
    public static volatile SingularAttribute<Cart, Integer> id;

}
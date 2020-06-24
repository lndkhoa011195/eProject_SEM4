package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Cart;
import model.Product;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-23T20:29:06")
@StaticMetamodel(CartDetail.class)
public class CartDetail_ { 

    public static volatile SingularAttribute<CartDetail, Integer> quantity;
    public static volatile SingularAttribute<CartDetail, Product> productId;
    public static volatile SingularAttribute<CartDetail, Cart> cartId;
    public static volatile SingularAttribute<CartDetail, Integer> id;

}
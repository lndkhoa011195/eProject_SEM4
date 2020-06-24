package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Orders;
import model.Product;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-23T20:29:06")
@StaticMetamodel(OrderDetail.class)
public class OrderDetail_ { 

    public static volatile SingularAttribute<OrderDetail, Double> sellingPrice;
    public static volatile SingularAttribute<OrderDetail, Integer> quantity;
    public static volatile SingularAttribute<OrderDetail, Double> originalPrice;
    public static volatile SingularAttribute<OrderDetail, String> unitName;
    public static volatile SingularAttribute<OrderDetail, Product> productId;
    public static volatile SingularAttribute<OrderDetail, Orders> orderId;
    public static volatile SingularAttribute<OrderDetail, String> manufacturerName;
    public static volatile SingularAttribute<OrderDetail, String> imageURL;
    public static volatile SingularAttribute<OrderDetail, String> description;
    public static volatile SingularAttribute<OrderDetail, Integer> id;
    public static volatile SingularAttribute<OrderDetail, String> madeIn;
    public static volatile SingularAttribute<OrderDetail, String> productName;

}
package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Product;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-23T20:29:06")
@StaticMetamodel(Promotion.class)
public class Promotion_ { 

    public static volatile SingularAttribute<Promotion, Product> productId;
    public static volatile SingularAttribute<Promotion, String> imageURL;
    public static volatile SingularAttribute<Promotion, Date> modifiedDate;
    public static volatile SingularAttribute<Promotion, Integer> id;

}
package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Product;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-23T20:29:06")
@StaticMetamodel(Manufacturer.class)
public class Manufacturer_ { 

    public static volatile SingularAttribute<Manufacturer, String> address;
    public static volatile CollectionAttribute<Manufacturer, Product> productCollection;
    public static volatile SingularAttribute<Manufacturer, String> phone;
    public static volatile SingularAttribute<Manufacturer, String> name;
    public static volatile SingularAttribute<Manufacturer, Date> modifiedDate;
    public static volatile SingularAttribute<Manufacturer, Integer> id;
    public static volatile SingularAttribute<Manufacturer, String> email;

}
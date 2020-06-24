package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Product;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-23T20:29:06")
@StaticMetamodel(Unit.class)
public class Unit_ { 

    public static volatile CollectionAttribute<Unit, Product> productCollection;
    public static volatile SingularAttribute<Unit, String> name;
    public static volatile SingularAttribute<Unit, Date> modifiedDate;
    public static volatile SingularAttribute<Unit, Integer> id;

}
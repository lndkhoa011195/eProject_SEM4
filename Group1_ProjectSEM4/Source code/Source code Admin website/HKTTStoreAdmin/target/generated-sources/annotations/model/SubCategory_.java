package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Category;
import model.Product;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-23T20:29:06")
@StaticMetamodel(SubCategory.class)
public class SubCategory_ { 

    public static volatile CollectionAttribute<SubCategory, Product> productCollection;
    public static volatile SingularAttribute<SubCategory, String> name;
    public static volatile SingularAttribute<SubCategory, Date> modifiedDate;
    public static volatile SingularAttribute<SubCategory, Integer> id;
    public static volatile SingularAttribute<SubCategory, Category> categoryId;

}
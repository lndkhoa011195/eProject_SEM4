package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.CartDetail;
import model.Category;
import model.Manufacturer;
import model.OrderDetail;
import model.Promotion;
import model.SubCategory;
import model.Unit;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-23T20:29:06")
@StaticMetamodel(Product.class)
public class Product_ { 

    public static volatile SingularAttribute<Product, Double> originalPrice;
    public static volatile CollectionAttribute<Product, OrderDetail> orderDetailCollection;
    public static volatile CollectionAttribute<Product, CartDetail> cartDetailCollection;
    public static volatile SingularAttribute<Product, Manufacturer> manufacturerId;
    public static volatile SingularAttribute<Product, String> description;
    public static volatile SingularAttribute<Product, SubCategory> subCategoryId;
    public static volatile SingularAttribute<Product, Integer> isActive;
    public static volatile SingularAttribute<Product, Double> sellingPrice;
    public static volatile CollectionAttribute<Product, Promotion> promotionCollection;
    public static volatile SingularAttribute<Product, String> imageURL;
    public static volatile SingularAttribute<Product, String> name;
    public static volatile SingularAttribute<Product, Date> modifiedDate;
    public static volatile SingularAttribute<Product, Unit> unitId;
    public static volatile SingularAttribute<Product, Integer> id;
    public static volatile SingularAttribute<Product, String> madeIn;
    public static volatile SingularAttribute<Product, Category> categoryId;

}
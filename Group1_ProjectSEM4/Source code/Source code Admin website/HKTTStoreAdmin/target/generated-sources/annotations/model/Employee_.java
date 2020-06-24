package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-23T20:29:06")
@StaticMetamodel(Employee.class)
public class Employee_ { 

    public static volatile SingularAttribute<Employee, String> password;
    public static volatile SingularAttribute<Employee, String> phone;
    public static volatile SingularAttribute<Employee, String> name;
    public static volatile SingularAttribute<Employee, Date> modifiedDate;
    public static volatile SingularAttribute<Employee, Integer> id;
    public static volatile SingularAttribute<Employee, Integer> isActive;
    public static volatile SingularAttribute<Employee, String> email;
    public static volatile SingularAttribute<Employee, String> username;

}
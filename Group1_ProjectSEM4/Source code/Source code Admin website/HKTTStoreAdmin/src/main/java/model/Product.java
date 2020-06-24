/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "Product", catalog = "HKTTStoreDB", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
    @NamedQuery(name = "Product.findById", query = "SELECT p FROM Product p WHERE p.id = :id"),
    @NamedQuery(name = "Product.findByName", query = "SELECT p FROM Product p WHERE p.name = :name"),
    @NamedQuery(name = "Product.findByOriginalPrice", query = "SELECT p FROM Product p WHERE p.originalPrice = :originalPrice"),
    @NamedQuery(name = "Product.findBySellingPrice", query = "SELECT p FROM Product p WHERE p.sellingPrice = :sellingPrice"),
    @NamedQuery(name = "Product.findByDescription", query = "SELECT p FROM Product p WHERE p.description = :description"),
    @NamedQuery(name = "Product.findByMadeIn", query = "SELECT p FROM Product p WHERE p.madeIn = :madeIn"),
    @NamedQuery(name = "Product.findByImageURL", query = "SELECT p FROM Product p WHERE p.imageURL = :imageURL"),
    @NamedQuery(name = "Product.findByModifiedDate", query = "SELECT p FROM Product p WHERE p.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "Product.findByIsActive", query = "SELECT p FROM Product p WHERE p.isActive = :isActive")})
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
//    @Id
//    @Basic(optional = false)
//    @NotNull
//    @Column(name = "Id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "Name", nullable = false, length = 250)
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "OriginalPrice", nullable = false)
    private double originalPrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SellingPrice", nullable = false)
    private double sellingPrice;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "Description", nullable = false, length = 250)
    private String description;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "MadeIn", nullable = false, length = 250)
    private String madeIn;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "ImageURL", nullable = false, length = 250)
    private String imageURL;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ModifiedDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsActive", nullable = false)
    private int isActive;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Collection<OrderDetail> orderDetailCollection;
    @JoinColumn(name = "CategoryId", referencedColumnName = "Id", nullable = false)
    @ManyToOne(optional = false)
    private Category categoryId;
    @JoinColumn(name = "ManufacturerId", referencedColumnName = "Id", nullable = false)
    @ManyToOne(optional = false)
    private Manufacturer manufacturerId;
    @JoinColumn(name = "SubCategoryId", referencedColumnName = "Id", nullable = false)
    @ManyToOne(optional = false)
    private SubCategory subCategoryId;
    @JoinColumn(name = "UnitId", referencedColumnName = "Id", nullable = false)
    @ManyToOne(optional = false)
    private Unit unitId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Collection<Promotion> promotionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Collection<CartDetail> cartDetailCollection;

    public Product() {
    }

    public Product(Integer id) {
        this.id = id;
    }

    public Product(Integer id, String name, double originalPrice, double sellingPrice, String description, String madeIn, String imageURL, Date modifiedDate, int isActive) {
        this.id = id;
        this.name = name;
        this.originalPrice = originalPrice;
        this.sellingPrice = sellingPrice;
        this.description = description;
        this.madeIn = madeIn;
        this.imageURL = imageURL;
        this.modifiedDate = modifiedDate;
        this.isActive = isActive;
    }

    public Product(String name, double originalPrice, double sellingPrice, String description, String madeIn, String imageURL, Date modifiedDate, int isActive, Category categoryId, Manufacturer manufacturerId, SubCategory subCategoryId, Unit unitId) {
        this.name = name;
        this.originalPrice = originalPrice;
        this.sellingPrice = sellingPrice;
        this.description = description;
        this.madeIn = madeIn;
        this.imageURL = imageURL;
        this.modifiedDate = modifiedDate;
        this.isActive = isActive;
        this.categoryId = categoryId;
        this.manufacturerId = manufacturerId;
        this.subCategoryId = subCategoryId;
        this.unitId = unitId;
    }

    public Product(Integer id, String name, double originalPrice, double sellingPrice, String description, String madeIn, String imageURL, Date modifiedDate, int isActive, Category categoryId, Manufacturer manufacturerId, SubCategory subCategoryId, Unit unitId) {
        this.id = id;
        this.name = name;
        this.originalPrice = originalPrice;
        this.sellingPrice = sellingPrice;
        this.description = description;
        this.madeIn = madeIn;
        this.imageURL = imageURL;
        this.modifiedDate = modifiedDate;
        this.isActive = isActive;
        this.categoryId = categoryId;
        this.manufacturerId = manufacturerId;
        this.subCategoryId = subCategoryId;
        this.unitId = unitId;
    }
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMadeIn() {
        return madeIn;
    }

    public void setMadeIn(String madeIn) {
        this.madeIn = madeIn;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    @XmlTransient
    public Collection<OrderDetail> getOrderDetailCollection() {
        return orderDetailCollection;
    }

    public void setOrderDetailCollection(Collection<OrderDetail> orderDetailCollection) {
        this.orderDetailCollection = orderDetailCollection;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    public Manufacturer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Manufacturer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public SubCategory getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(SubCategory subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public Unit getUnitId() {
        return unitId;
    }

    public void setUnitId(Unit unitId) {
        this.unitId = unitId;
    }

    @XmlTransient
    public Collection<Promotion> getPromotionCollection() {
        return promotionCollection;
    }

    public void setPromotionCollection(Collection<Promotion> promotionCollection) {
        this.promotionCollection = promotionCollection;
    }

    @XmlTransient
    public Collection<CartDetail> getCartDetailCollection() {
        return cartDetailCollection;
    }

    public void setCartDetailCollection(Collection<CartDetail> cartDetailCollection) {
        this.cartDetailCollection = cartDetailCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Product[ id=" + id + " ]";
    }
    
}

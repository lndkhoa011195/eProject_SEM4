/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "OrderDetail", catalog = "HKTTStoreDB", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderDetail.findAll", query = "SELECT o FROM OrderDetail o"),
    @NamedQuery(name = "OrderDetail.findById", query = "SELECT o FROM OrderDetail o WHERE o.id = :id"),
    @NamedQuery(name = "OrderDetail.findByProductName", query = "SELECT o FROM OrderDetail o WHERE o.productName = :productName"),
    @NamedQuery(name = "OrderDetail.findByOriginalPrice", query = "SELECT o FROM OrderDetail o WHERE o.originalPrice = :originalPrice"),
    @NamedQuery(name = "OrderDetail.findBySellingPrice", query = "SELECT o FROM OrderDetail o WHERE o.sellingPrice = :sellingPrice"),
    @NamedQuery(name = "OrderDetail.findByDescription", query = "SELECT o FROM OrderDetail o WHERE o.description = :description"),
    @NamedQuery(name = "OrderDetail.findByUnitName", query = "SELECT o FROM OrderDetail o WHERE o.unitName = :unitName"),
    @NamedQuery(name = "OrderDetail.findByManufacturerName", query = "SELECT o FROM OrderDetail o WHERE o.manufacturerName = :manufacturerName"),
    @NamedQuery(name = "OrderDetail.findByMadeIn", query = "SELECT o FROM OrderDetail o WHERE o.madeIn = :madeIn"),
    @NamedQuery(name = "OrderDetail.findByImageURL", query = "SELECT o FROM OrderDetail o WHERE o.imageURL = :imageURL"),
    @NamedQuery(name = "OrderDetail.findByQuantity", query = "SELECT o FROM OrderDetail o WHERE o.quantity = :quantity")})
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "Id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "ProductName", nullable = false, length = 250)
    private String productName;
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
    @Column(name = "UnitName", nullable = false, length = 250)
    private String unitName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "ManufacturerName", nullable = false, length = 250)
    private String manufacturerName;
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
    @Column(name = "Quantity", nullable = false)
    private int quantity;
    @JoinColumn(name = "OrderId", referencedColumnName = "Id", nullable = false)
    @ManyToOne(optional = false)
    private Orders orderId;
    @JoinColumn(name = "ProductId", referencedColumnName = "Id", nullable = false)
    @ManyToOne(optional = false)
    private Product productId;

    public OrderDetail() {
    }

    public OrderDetail(Integer id) {
        this.id = id;
    }

    public OrderDetail(Integer id, String productName, double originalPrice, double sellingPrice, String description, String unitName, String manufacturerName, String madeIn, String imageURL, int quantity) {
        this.id = id;
        this.productName = productName;
        this.originalPrice = originalPrice;
        this.sellingPrice = sellingPrice;
        this.description = description;
        this.unitName = unitName;
        this.manufacturerName = manufacturerName;
        this.madeIn = madeIn;
        this.imageURL = imageURL;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Orders getOrderId() {
        return orderId;
    }

    public void setOrderId(Orders orderId) {
        this.orderId = orderId;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
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
        if (!(object instanceof OrderDetail)) {
            return false;
        }
        OrderDetail other = (OrderDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.OrderDetail[ id=" + id + " ]";
    }
    
}

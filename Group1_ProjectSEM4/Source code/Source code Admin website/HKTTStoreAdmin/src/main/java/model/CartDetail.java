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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "CartDetail", catalog = "HKTTStoreDB", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CartDetail.findAll", query = "SELECT c FROM CartDetail c"),
    @NamedQuery(name = "CartDetail.findById", query = "SELECT c FROM CartDetail c WHERE c.id = :id"),
    @NamedQuery(name = "CartDetail.findByQuantity", query = "SELECT c FROM CartDetail c WHERE c.quantity = :quantity")})
public class CartDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "Id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Quantity", nullable = false)
    private int quantity;
    @JoinColumn(name = "CartId", referencedColumnName = "Id", nullable = false)
    @ManyToOne(optional = false)
    private Cart cartId;
    @JoinColumn(name = "ProductId", referencedColumnName = "Id", nullable = false)
    @ManyToOne(optional = false)
    private Product productId;

    public CartDetail() {
    }

    public CartDetail(Integer id) {
        this.id = id;
    }

    public CartDetail(Integer id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Cart getCartId() {
        return cartId;
    }

    public void setCartId(Cart cartId) {
        this.cartId = cartId;
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
        if (!(object instanceof CartDetail)) {
            return false;
        }
        CartDetail other = (CartDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.CartDetail[ id=" + id + " ]";
    }
    
}

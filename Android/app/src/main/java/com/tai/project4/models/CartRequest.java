package com.tai.project4.models;

public class CartRequest {
    private int CustomerId;
    private int ProductId;
    private int Quantity;

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public CartRequest(int customerId, int productId, int quantity) {
        CustomerId = customerId;
        ProductId = productId;
        Quantity = quantity;
    }
}

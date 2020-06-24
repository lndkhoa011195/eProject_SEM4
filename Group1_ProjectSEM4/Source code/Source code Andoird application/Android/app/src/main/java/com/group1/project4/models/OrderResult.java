package com.group1.project4.models;

public class OrderResult {
    private int Id;
    private String OrderCode;
    private String OrderDate;
    private int Status;
    private double OriginalSum;
    private double SellingSum;
    private int ProductCount;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public void setOrderCode(String orderCode) {
        OrderCode = orderCode;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public double getOriginalSum() {
        return OriginalSum;
    }

    public void setOriginalSum(double originalSum) {
        OriginalSum = originalSum;
    }

    public double getSellingSum() {
        return SellingSum;
    }

    public void setSellingSum(double sellingSum) {
        SellingSum = sellingSum;
    }

    public int getProductCount() {
        return ProductCount;
    }

    public void setProductCount(int productCount) {
        ProductCount = productCount;
    }

    public OrderResult(int id, String orderCode, String orderDate, int status, double originalSum, double sellingSum, int productCount) {
        Id = id;
        OrderCode = orderCode;
        OrderDate = orderDate;
        Status = status;
        OriginalSum = originalSum;
        SellingSum = sellingSum;
        ProductCount = productCount;
    }
}

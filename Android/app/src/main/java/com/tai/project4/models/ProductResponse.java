package com.tai.project4.models;

public class ProductResponse {
    private int Id;
    private String Name;
    private double OriginalPrice;
    private double SellingPrice;
    private String Description;
    private String MadeIn;
    private String ManufacturerName;
    private String UnitName;
    private String ImageURL;

    public ProductResponse(int id, String name, double originalPrice, double sellingPrice, String description, String madeIn, String manufacturerName, String unitName, String imageURL) {
        Id = id;
        Name = name;
        OriginalPrice = originalPrice;
        SellingPrice = sellingPrice;
        Description = description;
        MadeIn = madeIn;
        ManufacturerName = manufacturerName;
        UnitName = unitName;
        ImageURL = imageURL;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getOriginalPrice() {
        return OriginalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        OriginalPrice = originalPrice;
    }

    public double getSellingPrice() {
        return SellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        SellingPrice = sellingPrice;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getMadeIn() {
        return MadeIn;
    }

    public void setMadeIn(String madeIn) {
        MadeIn = madeIn;
    }

    public String getManufacturerName() {
        return ManufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        ManufacturerName = manufacturerName;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }
}

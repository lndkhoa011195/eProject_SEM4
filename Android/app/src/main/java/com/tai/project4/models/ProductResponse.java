package com.tai.project4.models;

public class ProductResponse {
    private int Id;
    private String Name;
    private String ManufacturerName;
    private double Price;
    private String ImageURL;

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

    public String getManufacturerName() {
        return ManufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        ManufacturerName = manufacturerName;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public ProductResponse(int id, String name, String manufacturerName, double price, String imageURL) {
        Id = id;
        Name = name;
        ManufacturerName = manufacturerName;
        Price = price;
        ImageURL = imageURL;
    }
}

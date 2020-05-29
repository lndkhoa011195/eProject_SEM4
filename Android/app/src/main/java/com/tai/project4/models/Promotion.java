package com.tai.project4.models;

public class Promotion {
    public int Id;
    public int ProductId;
    public String ImageURL;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public Promotion(int id, int productId, String imageURL) {
        Id = id;
        ProductId = productId;
        ImageURL = imageURL;
    }
}

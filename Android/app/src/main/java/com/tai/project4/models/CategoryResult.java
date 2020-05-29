package com.tai.project4.models;

public class CategoryResult {
    public int SubCategoryID;
    public String SubCategoryName;
    public String CategoryName;

    public CategoryResult(int subCategoryID, String subCategoryName, String categoryName) {
        SubCategoryID = subCategoryID;
        SubCategoryName = subCategoryName;
        CategoryName = categoryName;
    }

    public int getSubCategoryID() {
        return SubCategoryID;
    }

    public void setSubCategoryID(int subCategoryID) {
        SubCategoryID = subCategoryID;
    }

    public String getSubCategoryName() {
        return SubCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        SubCategoryName = subCategoryName;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }
}

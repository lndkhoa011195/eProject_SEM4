package com.tai.project4.interfaces;



import com.tai.project4.model.Account;
import com.tai.project4.model.Login;
import com.tai.project4.models.CartRequest;
import com.tai.project4.models.CartResult;
import com.tai.project4.models.CategoryResult;
import com.tai.project4.models.ProductResponse;
import com.tai.project4.models.Promotion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    //Category
    @GET("/api/Category/GetCategoryAndSubCategory")
    Call<List<CategoryResult>> GetCategoryAndSubCategory();

    //Customer
    @POST("/api/Customer/Authenticate")
    Call<Account> Login(@Body Login account);

    @POST("/api/Customer/AddToCart")
    Call<String> AddToCart(@Body CartRequest cartRequest);

    @POST("/api/Customer/RemoveFromCart")
    Call<String> RemoveFromCart(@Body CartRequest cartRequest);

    @POST("/api/Customer/GetCartCount")
    Call<String> GetCartCount(@Query("_customerId") Integer _customerId);

    @POST("/api/Customer/GetProductsInCart")
    Call<List<CartResult>> GetProductsInCart(@Query("_customerId") Integer _customerId);


    //Product
    @GET("/api/Product/GetProductsByName")
    Call<List<ProductResponse>> GetProductsByName(@Query("name") String name);

    @GET("/api/Product/GetProduct")
    Call<ProductResponse> GetProduct(@Query("id") Integer id);

    @GET("/api/Product/GetRecentProducts")
    Call<List<ProductResponse>> GetRecentProducts();

    @GET("/api/Product/GetBestDealProducts")
    Call<List<ProductResponse>> GetBestDealProducts();

    @GET("/api/Product/GetPromotionProducts")
    Call<List<Promotion>> GetPromotionProducts();

    @GET("/api/Product/GetProductsBySubCategory")
    Call<List<ProductResponse>> GetProductsBySubCategory(@Query("SubCategoryId") Integer SubCategoryId);







}

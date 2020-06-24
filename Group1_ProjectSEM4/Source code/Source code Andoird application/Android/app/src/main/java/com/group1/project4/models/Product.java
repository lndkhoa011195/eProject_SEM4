package com.group1.project4.models;

import android.content.Context;
import android.content.Intent;

import com.group1.project4.ProductDetailActivity;

public class Product {
    public void startProductDetailActivity(String product_id, Context context) {
        Intent productDetailIntent = new Intent(context, ProductDetailActivity.class);
        productDetailIntent.putExtra("p_id", product_id);
        context.startActivity(productDetailIntent);
    }
}

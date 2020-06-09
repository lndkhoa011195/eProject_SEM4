package com.tai.project4;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.squareup.picasso.Picasso;
import com.tai.project4.interfaces.APIClient;
import com.tai.project4.interfaces.APIInterface;
import com.tai.project4.models.CategoryResult;
import com.tai.project4.models.ProductResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

    public static final String PREFS = "PREFS";
    TextView tvProductName, tvProductDesc, tvMRP, tvPrice, tvSaved, tvSavedPer, tvQty, addtocart, product_id, buy_now, tvDesc, tvBrand, tvUnittype;
    ImageView ivProductImage, add, remove;
    String p_id;
    SharedPreferences sp;
    ScrollView view;
    private ProgressBar mProgressBar;
    AddToCart addToCart;

    APIInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        view = findViewById(R.id.product_page);
        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        addToCart=new AddToCart(ProductDetailActivity.this);
        Bundle extras = getIntent().getExtras();
        p_id = extras.getString("p_id");

        sp = getSharedPreferences(PREFS, Context.MODE_PRIVATE);

        addtocart = findViewById(R.id.addtocart);
        product_id = findViewById(R.id.product_id);

        tvProductName = findViewById(R.id.product_name);
        //tvProductDesc = findViewById(R.id.product_desc);
        tvMRP = findViewById(R.id.mrp);
        tvPrice = findViewById(R.id.price);
        tvSaved = findViewById(R.id.saved);
        tvSavedPer = findViewById(R.id.saved_per);
        tvQty = findViewById(R.id.product_qty);
        tvDesc = findViewById(R.id.description);
        tvBrand = findViewById(R.id.brand);
        tvUnittype = findViewById(R.id.unit);
        ivProductImage = findViewById(R.id.product_img);
        add = findViewById(R.id.add);
        remove = findViewById(R.id.remove);
        buy_now = findViewById(R.id.buynow);
        tvMRP.setPaintFlags(tvMRP.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        final int[] number = {1};
        tvQty.setText("" + number[0]);

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginid = sp.getString("loginid", null);
                if (loginid != null) {
                    addToCart.addToCart(product_id.getText().toString(),tvQty.getText().toString());
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetailActivity.this);
                    builder.setTitle("Heyy..")
                            .setMessage("To add this item in your cart you have to login first. Do you want to login ")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent=new Intent(ProductDetailActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("No Just Continue ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setCancelable(false);
                    builder.show();
                }
            }
        });
        buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginid = sp.getString("loginid", null);
                if (loginid != null) {
                    addToCart.addToCart(product_id.getText().toString(),tvQty.getText().toString());
                    Intent intent = new Intent(getApplicationContext(), MyCart.class);
                    startActivity(intent);
                    finish();
                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetailActivity.this);
                    builder.setTitle("Heyy..")
                            .setMessage("To add this item in your cart you have to login first. Do you want to login ")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent=new Intent(ProductDetailActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("No Just Continue ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setCancelable(false);
                    builder.show();
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                number[0] = number[0] + 1;
                tvQty.setText("" + number[0]);

            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (number[0] == 1) {
                    tvQty.setText("" + number[0]);
                }

                if (number[0] > 1) {

                    number[0] = number[0] - 1;
                    tvQty.setText("" + number[0]);
                }
            }
        });

//        ProductDetail productDetail = new ProductDetail();
//        productDetail.execute();

        //GetProductDetails
        try {
            apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<ProductResponse> productDetailsCall = apiInterface.GetProduct(Integer.parseInt(p_id));

            productDetailsCall.enqueue(new Callback<ProductResponse>() {
                @Override
                public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                    if (!response.isSuccessful())
                        return;
                    Log.d("TAG", response.code() + "");
                    ProductResponse productDetails = response.body();
                    tvProductName.setText(productDetails.getName());
                    tvDesc.setText(productDetails.getDescription());
                    tvBrand.setText(productDetails.getManufacturerName());
                    tvUnittype.setText(productDetails.getUnitName());
                    tvMRP.setText(String.valueOf(productDetails.getOriginalPrice()));
                    tvPrice.setText(String.valueOf(productDetails.getSellingPrice()));
                    product_id.setText(String.valueOf(productDetails.getId()));
                    tvQty.setText("1");
                    Picasso.with(ProductDetailActivity.this).load(productDetails.getImageURL()).placeholder(R.drawable.watermark_icon).into(ivProductImage);

                    double p_mrp = productDetails.getOriginalPrice();
                    double p_sp = productDetails.getSellingPrice();
                    double p_dp = (p_mrp - p_sp);
                    double p_dp_p = p_dp / (p_mrp / 100);

                    int p_dp_i = (int) p_dp;
                    int p_dp_p_i = (int) p_dp_p;

                    tvSaved.setText(String.valueOf(p_dp_i));
                    tvSavedPer.setText("(" + p_dp_p_i + "%)");
                    mProgressBar.setVisibility(View.GONE);
                    view.setVisibility(View.VISIBLE);

                }

                @Override
                public void onFailure(Call<ProductResponse> call, Throwable t) {
                    call.cancel();
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetailActivity.this);
                    builder.setTitle("Received Message");
                    builder.setCancelable(true);
                    builder.setTitle("No Internet Connection");
                    builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                }
            });
        }
        catch (Exception ex){
            AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetailActivity.this);
            builder.setTitle("Received Message");
            builder.setCancelable(true);
            builder.setTitle("No Internet Connection");
            builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
        }

    }






//    class ProductDetail extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... params) {
//            String productUrl = getResources().getString(R.string.base_url) + "getProductDetail/" + p_id;
//
//            try {
//                URL url = new URL(productUrl);
//
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.setDoInput(true);
//                httpURLConnection.setDoOutput(true);
//
//                InputStream inputStream = httpURLConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//                String result = "", line = "";
//                while ((line = bufferedReader.readLine()) != null) {
//                    result += line;
//                }
//                return result;
//            } catch (Exception e) {
//                return e.toString();
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetailActivity.this);
//            builder.setTitle("Received Message");
//
//            try {
//
//                JSONArray productDetailsArray = new JSONArray(s);
//                JSONObject json_data = new JSONObject();
//                json_data = productDetailsArray.getJSONObject(0);
//
//                tvProductName.setText(json_data.getString("name"));
//                tvProductDesc.setText(json_data.getString("description"));
//                tvMRP.setText("\u20B9 " + json_data.getString("mrp"));
//                tvPrice.setText("\u20B9 " + json_data.getString("selling_price"));
//                product_id.setText(json_data.getString("id"));
//                tvQty.setText("1");
//                Picasso.with(ProductDetailActivity.this).load(getResources().getString(R.string.img_base_url) + "product_images/" + json_data.getString("image")).placeholder(R.drawable.watermark_icon).into(ivProductImage);
//
//                double p_mrp = Double.parseDouble(json_data.getString("mrp"));
//                double p_sp = Double.parseDouble(json_data.getString("selling_price"));
//                double p_dp = (p_mrp - p_sp);
//                double p_dp_p = p_dp / (p_mrp / 100);
//
//                int p_dp_i = (int) p_dp;
//                int p_dp_p_i = (int) p_dp_p;
//
//                tvSaved.setText("\u20B9" + p_dp_i);
//                tvSavedPer.setText("(" + p_dp_p_i + "%)");
//                mProgressBar.setVisibility(View.GONE);
//                view.setVisibility(View.VISIBLE);
//
//            } catch (JSONException e) {
//                builder.setCancelable(true);
//                builder.setTitle("No Internet Connection");
//                builder.setMessage("Please Connect to internet");
//                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//                builder.show();
//            }
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//    }

}

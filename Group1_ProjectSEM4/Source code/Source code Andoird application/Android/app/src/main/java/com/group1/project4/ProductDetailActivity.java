package com.group1.project4;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.group1.project4.interfaces.APIClient;
import com.group1.project4.interfaces.APIInterface;
import com.group1.project4.models.ProductResponse;
import com.group1.project4.util.NumberManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

    public static final String PREFS = "PREFS";
    TextView tvProductName, tvProductDesc, tvMRP, tvPrice, tvSaved, tvSavedPer, tvQty, product_id, tvDesc, tvBrand, tvUnittype;
    ImageView ivProductImage, add, remove;
    ImageButton backdetail;
    Button addtocart, buy_now;
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
        addToCart=new AddToCart(ProductDetailActivity.this, ProductDetailActivity.this);
        Bundle extras = getIntent().getExtras();
        p_id = extras.getString("p_id");

        sp = getSharedPreferences(PREFS, Context.MODE_PRIVATE);

        addtocart = findViewById(R.id.addtocart);
        product_id = findViewById(R.id.product_id);
        tvProductName = findViewById(R.id.product_name);
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
        backdetail = findViewById(R.id.backdetail);
        tvMRP.setPaintFlags(tvMRP.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        final int[] number = {1};
        tvQty.setText("" + number[0]);

        backdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), MyCartActivity.class);
                            finish();
                            startActivity(intent);
                        }
                    }, 500);

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
                    tvMRP.setText(NumberManager.getInstance().format(productDetails.getOriginalPrice()) + "đ");
                    tvPrice.setText(NumberManager.getInstance().format(productDetails.getSellingPrice()) + "đ");
                    product_id.setText(String.valueOf(productDetails.getId()));
                    tvQty.setText("1");
                    Picasso.with(ProductDetailActivity.this).load(productDetails.getImageURL()).placeholder(R.drawable.watermark_icon).into(ivProductImage);

                    double p_mrp = productDetails.getOriginalPrice();
                    double p_sp = productDetails.getSellingPrice();
                    double p_dp = (p_mrp - p_sp);
                    double p_dp_p = p_dp / (p_mrp / 100);

                    int p_dp_i = (int) p_dp;
                    int p_dp_p_i = (int) p_dp_p;

                    tvSaved.setText(NumberManager.getInstance().format(p_dp_i) + "đ");
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
}

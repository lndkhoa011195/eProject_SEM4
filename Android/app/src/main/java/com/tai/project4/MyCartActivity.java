package com.tai.project4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tai.project4.adapter.CartAdapter;
import com.tai.project4.interfaces.APIClient;
import com.tai.project4.interfaces.APIInterface;
import com.tai.project4.models.CartResult;
import com.tai.project4.util.NumberManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCartActivity extends AppCompatActivity {
    public static final String PREFS = "PREFS";
    SharedPreferences sp;
    double savings = 0;
    double payable_amt = 0;
    TextView tvSavings, tvPayableAmt, start_shopping;
    Button proceed;
    LinearLayout l1, l2, empty;
    private ProgressBar mProgressBar;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Cart");

        sp = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        String login_id = sp.getString("loginid", null);

        tvSavings = findViewById(R.id.total_discount);
        tvPayableAmt = findViewById(R.id.total_amount);
        proceed = findViewById(R.id.proceed);
        mProgressBar = findViewById(R.id.progressBar);
        l1 = findViewById(R.id.ll_item_products);
        l2 = findViewById(R.id.ll_item);
        empty = findViewById(R.id.empty_cart);
        start_shopping = findViewById(R.id.startshopping);

        start_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();

            }
        });

        mProgressBar.setVisibility(View.VISIBLE);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<List<CartResult>> CartResultListCall = apiInterface.GetProductsInCart(Integer.parseInt(login_id));
        CartResultListCall.enqueue(new Callback<List<CartResult>>() {
            @Override
            public void onResponse(Call<List<CartResult>> call, Response<List<CartResult>> response) {
                if (!response.isSuccessful())
                    return;
                List<CartResult> cartResults = response.body();
                if (cartResults.size() != 0) {
                    for (int i = 0; i < cartResults.size(); i++) {
                        double p_mrp = cartResults.get(i).getOriginalPrice();
                        double p_sp = cartResults.get(i).getSellingPrice();
                        double p_dp = (p_mrp - p_sp) / (p_mrp / 100);
                        int p_dp_i = (int) p_dp;
                        int p_qty = cartResults.get(i).getQuantity();
                        savings = savings + ((p_mrp - p_sp) * p_qty);
                        payable_amt = payable_amt + (p_sp * p_qty);
                    }
                    tvSavings.setText(NumberManager.getInstance().format(savings) + "đ");
                    tvPayableAmt.setText(NumberManager.getInstance().format(payable_amt) + "đ");

                    l1.setVisibility(View.VISIBLE);
                    l2.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.GONE);

                    RecyclerView cart_item_recyclerview = findViewById(R.id.recyclerview_item_products);
                    cart_item_recyclerview.setLayoutManager(new LinearLayoutManager(MyCartActivity.this));
                    cart_item_recyclerview.setAdapter(new CartAdapter(MyCartActivity.this, cartResults, tvSavings, tvPayableAmt, MyCartActivity.this));
                } else {
                    mProgressBar.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onFailure(Call<List<CartResult>> call, Throwable t) {
                call.cancel();
            }
        });


        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent checkOutIntent = new Intent(MyCartActivity.this, CheckOutActivity.class);
                startActivity(checkOutIntent);
            }
        });
    }
}

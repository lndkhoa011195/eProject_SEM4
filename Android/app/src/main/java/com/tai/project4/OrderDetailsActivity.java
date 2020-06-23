package com.tai.project4;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.Toast;
import com.google.gson.Gson;
import com.tai.project4.adapter.OrderDetailAdapter;
import com.tai.project4.interfaces.APIClient;
import com.tai.project4.interfaces.APIInterface;
import com.tai.project4.models.CartResult;
import com.tai.project4.models.OrderDetail;
import com.tai.project4.models.RequestResult;
import com.tai.project4.models.StatusCode;
import com.tai.project4.util.NumberManager;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsActivity extends AppCompatActivity {
    public static final String PREFS = "PREFS";
    SharedPreferences sp;
    double savings = 0;
    double payable_amt = 0;
    TextView tvSavings, tvPayableAmt, tvShipName, tvShipPhone, tvShipAddress, tvShipNote;
    Button btnCancel;
    LinearLayout l1, l2;
    private ProgressBar mProgressBar;
    public String order_id;
    AlertDialog.Builder builder;

    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Order Details");

        sp = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        String login_id = sp.getString("loginid", null);

        Bundle extras = getIntent().getExtras();
        order_id = extras.getString("order_id");

        tvSavings = findViewById(R.id.total_discount);
        tvPayableAmt = findViewById(R.id.total_amount);
        tvShipName = findViewById(R.id.shipName);
        tvShipPhone = findViewById(R.id.shipPhone);
        tvShipAddress = findViewById(R.id.shipAddress);
        tvShipNote = findViewById(R.id.shipNote);
        mProgressBar = findViewById(R.id.progressBar);
        btnCancel = findViewById(R.id.btnCancel);
        l1 = findViewById(R.id.ll_item_products);
        l2 = findViewById(R.id.ll_item);

        mProgressBar.setVisibility(View.VISIBLE);
        
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<RequestResult> call = apiInterface.GetOrderDetailsTest(order_id);
        call.enqueue(new Callback<RequestResult>() {
            @Override
            public void onResponse(Call<RequestResult> call, Response<RequestResult> response) {
                RequestResult result = response.body();
                if (result.getStatusCode() == StatusCode.FAILED) {
                    Toast.makeText(getApplicationContext(), result.getContent(), Toast.LENGTH_LONG).show();
                } else {
                    Gson gson = new Gson();
                    OrderDetail orderDetail = gson.fromJson(result.getContent(), OrderDetail.class);
                    List<CartResult> CartResultList = orderDetail.getDetails();
                    if (CartResultList.size() != 0) {
                        for (int i = 0; i < CartResultList.size(); i++) {
                            double p_mrp = CartResultList.get(i).getOriginalPrice();
                            double p_sp = CartResultList.get(i).getSellingPrice();
                            double p_dp = (p_mrp - p_sp) / (p_mrp / 100);
                            int p_dp_i = (int) p_dp;
                            int p_qty = CartResultList.get(i).getQuantity();
                            savings = savings + ((p_mrp - p_sp) * p_qty);
                            payable_amt = payable_amt + (p_sp * p_qty);
                        }
                        tvSavings.setText(NumberManager.getInstance().format(savings) + "đ");
                        tvPayableAmt.setText(NumberManager.getInstance().format(payable_amt) + "đ");
                        tvShipName.setText(orderDetail.getShipName());
                        tvShipPhone.setText(orderDetail.getShipPhone());
                        tvShipAddress.setText(orderDetail.getShipAddress());
                        tvShipNote.setText(orderDetail.getShipNote());
                        l1.setVisibility(View.VISIBLE);
                        l2.setVisibility(View.VISIBLE);
                        mProgressBar.setVisibility(View.GONE);
                        RecyclerView order_detail_item_recyclerview = findViewById(R.id.recyclerview_item_orders);
                        order_detail_item_recyclerview.setLayoutManager(new LinearLayoutManager(OrderDetailsActivity.this));
                        order_detail_item_recyclerview.setAdapter(new OrderDetailAdapter(CartResultList, OrderDetailsActivity.this));
                        if (orderDetail.getOrderStatus() == 1){
                            btnCancel.setBackgroundResource(R.color.background);
                            btnCancel.setTextColor(Color.WHITE);
                            btnCancel.setEnabled(true);
                            btnCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    builder = new AlertDialog.Builder(OrderDetailsActivity.this);
                                    builder.setMessage("Do you want to cancel an order ?")
                                            .setCancelable(false)
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    Call<RequestResult> call = apiInterface.CancelOrder(order_id);
                                                    call.enqueue(new Callback<RequestResult>() {
                                                        @Override
                                                        public void onResponse(Call<RequestResult> call, Response<RequestResult> response) {
                                                            if (!response.isSuccessful())
                                                                return;
                                                                Toast.makeText(getApplicationContext(), "Cancel Success",
                                                                        Toast.LENGTH_SHORT).show();
                                                                finish();
                                                        }

                                                        @Override
                                                        public void onFailure(Call<RequestResult> call, Throwable t) {
                                                            call.cancel();
                                                        }
                                                    });

                                                }
                                            })
                                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    //  Action for 'NO' Button
                                                    dialog.cancel();
                                                }
                                            });
                                    //Creating dialog box
                                    AlertDialog alert = builder.create();
                                    //Setting the title manually
                                    alert.setTitle("Cancel Oder");
                                    alert.show();
                                }
                            });
                        }else {
                            btnCancel.setEnabled(false);
                        }
                    } else {
                        mProgressBar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<RequestResult> call, Throwable t) {
                call.cancel();
            }
        });
    }

}

package com.group1.project4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.group1.project4.adapter.OrderDetailAdapter;
import com.group1.project4.interfaces.APIClient;
import com.group1.project4.interfaces.APIInterface;
import com.group1.project4.models.CartResult;
import com.group1.project4.models.CheckOutRequest;
import com.group1.project4.models.RequestResult;
import com.group1.project4.models.StatusCode;
import com.group1.project4.util.NumberManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOutActivity extends AppCompatActivity {
    public static final String PREFS = "PREFS";
    SharedPreferences sp;
    APIInterface apiInterface;
    EditText ShipName, ShipPhone, ShipAddress, ShipNote;
    Switch swLoadInfo;
    Button btnCheckOut;
    TextView checkout_total_discount, checkout_total_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Check Out");

        ShipName = findViewById(R.id.ShipName);
        ShipPhone = findViewById(R.id.ShipPhone);
        ShipAddress = findViewById(R.id.ShipAddress);
        ShipNote = findViewById(R.id.ShipNote);
        swLoadInfo = findViewById(R.id.swLoadInfo);
        btnCheckOut = findViewById(R.id.btnCheckOut);
        checkout_total_discount = findViewById(R.id.checkout_total_discount);
        checkout_total_amount = findViewById(R.id.checkout_total_amount);

        sp = getSharedPreferences(PREFS, Context.MODE_PRIVATE);

        String login_id = sp.getString("loginid", null);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<List<CartResult>> CartResultListCall = apiInterface.GetProductsInCart(Integer.parseInt(login_id));
        CartResultListCall.enqueue(new Callback<List<CartResult>>() {
            @Override
            public void onResponse(Call<List<CartResult>> call, Response<List<CartResult>> response) {
                if (!response.isSuccessful())
                    return;
                double savings = 0;
                double payable_amt = 0;
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
                    checkout_total_discount.setText(NumberManager.getInstance().format(savings) + "đ");
                    checkout_total_amount.setText(NumberManager.getInstance().format(payable_amt) + "đ");


                    RecyclerView chekout_recyclerview = findViewById(R.id.recyclerview_checkout);
                    chekout_recyclerview.setLayoutManager(new LinearLayoutManager(CheckOutActivity.this));
                    chekout_recyclerview.setAdapter(new OrderDetailAdapter(cartResults, CheckOutActivity.this));
                }

            }

            @Override
            public void onFailure(Call<List<CartResult>> call, Throwable t) {
                call.cancel();
                AlertDialog.Builder builder = new AlertDialog.Builder(CheckOutActivity.this);
                builder.setTitle("Received Message");
                builder.setCancelable(true);
                builder.setTitle("Can not connect to backend");
                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });

        swLoadInfo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    String Name = sp.getString("Name", "");
                    String Phone = sp.getString("Phone", "");
                    String Address = sp.getString("Address", "");

                    ShipName.setText(Name);
                    ShipPhone.setText(Phone);
                    ShipAddress.setText(Address);

                } else {
                    // The toggle is disabled
                    ShipName.setText("");
                    ShipPhone.setText("");
                    ShipAddress.setText("");
                }
            }
        });

        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = APIClient.getClient().create(APIInterface.class);
                String shipName = ShipName.getText().toString();
                String shipPhone = ShipPhone.getText().toString();
                String shipAddress = ShipAddress.getText().toString();
                String shipNote = ShipNote.getText().toString();
                if (shipName.isEmpty() || shipPhone.isEmpty() || shipAddress.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter shipping info", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    String login_id = sp.getString("loginid", null);
                    CheckOutRequest checkOutRequest = new CheckOutRequest(Integer.parseInt(login_id), shipName, shipPhone, shipAddress, shipNote);
                    Call<RequestResult> resultCall = apiInterface.CheckOut(checkOutRequest);
                    resultCall.enqueue(new Callback<RequestResult>() {
                        @Override
                        public void onResponse(Call<RequestResult> call, Response<RequestResult> response) {
                            if (!response.isSuccessful())
                            return;
                        RequestResult result = response.body();
                        if (result.getStatusCode() == StatusCode.FAILED) {
                            Toast.makeText(getApplicationContext(), result.getContent(), Toast.LENGTH_LONG).show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(CheckOutActivity.this);
                            builder.setTitle("Successful")
                                    .setMessage("Order Placed Successfully")
                                    .setIcon(R.drawable.ic_check_black)
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);        // Specify any activity here e.g. home or splash or login etc
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.putExtra("EXIT", true);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                            builder.show();
                        }

                        }

                        @Override
                        public void onFailure(Call<RequestResult> call, Throwable t) {
                            call.cancel();
                            AlertDialog.Builder builder = new AlertDialog.Builder(CheckOutActivity.this);
                            builder.setTitle("Received Message");
                            builder.setCancelable(true);
                            builder.setTitle("Can not connect to backend");
                            builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            builder.show();
                        }
                    });

                }
            }
        });
    }
}

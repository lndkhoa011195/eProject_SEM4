package com.tai.project4;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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

import com.tai.project4.adapter.CartAdapter;
import com.tai.project4.interfaces.APIClient;
import com.tai.project4.interfaces.APIInterface;
import com.tai.project4.models.CartResult;
import com.tai.project4.models.RequestResult;
import com.tai.project4.models.StatusCode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCart extends AppCompatActivity {
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
                    tvSavings.setText("\u20B9" + Double.toString(savings));
                    tvPayableAmt.setText("\u20B9" + Double.toString(payable_amt));

                    l1.setVisibility(View.VISIBLE);
                    l2.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.GONE);

                    RecyclerView cart_item_recyclerview = findViewById(R.id.recyclerview_item_products);
                    cart_item_recyclerview.setLayoutManager(new LinearLayoutManager(MyCart.this));
                    cart_item_recyclerview.setAdapter(new CartAdapter(cartResults, tvSavings, tvPayableAmt, MyCart.this));
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
                sp = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
                String login_id = sp.getString("loginid", null);
                apiInterface = APIClient.getClient().create(APIInterface.class);
                Call<RequestResult> resultCall = apiInterface.CheckOutTest(Integer.parseInt(login_id));
                resultCall.enqueue(new Callback<RequestResult>() {
                    @Override
                    public void onResponse(Call<RequestResult> call, Response<RequestResult> response) {
                        if (!response.isSuccessful())
                            return;
                        RequestResult result = response.body();
                        if (result.getErrorCode() == StatusCode.FAILED) {
                            Toast.makeText(getApplicationContext(), result.getContent(), Toast.LENGTH_LONG).show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MyCart.this);
                            builder.setTitle("Successful")
                                    .setMessage("Order Placed Successfully")
                                    .setIcon(R.drawable.ic_check_black)
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            Intent ii = new Intent(MyCart.this, HomeActivity.class);
                                            startActivity(ii);
                                            finish();
                                        }
                                    });
                            builder.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RequestResult> call, Throwable t) {
                        call.cancel();
                    }
                });


//                class PlaceOrder extends AsyncTask<String, Void, String> {
//
//                    @Override
//                    protected String doInBackground(String... params) {
//                        String cartProductsURL = getResources().getString(R.string.base_url) + "placeOrder/";
//
//                        try {
//                            URL url = new URL(cartProductsURL);
//                            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                            httpURLConnection.setRequestMethod("POST");
//                            httpURLConnection.setDoInput(true);
//                            httpURLConnection.setDoOutput(true);
//                            OutputStream outputStream = httpURLConnection.getOutputStream();
//                            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                            String post_Data = URLEncoder.encode("login_id", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8") + "&" +
//                                    URLEncoder.encode("savings", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&" +
//                                    URLEncoder.encode("payableamt", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8");
//
//                            bufferedWriter.write(post_Data);
//                            bufferedWriter.flush();
//                            bufferedWriter.close();
//                            outputStream.close();
//                            InputStream inputStream = httpURLConnection.getInputStream();
//                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//                            String result = "", line = "";
//                            while ((line = bufferedReader.readLine()) != null) {
//                                result += line;
//                            }
//                            return result;
//                        } catch (Exception e) {
//                            return e.toString();
//                        }
//                    }
//
//                    @Override
//                    protected void onPostExecute(String s) {
//                        super.onPostExecute(s);
//                        AlertDialog.Builder builder = new AlertDialog.Builder(MyCart.this);
//                        builder.setTitle("Successful")
//                                .setMessage("Order Placed Successfully")
//                                .setIcon(R.drawable.ic_check_black)
//                                .setCancelable(false)
//                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                                        Intent ii = new Intent(MyCart.this, OrderActivity.class);
//                                        startActivity(ii);
//                                        finish();
//                                    }
//                                });
//                        builder.show();
//                    }
//
//                    @Override
//                    protected void onPreExecute() {
//                        super.onPreExecute();
//                    }
//
//                }
//                PlaceOrder placeOrderOBJ = new PlaceOrder();
//                placeOrderOBJ.execute(sp.getString("loginid", null), tvSavings.getText().toString(), tvPayableAmt.getText().toString());
            }
        });
    }
}

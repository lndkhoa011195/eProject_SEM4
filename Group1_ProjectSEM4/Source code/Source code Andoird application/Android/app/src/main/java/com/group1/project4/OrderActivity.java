package com.group1.project4;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.gson.Gson;
import com.group1.project4.adapter.OrderAdapter;
import com.group1.project4.interfaces.APIClient;
import com.group1.project4.interfaces.APIInterface;
import com.group1.project4.models.OrderResult;
import com.group1.project4.models.RequestResult;
import com.group1.project4.models.StatusCode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {

    public static final String PREFS = "PREFS";
    SharedPreferences sp;
    ConstraintLayout cl;
    private ProgressBar mProgressBar;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Order History");
        mProgressBar = findViewById(R.id.progressBar);

        cl = findViewById(R.id.ll_empty);
        mProgressBar.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        sp = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        String login_id = sp.getString("loginid", null);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<RequestResult> cartRequestCall = apiInterface.GetOrders(Integer.parseInt(login_id));
        cartRequestCall.enqueue(new Callback<RequestResult>() {
            @Override
            public void onResponse(Call<RequestResult> call, Response<RequestResult> response) {
                if (!response.isSuccessful())
                    return;
                RequestResult result = response.body();
                if (result.getStatusCode() == StatusCode.FAILED) {
                    Toast.makeText(getApplicationContext(), result.getContent(), Toast.LENGTH_LONG).show();
                } else {
                    Gson gson = new Gson();
                    OrderResult[] categoryResultsArray = gson.fromJson(result.getContent(), OrderResult[].class);
                    List<OrderResult> OrderResultList = new ArrayList<>(Arrays.asList(categoryResultsArray));
                    mProgressBar.setVisibility(View.GONE);
                    if (OrderResultList.size() == 0) {
                        cl.setVisibility(View.VISIBLE);
                    } else {
                        RecyclerView order_recyclerview = findViewById(R.id.recyclerview_order_details);
                        order_recyclerview.setNestedScrollingEnabled(false);
                        order_recyclerview.setLayoutManager(new LinearLayoutManager(OrderActivity.this));
                        order_recyclerview.setAdapter(new OrderAdapter(OrderResultList, OrderActivity.this));
                    }
                }
            }

            @Override
            public void onFailure(Call<RequestResult> call, Throwable t) {
                call.cancel();
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
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

package com.group1.project4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.group1.project4.adapter.ProductAdapter;
import com.group1.project4.interfaces.APIClient;
import com.group1.project4.interfaces.APIInterface;
import com.group1.project4.models.ProductResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultsActivity extends AppCompatActivity implements AddorRemoveCallbacks {

    private ProgressBar mProgressBar;

    LinearLayout ll;
    ConstraintLayout cl;
    public static final String PREFS = "PREFS";
    SharedPreferences sp;
    int cart_count;
    APIInterface apiInterface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search_results);
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.mytoolbar);
        sp = getApplicationContext().getSharedPreferences(PREFS, MODE_PRIVATE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mProgressBar = findViewById(R.id.progressBar);
        getSupportActionBar().setTitle("Search Results");

        ll = findViewById(R.id.ll_products);
        cl = findViewById(R.id.ll_empty);

        Bundle bundle = getIntent().getExtras();
        final String search_text = bundle.getString("search_text");

        mProgressBar.setVisibility(View.VISIBLE);

//        Toast.makeText(this, sub_cat_id, Toast.LENGTH_SHORT).show();

        try {
            apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<List<ProductResponse>> call = apiInterface.GetProductsByName(search_text);

            call.enqueue(new Callback<List<ProductResponse>>() {
                @Override
                public void onResponse(Call<List<ProductResponse>> call, Response<List<ProductResponse>> response) {
                    try {
                        if (!response.isSuccessful())
                            return;
                        Log.d("TAG", response.code() + "");
                        List<ProductResponse> responseList = response.body();

                        mProgressBar.setVisibility(View.GONE);
                        if (responseList.size() == 0) {
                            cl.setVisibility(View.VISIBLE);
                        } else {
                            ll.setVisibility(View.VISIBLE);

                            RecyclerView product_recyclerview = findViewById(R.id.recyclerview_products);
                            product_recyclerview.setNestedScrollingEnabled(false);
                            product_recyclerview.setLayoutManager(new LinearLayoutManager(SearchResultsActivity.this));
                            product_recyclerview.setAdapter(new ProductAdapter(SearchResultsActivity.this, responseList, SearchResultsActivity.this));
                        }
                    } catch (Exception ex) {

                    }
                }

                @Override
                public void onFailure(Call<List<ProductResponse>> call, Throwable t) {
                    call.cancel();
                    AlertDialog.Builder builder = new AlertDialog.Builder(SearchResultsActivity.this);
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
        } catch (Exception ex) {
            Log.d("Error", ex.getMessage() + "");
        }
    }

    @Override
    public void onAddProduct() {
        cart_count++;
        invalidateOptionsMenu();
    }

    @Override
    public void onRemoveProduct() {
        cart_count--;
        invalidateOptionsMenu();
    }
}

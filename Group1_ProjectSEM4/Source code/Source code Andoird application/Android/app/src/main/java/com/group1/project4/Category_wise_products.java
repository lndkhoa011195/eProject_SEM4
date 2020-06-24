package com.group1.project4;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.group1.project4.adapter.ProductAdapter;
import com.group1.project4.interfaces.APIClient;
import com.group1.project4.interfaces.APIInterface;
import com.group1.project4.models.Converter;
import com.group1.project4.models.ProductResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Category_wise_products extends AppCompatActivity implements AddorRemoveCallbacks {

    public static final String PREFS = "PREFS";
    LinearLayout ll;
    SharedPreferences sp;
    ConstraintLayout cl;
    int cart_count;
    private ProgressBar mProgressBar;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_wise_products);
        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        sp = getApplicationContext().getSharedPreferences(PREFS, MODE_PRIVATE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mProgressBar = findViewById(R.id.progressBar);

        Bundle extras = getIntent().getExtras();
        cart_count = Integer.parseInt(extras.getString("cart_count"));

        ll = findViewById(R.id.ll_products);

        cl = findViewById(R.id.ll_empty);

        Bundle bundle = getIntent().getExtras();
        final String sub_cat_id = bundle.getString("sub_cat_id");
        final String sub_category = bundle.getString("sub_category");

        getSupportActionBar().setTitle(sub_category);

        mProgressBar.setVisibility(View.VISIBLE);


        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<List<ProductResponse>> ProductResultListCall = apiInterface.GetProductsBySubCategory(Integer.parseInt(sub_cat_id));
        ProductResultListCall.enqueue(new Callback<List<ProductResponse>>() {
            @Override
            public void onResponse(Call<List<ProductResponse>> call, Response<List<ProductResponse>> response) {
                if (!response.isSuccessful())
                    return;
                List<ProductResponse> resultList = response.body();
                mProgressBar.setVisibility(View.GONE);
                if (resultList.size() == 0) {

                    cl.setVisibility(View.VISIBLE);
                } else {
                    ll.setVisibility(View.VISIBLE);
                    RecyclerView product_recyclerview = findViewById(R.id.recyclerview_products);
                    product_recyclerview.setNestedScrollingEnabled(false);
                    product_recyclerview.setLayoutManager(new LinearLayoutManager(Category_wise_products.this));
                    product_recyclerview.setAdapter(new ProductAdapter(Category_wise_products.this,resultList, Category_wise_products.this));
                }
            }

            @Override
            public void onFailure(Call<List<ProductResponse>> call, Throwable t) {
                call.cancel();
                AlertDialog.Builder builder = new AlertDialog.Builder(Category_wise_products.this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        final MenuItem menuItem = menu.findItem(R.id.cart);
        MenuItem menuItem2 = menu.findItem(R.id.action_search);
        menuItem2.setVisible(false);
        menuItem.setIcon(Converter.convertLayoutToImage(Category_wise_products.this, cart_count, R.drawable.ic_shopping_cart_white));

        if (sp.getString("loginid", null) != null) {
            apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<String> cartCountCall = apiInterface.GetCartCount(Integer.parseInt(sp.getString("loginid", null)));
            cartCountCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (!response.isSuccessful())
                        return;
                    String result = response.body();
                    cart_count = Integer.parseInt(result);
                    menuItem.setIcon(Converter.convertLayoutToImage(Category_wise_products.this, cart_count, R.drawable.ic_shopping_cart_white));
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    call.cancel();
                }
            });

        }

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.cart) {

            if (sp.getString("loginid", null) != null) {
                Intent i = new Intent(this, MyCartActivity.class);
                startActivity(i);
                return true;
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(Category_wise_products.this);
                builder.setTitle("Heyy..")
                        .setMessage("To see your cart you have to login first. Do you want to login ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Category_wise_products.this, LoginActivity.class);
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        invalidateOptionsMenu();

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

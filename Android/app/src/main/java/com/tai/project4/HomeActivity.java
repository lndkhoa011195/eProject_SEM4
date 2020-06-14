package com.tai.project4;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.gson.Gson;
import com.mikepenz.itemanimators.AlphaCrossFadeAnimator;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.tai.project4.adapter.ProductAdapter;
import com.tai.project4.interfaces.APIClient;
import com.tai.project4.interfaces.APIInterface;
import com.tai.project4.models.CartRequest;
import com.tai.project4.models.CategoryResult;
import com.tai.project4.models.ProductResponse;
import com.tai.project4.models.Promotion;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements BaseSliderView.OnSliderClickListener, AddorRemoveCallbacks {

    SliderLayout sliderShow;
    private static int cart_count = 0;
    HashMap<String, String> url_maps = new HashMap<>();

    private GridView mGridView;
    private ProgressBar mProgressBar;
    List<String> bsp_id_list = new ArrayList<String>();
    private Bsp_Grid mGridAdapter;
    private ArrayList<GridItem> mGridData;
    public static final String PREFS = "PREFS";
    SharedPreferences sp;
    LinearLayout l2;
    SharedPreferences.Editor editor;

    private Drawer result = null;
    private AccountHeader headerResult = null;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        sp = getApplicationContext().getSharedPreferences(PREFS, MODE_PRIVATE);
        editor = sp.edit();
        l2 = findViewById(R.id.ll_best_selling);
        mProgressBar = findViewById(R.id.progressBar);

        handleIntent(getIntent());
        final IProfile profile;
        if (sp.getString("loginid", null) == null) {
            profile = new ProfileDrawerItem().withName("Guest").withEmail("guest@hktt.com").withIcon(R.drawable.account).withTag("Guest");

        } else {
            profile = new ProfileDrawerItem().withName(sp.getString("Name", null)).withEmail(sp.getString("Email", null)).withIcon(R.drawable.account).withTag("CUSTOMER");
        }


        final Intent i = new Intent(this, ProfileActivity.class);

        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.color.colorBackgound)
                .addProfiles(profile)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {


                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        //sample usage of the onProfileChanged listener
                        //if the clicked item has the identifier 1 add a new profile ;)
                        if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getTag().equals("CUSTOMER")) {
//                            headerResult.removeProfile(profile);
                            startActivity(i);
                        }
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();

        new DrawerBuilder().withActivity(this).build();

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withItemAnimator(new AlphaCrossFadeAnimator())
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        if (drawerItem != null) {
                            if (drawerItem.getTag().toString().equals("LOGIN")) {
                                Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                                startActivity(i);
                                finish();

                            } else if (drawerItem.getTag().toString().equals("ORDER_HISTORY")) {
                                Intent i = new Intent(HomeActivity.this, OrderActivity.class);
                                startActivity(i);

                            } else if (drawerItem.getTag().toString().equals("MY_CART")) {
                                Intent i = new Intent(HomeActivity.this, MyCart.class);
                                startActivity(i);

                            } else if (drawerItem.getTag().toString().equals("LOG_OUT")) {
                                cart_count = 0;
                                invalidateOptionsMenu();
                                editor.clear().apply();
                                Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                                startActivity(i);
                                finish();

                            } else if (drawerItem.getTag().toString().equals("CATEGORIES")) {

                            } else if (drawerItem.getTag().toString().equals("SUB_CATEGORIES")) {
                                Intent intent = new Intent(HomeActivity.this, Category_wise_products.class);
                                intent.putExtra("sub_cat_id", String.valueOf(drawerItem.getIdentifier()));
                                intent.putExtra("cart_count", "" + cart_count);
                                intent.putExtra("sub_category", ((Nameable) drawerItem).getName().toString());
                                startActivity(intent);
                            }
                        }

                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
//                .withShowDrawerUntilDraggedOpened(true)
                .build();

        if (sp.getString("loginid", null) != null) {
            PrimaryDrawerItem order_history = new PrimaryDrawerItem().withName("Order History").withIcon(R.drawable.ic_history_black).withTag("ORDER_HISTORY");
            PrimaryDrawerItem my_cart = new PrimaryDrawerItem().withName("My Cart").withIcon(R.drawable.ic_shopping_cart_black).withTag("MY_CART");
            result.addItem(order_history);
            result.addItem(my_cart);
            result.addStickyFooterItem(new PrimaryDrawerItem().withName("Log Out").withIcon(R.drawable.ic_log_out).withTag("LOG_OUT"));

        } else {
            result.addStickyFooterItem(new PrimaryDrawerItem().withName("Log In").withIcon(R.drawable.ic_person_black).withTag("LOGIN"));
        }

        result.addItem(new DividerDrawerItem());
        result.addItem(new SecondaryDrawerItem().withName("Shop By Category").withTag("CATEGORY_LABEL").withSelectable(false).withSetSelected(false).withTextColor(getResources().getColor(R.color.colorBackgound)));
        result.addItem(new DividerDrawerItem());

        //SetCategoryAndSubCategory
        try {
            apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<List<CategoryResult>> promotionCall = apiInterface.GetCategoryAndSubCategory();
            promotionCall.enqueue(new Callback<List<CategoryResult>>() {
                @Override
                public void onResponse(Call<List<CategoryResult>> call, Response<List<CategoryResult>> response) {
                    if (!response.isSuccessful())
                        return;
                    //Log.d("TAG", response.code() + "");
                    List<CategoryResult> categoryResultList = response.body();
                    List<String> categories = new ArrayList<>();
                    for (CategoryResult cate : categoryResultList) {
                        categories.add(cate.getCategoryName());
                    }

                    List<String> categoryListNoDup = new ArrayList<>(
                            new HashSet<>(categories));
                    //Order by ascending
                    Collections.sort(categoryListNoDup);

                    for (String cate : categoryListNoDup) {
                        ExpandableDrawerItem item = new ExpandableDrawerItem().withName(cate).withIcon(R.drawable.ic_filter_list_black).withIdentifier(0).withSelectable(false).withTag("CATEGORIES");
                        for (CategoryResult subcate : categoryResultList) {
                            if (subcate.getCategoryName().equals(cate)) {
                                item.withSubItems(new SecondaryDrawerItem().withLevel(2).withName(subcate.getSubCategoryName()).withIcon(R.drawable.ic_minus_black).withIdentifier(subcate.getSubCategoryID()).withTag("SUB_CATEGORIES"));
                            }
                        }
                        result.addItem(item);
                    }
                }

                @Override
                public void onFailure(Call<List<CategoryResult>> call, Throwable t) {
                    call.cancel();
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
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

        } catch (Exception ex) {
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);

        //get promotion slider
        try {
            apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<List<Promotion>> promotionCall = apiInterface.GetPromotionProducts();
            promotionCall.enqueue(new Callback<List<Promotion>>() {
                @Override
                public void onResponse(Call<List<Promotion>> call, Response<List<Promotion>> response) {
                    if (!response.isSuccessful())
                        return;
                    //Log.d("TAG", response.code() + "");
                    List<Promotion> PromotionList = response.body();

                    for (int i = 0; i < PromotionList.size(); i++) {
                        url_maps.put("", PromotionList.get(i).getImageURL());
                        sliderShow = findViewById(R.id.slider);

                        for (String name : url_maps.keySet()) {
                            DefaultSliderView defaultSliderView = new DefaultSliderView(HomeActivity.this);
                            // initialize a SliderLayout
                            defaultSliderView
                                    .image(url_maps.get(name))
                                    .setOnSliderClickListener(HomeActivity.this);


                            defaultSliderView.bundle(new Bundle());
                            defaultSliderView.getBundle()
                                    .putString("extra", String.valueOf(PromotionList.get(i).getProductId()));


                            sliderShow.addSlider(defaultSliderView);
                        }
                        sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                        sliderShow.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator));
                    }


                }

                @Override
                public void onFailure(Call<List<Promotion>> call, Throwable t) {
                    call.cancel();
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
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

        } catch (Exception ex) {
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
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



        /*  Grid View Best Selling Product  */

        mGridView = findViewById(R.id.gridView);

        mProgressBar = findViewById(R.id.progressBar);

        //Initialize with empty data
        mGridData = new ArrayList<>();
        mGridAdapter = new Bsp_Grid(this, R.layout.bsp_grid_single, mGridData);
        mGridView.setAdapter(mGridAdapter);

        //Grid view click event
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Product detail = new Product();
                detail.startProductDetailActivity(bsp_id_list.get(position), HomeActivity.this);
            }
        });

        //Start download


        mProgressBar.setVisibility(View.VISIBLE);

        //GetBestDeals
        try {
            apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<List<ProductResponse>> promotionCall = apiInterface.GetBestDealProducts();
            promotionCall.enqueue(new Callback<List<ProductResponse>>() {
                @Override
                public void onResponse(Call<List<ProductResponse>> call, Response<List<ProductResponse>> response) {
                    if (!response.isSuccessful())
                        return;
                    //Log.d("TAG", response.code() + "");
                    List<ProductResponse> BestSellingProductsList = response.body();

                    l2.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.GONE);

                    RecyclerView product_recyclerview = findViewById(R.id.recyclerview_best_deals);
                    product_recyclerview.setNestedScrollingEnabled(false);
                    product_recyclerview.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
                    product_recyclerview.setAdapter(new ProductAdapter(HomeActivity.this, BestSellingProductsList, HomeActivity.this));


                }

                @Override
                public void onFailure(Call<List<ProductResponse>> call, Throwable t) {
                    call.cancel();
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
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

        } catch (Exception ex) {
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
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


        //GetRecentProducts
        try {
            apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<List<ProductResponse>> promotionCall = apiInterface.GetRecentProducts();
            promotionCall.enqueue(new Callback<List<ProductResponse>>() {
                @Override
                public void onResponse(Call<List<ProductResponse>> call, Response<List<ProductResponse>> response) {
                    if (!response.isSuccessful())
                        return;
                    //Log.d("TAG", response.code() + "");
                    List<ProductResponse> RecentProductsList = response.body();
                    GridItem item;
                    for (int i = 0; i < RecentProductsList.size(); i++) {
                        String title = "Nothing";

                        if (RecentProductsList.get(i).getName().length() > 20) {
                            title = RecentProductsList.get(i).getName().substring(0, 19);
                        } else {
                            title = RecentProductsList.get(i).getName();
                        }
                        item = new GridItem();
                        item.setTitle(title);

                        item.setImage(RecentProductsList.get(i).getImageURL());
                        bsp_id_list.add(String.valueOf(RecentProductsList.get(i).getId()));
                        mGridData.add(item);
                    }
                    mGridAdapter.setGridData(mGridData);
                    mProgressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<List<ProductResponse>> call, Throwable t) {
                    call.cancel();
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
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

        } catch (Exception ex) {
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
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


    @Override
    protected void onPostResume() {
        super.onPostResume();
        invalidateOptionsMenu();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {

        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        final MenuItem menuItem = menu.findItem(R.id.cart);
        menuItem.setIcon(Converter.convertLayoutToImage(HomeActivity.this, cart_count, R.drawable.ic_shopping_cart_white));

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
                    menuItem.setIcon(Converter.convertLayoutToImage(HomeActivity.this, cart_count, R.drawable.ic_shopping_cart_white));

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
                Intent i = new Intent(this, MyCart.class);
                startActivity(i);
                return true;
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle("Heyy..")
                        .setMessage("To see your cart you have to login first. Do you want to login ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
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
    public void onSliderClick(BaseSliderView slider) {

        String product_id = slider.getBundle().get("extra").toString();
        Product detail = new Product();
        detail.startProductDetailActivity(product_id, HomeActivity.this);

    }

    @Override
    protected void onNewIntent(Intent intent) {

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Intent i = new Intent(this, SearchResultsActivity.class);
            i.putExtra("search_text", query);
            startActivity(i);
            //use the query to search your data somehow
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

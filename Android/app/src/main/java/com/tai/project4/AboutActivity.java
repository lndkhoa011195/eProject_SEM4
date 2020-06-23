package com.tai.project4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.tai.project4.interfaces.APIClient;
import com.tai.project4.interfaces.APIInterface;
import com.tai.project4.model.Account;
import com.tai.project4.models.RequestResult;
import com.tai.project4.models.StatusCode;
import com.tai.project4.models.StoreInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    APIInterface apiInterface;
    TextView infoStoreOwner, infoStoreEmail, infoStorePhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = findViewById(R.id.mytoolbar);
        infoStoreOwner = findViewById(R.id.infoStoreOwner);
        infoStoreEmail = findViewById(R.id.infoStoreEmail);
        infoStorePhone = findViewById(R.id.infoStorePhone);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("About us");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.location);
        mapFragment.getMapAsync(this);
        try {
            apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<RequestResult> call = apiInterface.GetStoreInfo();
            call.enqueue(new Callback<RequestResult>() {
                @Override
                public void onResponse(Call<RequestResult> call, Response<RequestResult> response) {
                    if (!response.isSuccessful())
                        return;

                    RequestResult result = response.body();
                    if(result.getStatusCode() == StatusCode.SUCCESS){
                        Gson gson = new Gson();
                        StoreInfo storeInfo = gson.fromJson(result.getContent(), StoreInfo.class);
                        infoStoreOwner.setText("Owner: "+ storeInfo.getName());
                        infoStoreEmail.setText("Email: "+ storeInfo.getEmail());
                        infoStorePhone.setText("Phone: "+ storeInfo.getPhone());
                    }
                    else {
                        Toast.makeText(getApplicationContext(), result.getContent(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<RequestResult> call, Throwable t) {
                    call.cancel();
                }
            });
        } catch (Exception ex) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);
            builder.setTitle("Received Message");
            builder.setCancelable(true);
            builder.setTitle("Can not connect to backend!");
            builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng store = new LatLng(10.786504, 106.666306);
        mMap.addMarker(new MarkerOptions().position(store).title("HKTT Store"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(store));
        mMap.setMinZoomPreference(15.0f);
        mMap.setMaxZoomPreference(20.0f);
    }
}

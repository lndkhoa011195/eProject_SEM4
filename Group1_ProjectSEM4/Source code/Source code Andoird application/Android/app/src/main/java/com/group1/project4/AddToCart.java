package com.group1.project4;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.group1.project4.interfaces.APIClient;
import com.group1.project4.interfaces.APIInterface;
import com.group1.project4.models.CartRequest;
import com.group1.project4.util.LoadingDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddToCart {
    SharedPreferences sp;
    Context context;
    Activity activity;
    public static final String PREFS = "PREFS";
    int count;

    APIInterface apiInterface;

    public AddToCart(Activity activity,Context context) {
        this.activity = activity;
        this.context=context;
        this.sp = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    public void addToCart(String pid,String qty) {
        final LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.showLoadingDialog();
        String loginid = sp.getString("loginid", null);
        count=Integer.parseInt(qty);
        count++;

        try {
            apiInterface = APIClient.getClient().create(APIInterface.class);
            CartRequest request = new CartRequest(Integer.parseInt(loginid), Integer.parseInt(pid), Integer.parseInt(qty));
            Call<String> cartRequestCall = apiInterface.AddToCart(request);
            cartRequestCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (!response.isSuccessful())
                        return;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadingDialog.dismissLoadingDialog();
                        }
                    }, 500);

                    Log.d("TAG", response.code() + "");
                    String result = response.body();
                    if(result.equals("Failed"))
                    {
                        Toast.makeText(context, "Can not add product to cart", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(context, "Product Added to cart successfully", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    call.cancel();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadingDialog.dismissLoadingDialog();
                        }
                    }, 500);


                }
            });
        }
        catch (Exception ex){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
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

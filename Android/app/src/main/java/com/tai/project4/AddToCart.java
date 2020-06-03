package com.tai.project4;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.tai.project4.interfaces.APIClient;
import com.tai.project4.interfaces.APIInterface;
import com.tai.project4.models.CartRequest;
import com.tai.project4.models.ProductResponse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddToCart {
    SharedPreferences sp;
    Context context;
    public static final String PREFS = "PREFS";
    int count;

    APIInterface apiInterface;

    public AddToCart(Context context) {
        this.context=context;
        this.sp = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    public void addToCart(String pid,String qty) {

        String loginid = sp.getString("loginid", null);
        count=Integer.parseInt(qty);
        //count++;

        try {
            apiInterface = APIClient.getClient().create(APIInterface.class);
            CartRequest request = new CartRequest(Integer.parseInt(loginid), Integer.parseInt(pid), Integer.parseInt(qty));
            Call<String> cartRequestCall = apiInterface.AddToCart(request);
            cartRequestCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (!response.isSuccessful())
                        return;
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



//        if ((!pid.equals("")) && (!qty.equals("")) && (!loginid.equals(null))) {
//            class AddToCartInner extends AsyncTask<String, Void, String> {
//
//                @Override
//                protected void onPreExecute() {
//                    super.onPreExecute();
//                }
//
//                @Override
//                protected void onPostExecute(String s) {
//                    super.onPostExecute(s);
//
//                    Toast.makeText(context, "Product Added to cart successfully", Toast.LENGTH_SHORT).show();
//
//                }
//
//                @Override
//                protected String doInBackground(String... params) {
//
//                    String urls = context.getResources().getString(R.string.base_url).concat("addToCart/");
//                    try {
//                        URL url = new URL(urls);
//                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                        httpURLConnection.setRequestMethod("POST");
//                        httpURLConnection.setDoInput(true);
//                        httpURLConnection.setDoOutput(true);
//                        OutputStream outputStream = httpURLConnection.getOutputStream();
//                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                        String post_Data = URLEncoder.encode("login_id", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8") + "&" +
//                                URLEncoder.encode("product_id", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&" +
//                                URLEncoder.encode("qty", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8");
//
//                        bufferedWriter.write(post_Data);
//                        bufferedWriter.flush();
//                        bufferedWriter.close();
//                        outputStream.close();
//                        InputStream inputStream = httpURLConnection.getInputStream();
//                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//                        String result = "", line = "";
//                        while ((line = bufferedReader.readLine()) != null) {
//                            result += line;
//                        }
//                        return result;
//                    } catch (Exception e) {
//                        return e.toString();
//                    }
//                }
//            }
//
//            //creating asynctask object and executing it
//            AddToCartInner atocObj = new AddToCartInner();
//            atocObj.execute(loginid, pid, qty);
//        } else {
//            AlertDialog.Builder builder=new AlertDialog.Builder(context);
//            builder.setTitle("Not Added To Cart");
//            builder.setMessage("The Product That You want to add is not added");
//            builder.show();
//        }
    }
}

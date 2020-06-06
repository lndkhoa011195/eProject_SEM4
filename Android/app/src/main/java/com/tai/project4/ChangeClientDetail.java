package com.tai.project4;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tai.project4.interfaces.APIClient;
import com.tai.project4.interfaces.APIInterface;
import com.tai.project4.model.Account;
import com.tai.project4.models.RequestResult;
import com.tai.project4.models.StatusCode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeClientDetail extends AppCompatActivity{

    EditText etName, etMobile, etEmail, etAddress;
    Button register;
    public static final String PREFS="PREFS";
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_client_detail);

        sp=getApplicationContext().getSharedPreferences(PREFS,MODE_PRIVATE);
        edit=sp.edit();
        etName = findViewById(R.id.name);
        etEmail = findViewById(R.id.email);
        etMobile = findViewById(R.id.mobile);
        etAddress = findViewById(R.id.address);
        register = findViewById(R.id.register);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        if (sp.getString("loginid", null) != null) {
            etName.setText(sp.getString("Name", "Name"));
            etEmail.setText(sp.getString("Email", "Email"));
            etMobile.setText(sp.getString("Phone", "Phone"));
            etAddress.setText(sp.getString("Address", "Address"));
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Id = sp.getString("loginid", null);
                String Name = etName.getText().toString();
                String Email = etEmail.getText().toString();
                String Phone = etMobile.getText().toString();
                String Address = etAddress.getText().toString();
                String Password = sp.getString("Password", "Password");

                Account account = new Account(Integer.parseInt(Id), Name, Email, Phone, Password, Address);
                Call<RequestResult> call = apiInterface.UpdateInfo(account);
                call.enqueue(new Callback<RequestResult>() {
                    @Override
                    public void onResponse(Call<RequestResult> call, Response<RequestResult> response) {
                        if (!response.isSuccessful())
                            return;
                        Log.d("TAG", response.code() + "");
                        RequestResult result = response.body();
                        if (result.getErrorCode() == StatusCode.FAILED) {
                            Toast.makeText(getApplicationContext(), result.getContent(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Register successful.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RequestResult> call, Throwable t) {

                    }
                });
            }
        });
    }
}

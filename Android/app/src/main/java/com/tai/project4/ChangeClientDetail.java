package com.tai.project4;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tai.project4.interfaces.APIClient;
import com.tai.project4.interfaces.APIInterface;
import com.tai.project4.models.Account;
import com.tai.project4.models.RequestResult;
import com.tai.project4.models.StatusCode;
import com.tai.project4.util.Helper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeClientDetail extends AppCompatActivity {

    EditText etName, etMobile, etEmail, etAddress;
    Button register;
    public static final String PREFS = "PREFS";
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_client_detail);

        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update");
        sp = getApplicationContext().getSharedPreferences(PREFS, MODE_PRIVATE);
        edit = sp.edit();
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

                Helper helper = new Helper();
                if (!helper.isValidEmail(etEmail.getText().toString())) {
                    Toast.makeText(ChangeClientDetail.this, "Enter Valid Email Address", Toast.LENGTH_SHORT).show();
                } else if (!helper.validateLetters(etName.getText().toString())) {
                    Toast.makeText(ChangeClientDetail.this, "Enter Valid Name", Toast.LENGTH_SHORT).show();
                } else if (!helper.validatePhone(etMobile.getText().toString())) {
                    Toast.makeText(ChangeClientDetail.this, "Enter Valid Phone", Toast.LENGTH_SHORT).show();
                } else if (!helper.validateAddress(etAddress.getText().toString())) {
                    Toast.makeText(ChangeClientDetail.this, "Enter Valid Address", Toast.LENGTH_SHORT).show();
                } else
                    {
                    Account account = new Account(Integer.parseInt(Id), Name, Email, Phone, Password, Address);
                    Call<RequestResult> call = apiInterface.UpdateInfo(account);
                    call.enqueue(new Callback<RequestResult>() {
                        @Override
                        public void onResponse(Call<RequestResult> call, Response<RequestResult> response) {
                            if (!response.isSuccessful())
                                return;
                            Log.d("TAG", response.code() + "");
                            RequestResult result = response.body();
                            if (result.getStatusCode() == StatusCode.FAILED) {
                                Toast.makeText(getApplicationContext(), result.getContent(), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Update successful.", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<RequestResult> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }
}

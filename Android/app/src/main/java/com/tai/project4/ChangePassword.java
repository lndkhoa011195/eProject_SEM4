package com.tai.project4;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.tai.project4.models.ChangePass;
import com.tai.project4.models.RequestResult;
import com.tai.project4.models.StatusCode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {

    EditText tvOldPsw, tvNewPsw, tvConfPsw;
    Button bChange;
    public static final String PREFS = "PREFS";
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change Password");
        tvOldPsw = findViewById(R.id.otp);
        tvNewPsw = findViewById(R.id.new_psw);
        tvConfPsw = findViewById(R.id.conf_psw);
        bChange = findViewById(R.id.change);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        sp = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        edit = sp.edit();

        bChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String customerId = sp.getString("loginid", null);
                String oldPassword = tvOldPsw.getText().toString();
                String newPassword = tvNewPsw.getText().toString();
                String cpPassword = tvConfPsw.getText().toString();

                if(tvNewPsw.getText().toString().equals(tvConfPsw.getText().toString())){
                    ChangePass changePass = new ChangePass(Integer.parseInt(customerId) , oldPassword, newPassword);
                    Call<RequestResult> call = apiInterface.ChangePassword(changePass);
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this);
                                builder.setTitle("Successful")
                                        .setMessage("Change Password Successfully. \n please Login again")
                                        .setIcon(R.drawable.ic_check_black)
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                intent.putExtra("EXIT", true);
                                                startActivity(intent);
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
                }else {
                    Toast.makeText(getApplicationContext(), "Password and confirm password not matches.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}

package com.tai.project4;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.tai.project4.interfaces.APIClient;
import com.tai.project4.interfaces.APIInterface;
import com.tai.project4.model.Account;
import com.tai.project4.models.RequestResult;
import com.tai.project4.models.StatusCode;
import com.tai.project4.util.Helper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    String Name, Email, Phone, Address, Password, cpassword;
    EditText etName, etMobile, etEmail, etAddress, etPassword, etcPassword;
    Button register;
    APIInterface apiInterface;
    TextView loginnext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loginnext = findViewById(R.id.loginnext);
        etName = findViewById(R.id.name);
        etEmail = findViewById(R.id.email);
        etMobile = findViewById(R.id.mobile);
        etAddress = findViewById(R.id.address);
        etPassword = findViewById(R.id.password);
        etcPassword = findViewById(R.id.cpassword);
        register = findViewById(R.id.register);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        loginnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = etName.getText().toString();
                Email = etEmail.getText().toString();
                Phone = etMobile.getText().toString();
                Address = etAddress.getText().toString();
                Password = etPassword.getText().toString();
                cpassword = etcPassword.getText().toString();

                Helper helper = new Helper();
                if ((!Name.equals("")) && (!Email.equals("")) && (!Phone.equals("")) && (!Address.equals("")) && (!Password.equals("")) && (!cpassword.equals(""))) {
                    Account account = new Account(0, Name, Email, Phone, Password, Address);
                    if(!cpassword.equals(Password))
                    {
                        Toast.makeText(getApplicationContext(), "Password and confirm password not matches.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    else if(!helper.isValidEmail(etEmail.getText().toString()))
                    {
                        Toast.makeText(RegisterActivity.this, "Enter Valid Email Address", Toast.LENGTH_SHORT).show();
                    }else {
                        Call<RequestResult> call = apiInterface.SignUp(account);
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
                                    Toast.makeText(getApplicationContext(), "Register successful. Please go back to login.", Toast.LENGTH_LONG).show();
                                    etName.setText("");
                                    etEmail.setText("");
                                    etMobile.setText("");
                                    etAddress.setText("");
                                    etPassword.setText("");
                                    etcPassword.setText("");
                                }
                            }

                            @Override
                            public void onFailure(Call<RequestResult> call, Throwable t) {
                                call.cancel();
                            }
                        });
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Please enter all infomations.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

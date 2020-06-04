package com.tai.project4;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.tai.project4.model.Login;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static final String PREFS = "PREFS";
    EditText etLogin_email, etPassword;
    Button btnLogin, btnSkip;
    TextView signup;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp = getApplicationContext().getSharedPreferences(PREFS, MODE_PRIVATE);
        edit = sp.edit();
        signup = findViewById(R.id.signup);
        etLogin_email = findViewById(R.id.loginEmail);
        etPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.login);
        btnSkip = findViewById(R.id.skip_login);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String txtEmail = etLogin_email.getText().toString();
                final String txtPass = etPassword.getText().toString();
                if (txtEmail.isEmpty() || txtPass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Đm nhập cho đủ vào", Toast.LENGTH_SHORT).show();
                } 
                else {
                    Login login = new Login(txtEmail, txtPass);
                    Call<Account> call3 = apiInterface.Login(login);
                    call3.enqueue(new Callback<Account>() {
                        @Override
                        public void onResponse(Call<Account> call, Response<Account> response) {
                            try {
                                if (!response.isSuccessful())
                                    return;
                                Log.d("TAG", response.code() + "");
                                Account Account = response.body();

                                if (txtEmail.equalsIgnoreCase(Account.getEmail())) {
                                    edit.putString("loginid", String.valueOf(Account.getId()));
                                    edit.putString("Name", Account.getName());
                                    edit.putString("Email", Account.getEmail());
                                    edit.putString("Phone", Account.getPhone());
                                    edit.putString("Address", Account.getAddress());
                                    edit.apply();
                                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Login Invalid", Toast.LENGTH_SHORT).show();
                                    etPassword.setText("");
                                }
                            } catch (Exception e) {

                            }
                        }

                        @Override
                        public void onFailure(Call<Account> call, Throwable t) {
                            call.cancel();
                        }
                    });
                }
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
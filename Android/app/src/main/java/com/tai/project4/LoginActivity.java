package com.tai.project4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
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
import com.tai.project4.model.Data;
import com.tai.project4.model.Login;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

public class LoginActivity extends AppCompatActivity {

    public static final String PREFS = "PREFS";
    EditText etLogin_email, etPassword;
    Button btnLogin, btnSkip;
    //String login_id, password;
    TextView signup, forget, responeText;
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
        forget = findViewById(R.id.forget);
        responeText = findViewById(R.id.responseText);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String txtEmail = etLogin_email.getText().toString();
                final String txtPass = etPassword.getText().toString();
                if (txtEmail.isEmpty() || txtPass.isEmpty()) {
                    responeText.setText("Cút");
                    //Toast.makeText(LoginActivity.this, "Đm nhập cho đủ vào", Toast.LENGTH_SHORT).show();
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
                                    edit.putString("Id", String.valueOf(Account.getId()));
                                    edit.putString("Name", Account.getName());
                                    edit.putString("Email", Account.getEmail());
                                    edit.putString("Phone", Account.getPhone());
                                    edit.putString("Address", Account.getAddress());
                                    edit.apply();
                                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
//                                i.putExtras(bundle);
                                    startActivity(i);
                                    finish();
                                } else {
                                    //Toast.makeText(LoginActivity.this, "Login Invalid", Toast.LENGTH_SHORT).show();
                                    responeText.setText("Login Invalid");
                                    etPassword.setText("");
                                }
                            } catch (Exception e) {
                                //Toast.makeText(LoginActivity.this, "Login Invalid", Toast.LENGTH_SHORT).show();
                                //responeText.setText("Login Invalid");
                                //etPassword.setText("");
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
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ForgetActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

//    public void login() {
//        login_id = etLogin_id.getText().toString();
//        password = etPassword.getText().toString();
//        if ((!login_id.equals("")) && (!password.equals(""))) {
//            class LoginUser extends AsyncTask<String, Void, String> {
//
//                @Override
//                protected void onPreExecute() {
//                    super.onPreExecute();
//                }
//
//                @Override
//                protected void onPostExecute(String s) {
//                    super.onPostExecute(s);
//                    if (s.trim().equals("INLOGIN")) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
//                        builder.setTitle("Login Status")
//                                .setMessage("Invalid Login");
//                        builder.show();
//                    } else {
//
//                        try {
//                            JSONArray clientDetailArray = new JSONArray(s);
//                            JSONObject json_data = new JSONObject();
//                            json_data = clientDetailArray.getJSONObject(0);
//
//                            edit.putString("loginid", json_data.getString("email"));
//                            edit.putString("name", json_data.getString("name"));
//                            edit.putString("mobile", json_data.getString("mobile"));
//                            edit.putString("city", json_data.getString("city"));
//                            edit.putString("locality", json_data.getString("locality"));
//                            edit.putString("address", json_data.getString("address"));
//                            edit.putString("c_dt", json_data.getString("c_dt"));
//                            edit.apply();
//                            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
//                            startActivity(i);
//                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
//                            finish();
//                        } catch (Exception e) {
//                            Toast.makeText(LoginActivity.this, "Invalid Login"
//                                    , Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//
//                @Override
//                protected String doInBackground(String... params) {
//
//                    String urls = "http://10.10.14.60:5656/api/Customer/Authenticate";
//                    try {
//                        URL url = new URL(urls);
//                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                        httpURLConnection.setRequestMethod("POST");
//                        httpURLConnection.setDoInput(true);
//                        httpURLConnection.setDoOutput(true);
//                        OutputStream outputStream = httpURLConnection.getOutputStream();
//                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                        String post_Data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8") + "&" +
//                                URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8");
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
//            LoginUser loginUsrObj = new LoginUser();
//            loginUsrObj.execute(login_id, password);
//        } else {
//            Toast.makeText(this, "All Fields Are Mandatory", Toast.LENGTH_SHORT).show();
//        }
//    }
}
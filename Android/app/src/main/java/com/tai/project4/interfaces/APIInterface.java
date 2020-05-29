package com.tai.project4.interfaces;



import com.tai.project4.model.Account;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIInterface {
    @FormUrlEncoded
    @POST("/api/Customer/Authenticate")
    Call<Account> doPostLogin(@Field("email") String email,@Field("password") String password);
}

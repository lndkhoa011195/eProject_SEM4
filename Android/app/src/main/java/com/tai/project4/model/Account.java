package com.tai.project4.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Account implements Serializable {
    @SerializedName("idAccount")
    public String idAccount;
    @SerializedName("nameAcc")
    public String nameAcc;
    @SerializedName("phone")
    public String phone;
    @SerializedName("email")
    public String email;
}

package com.tai.project4.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Account implements Serializable {

    @SerializedName("Email")
    public String email;
    @SerializedName("Password")
    public String password;
    @SerializedName("Name")
    public String name;
    @SerializedName("Phone")
    public String phone;
}

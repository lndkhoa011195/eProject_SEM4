package com.tai.project4.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Account implements Serializable {

    @SerializedName("Id")
    public Integer Id;
    @SerializedName("Name")
    public String Name;
    @SerializedName("Email")
    public String Email;
    @SerializedName("Phone")
    public String Phone;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public Account(Integer id, String name, String email, String phone) {
        Id = id;
        Name = name;
        Email = email;
        Phone = phone;
    }

    public Account() {
    }
}

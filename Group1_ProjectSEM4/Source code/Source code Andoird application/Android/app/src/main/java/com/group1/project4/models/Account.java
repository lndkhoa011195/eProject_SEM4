package com.group1.project4.models;

import java.io.Serializable;

public class Account implements Serializable {

    public Integer Id;
    public String Name;
    public String Email;
    public String Phone;
    public String Password;
    public String Address;

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

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Account(Integer id, String name, String email, String phone, String password, String address) {
        Id = id;
        Name = name;
        Email = email;
        Phone = phone;
        Password = password;
        Address = address;
    }
}

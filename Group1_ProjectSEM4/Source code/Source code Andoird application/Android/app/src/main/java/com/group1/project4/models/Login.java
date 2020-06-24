package com.group1.project4.models;

public class Login {
    public String Email;
    public String Password;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Login(String email, String password) {
        Email = email;
        Password = password;
    }

    public Login() {
    }
}

package com.group1.project4.models;

public class CheckOutRequest {
    private int CustomerId;
    private String Name;
    private String Phone;
    private String ShippingAddress;
    private String Note;

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getShippingAddress() {
        return ShippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        ShippingAddress = shippingAddress;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public CheckOutRequest(int customerId, String name, String phone, String shippingAddress, String note) {
        CustomerId = customerId;
        Name = name;
        Phone = phone;
        ShippingAddress = shippingAddress;
        Note = note;
    }
}

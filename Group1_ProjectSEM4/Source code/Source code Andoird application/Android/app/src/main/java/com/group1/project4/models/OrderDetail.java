package com.group1.project4.models;

import java.util.List;

public class OrderDetail {
    private String ShipName;
    private String ShipPhone;
    private String ShipAddress;
    private String ShipNote;
    private Integer OrderStatus;
    private List<CartResult> Details;

    public String getShipName() {
        return ShipName;
    }

    public void setShipName(String shipName) {
        ShipName = shipName;
    }

    public String getShipPhone() {
        return ShipPhone;
    }

    public void setShipPhone(String shipPhone) {
        ShipPhone = shipPhone;
    }

    public String getShipAddress() {
        return ShipAddress;
    }

    public void setShipAddress(String shipAddress) {
        ShipAddress = shipAddress;
    }

    public String getShipNote() {
        return ShipNote;
    }

    public void setShipNote(String shipNote) {
        ShipNote = shipNote;
    }

    public Integer getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        OrderStatus = orderStatus;
    }

    public List<CartResult> getDetails() {
        return Details;
    }

    public void setDetails(List<CartResult> details) {
        Details = details;
    }

    public OrderDetail(String shipName, String shipPhone, String shipAddress, String shipNote, Integer orderStatus, List<CartResult> details) {
        ShipName = shipName;
        ShipPhone = shipPhone;
        ShipAddress = shipAddress;
        ShipNote = shipNote;
        OrderStatus = orderStatus;
        Details = details;
    }
}

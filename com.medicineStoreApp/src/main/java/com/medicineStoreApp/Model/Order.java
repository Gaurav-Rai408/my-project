package com.medicineStoreApp.Model;

import java.util.List;

public class Order {
    private String orderId;
    private String userId;
    private String status;
    private double totalAmount;
    private String shippingAddress;
    private List<CartItem> orderItems; 
    private String name;
    private String date;
    private String tranID;
    
    // Constructor
    public Order(String orderId, String userId, String status, double totalAmount, String shippingAddress, List<CartItem> orderItems , String name ,  String date) {
        this.orderId = orderId;
        this.userId = userId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.shippingAddress = shippingAddress;
        this.orderItems = orderItems;
        this.name = name ;
        this.date = date ;
     
    }
    
    public Order(String orderId, String userId, String status, double totalAmount, String shippingAddress, List<CartItem> orderItems) {
        this.orderId = orderId;
        this.userId = userId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.shippingAddress = shippingAddress;
        this.orderItems = orderItems;
     
    }

    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }
    public String getTranID() {
        return tranID;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public void setTranId(String tranID) {
        this.tranID = tranID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }
    public String getName() {
        return name;
    }
    public String getOrderedTime() {
        return date;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<CartItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<CartItem> orderItems) {
        this.orderItems = orderItems;
    }
}

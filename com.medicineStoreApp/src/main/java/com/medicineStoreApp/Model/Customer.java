package com.medicineStoreApp.Model;

import java.util.Date;

public class Customer {

    private int id;            // ID of the customer (Auto-incrementing)
    private String password;   // Password of the customer
    private String phone;      // Phone number of the customer
    private String username;   // Username of the customer
    private Date creationDate; // Date when the customer was created
    private String email;      // Email address of the customer
    private String gender;     // Gender of the customer
    private String address;    // Address of the customer

    // Constructors
    public Customer() {
    }

    public Customer(int id, String password, String phone, String username, Date creationDate, String email, String gender, String address) {
        this.id = id;
        this.password = password;
        this.phone = phone;
        this.username = username;
        this.creationDate = creationDate;
        this.email = email;
        this.gender = gender;
        this.address = address;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer [id=" + id + ", username=" + username + ", email=" + email + ", phone=" + phone + "]";
    }
}

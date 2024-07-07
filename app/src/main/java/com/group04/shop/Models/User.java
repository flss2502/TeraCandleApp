package com.group04.shop.Models;

import java.util.concurrent.atomic.AtomicLong;

public class User {
    private String id;
    private String fullName;
    private Role role;
    private String address;
    private String phoneNumber;
    private String email;
    private String password;

    public User(AtomicLong userId, String fullName, String address, String phoneNumber, String email, String password) {
        this.id = String.valueOf(userId); // Convert AtomicLong to String
        this.fullName = fullName;
        this.role = Role.CUSTOMER; // Set the role to CUSTOMER by default
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Role getRole() {
        return role;
    }

    // No setter for role to enforce immutability of role once set during user creation

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}



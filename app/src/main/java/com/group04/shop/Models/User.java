package com.group04.shop.Models;

public class User {
    private String id;
    private String fullName;
    private Role role;
    private String address;
    private String phoneNumber;
    private String confirmPassword;
    private String email;
    private String password;

    public User(String id, String fullName, Role role, String address, String phoneNumber, String confirmPassword, String email, String password) {
        this.id = id;
        this.fullName = fullName;
        this.role = role;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.confirmPassword = confirmPassword;
        this.email = email;
        this.password = password;
    }
    public User(String finalUserId, String fullName, Role customer, String address, String phone, String email, String password) {

    }

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

    public void setRole(Role role) {
        this.role = role;
    }

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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

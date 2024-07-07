package com.group04.shop.Domain;

import java.util.concurrent.atomic.AtomicLong;

public class UserDomain {
    private String fullName;
    private String email;
    private String address;
    private String phoneNumber;
    private String role;
    private AtomicLong userId;
    private String password;

    public UserDomain() {
    }

    public UserDomain(AtomicLong userId, String fullName, String address, String phoneNumber,String email,  String password) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}

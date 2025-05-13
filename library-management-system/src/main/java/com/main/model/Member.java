package com.main.model;

public class Member extends User {
    // member is supended if balance is negative 
    public Member(String email, String password, String username, String name, int id, String status, double balance) {
        super(email, password, username, name, "member", id, status, balance);
    }
}

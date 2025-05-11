package com.example.model;

public class Admin extends User {
    public Admin(String email, String password, String username, String name, int id, double balance) {
        super(email, password, username, name, "admin", id, "normal", balance);
    }

    @Override
    public void setStatus(String status) {
        System.out.println("Can't set status for admin");
    }
}

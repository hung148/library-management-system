package com.main.model;

public abstract class User {
    private String email;
    private String password;
    private String username;
    private String name;
    private String type; // admin or member
    private int id; // unique
    private String status; // normal, suspended 
    private double balance;

    public User(String email, String password, String username, String name, String type, int id, String status, double balance) {
        this.email = email.trim();
        this.password = password.trim();
        this.username = username.trim();
        this.name = name.trim();
        this.type = type.trim();
        this.id = id;
        this.status = status;
        this.balance = balance;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addBalance(double amount) {
        balance += amount;
    }
}

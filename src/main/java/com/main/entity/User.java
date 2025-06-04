package com.main.entity;

public abstract class User {
    protected String email; // unique
    protected String hashpassword;
    protected String username;
    protected String name;
    protected String type; // admin or member 
    protected int id; // unique used for identify user in database
    protected String status; // normal, suspended
    protected double balance; // money 

    public abstract String getEmail();

    public abstract void setEmail(String email); 

    public abstract String getHashPassword();

    public abstract void setHashPassword(String hashpassword);

    public abstract String getUsername();

    public abstract void setUsername(String username); 

    public abstract String getName();

    public abstract void setName(String name);

    public abstract String getType(); 

    public abstract void setType(String type);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract String getStatus();

    public abstract void setStatus(String status);

    public abstract double getBalance();

    public abstract void setBalance(double balance);

    @Override
    public String toString() {
        return ("User name: " + name + "\n" +
                "User username: " + username + "\n" +
                "User password: " + hashpassword + "\n" + 
                "User email: " + email + "\n" +
                "User type: " + type + "\n" +
                "User status: " + status + "\n" + 
                "User balance: " + balance + "\n" + 
                "User id: " + id);   
    }
}

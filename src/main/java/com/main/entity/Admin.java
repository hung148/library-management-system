package com.main.entity;

public class Admin extends User {
    
    public Admin(String email, String username, String name, String hashedpassword, double balance) {
        this.name = name;
        this.username = username;
        this.hashpassword = hashedpassword;
        this.email = email;
        this.status = "normal";
        this.type = "admin";
        this.balance = balance;
    }

    public Admin() {}

    public Admin(Admin admin) {
        this.id = admin.getId();
        this.name = admin.getName();
        this.username = admin.getUsername();
        this.hashpassword = admin.getHashPassword();
        this.email = admin.getEmail();
        this.status = admin.getStatus();
        this.type = admin.getType();
        this.balance = admin.getBalance();
    }
    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        if (!(email == null) && !(email.trim().isEmpty())) {
            this.email = email;
        } else {
            System.out.println("Invalid email!");
        }
    }

    @Override
    public String getHashPassword() {
        return hashpassword;
    }

    @Override
    public void setHashPassword(String hashpassword) {
        if (!(hashpassword == null) && !(hashpassword.trim().isEmpty())) {
            this.hashpassword = hashpassword;
        } else {
            System.out.println("Invalid password!");
        }
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        if (!(username == null) && !(username.trim().isEmpty())) {
            this.username = username;
        } else {
            System.out.println("Invalid username!");
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        if (!(name == null) && !(name.trim().isEmpty())) {
            this.name = name;
        } else {
            System.out.println("Invalid name!");
        }
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        if (!(type == null) && !(type.trim().isEmpty())) {
            this.type = type;
        } else {
            System.out.println("Invalid type!");
        }
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getStatus() {
        return status;
    }
    
    @Override
    public void setStatus(String status) {
        this.status = "normal"; // admin status is always normal
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String getLibraryID() {
        return null;
    }

    @Override
    public void setLibraryID(String libraryID) {
        // do nothing
    }
}

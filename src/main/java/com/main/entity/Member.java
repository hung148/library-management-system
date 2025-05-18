package com.main.entity;

public class Member extends User {
    // member inherited from user
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
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        if (!(password == null) && !(password.trim().isEmpty())) {
            this.password = password;
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
        if (!(status == null) && !(status.trim().isEmpty())) {
            this.status = status;
        } else {
            System.out.println("Invalid status!");
        }
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void setBalance(double balance) {
        this.balance = balance;
    }
}

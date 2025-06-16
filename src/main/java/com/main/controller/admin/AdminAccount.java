package com.main.controller.admin;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import com.main.app.Main;
import com.main.services.AuthServices;
import com.main.view.LibraryApplication;
public class AdminAccount {
    @FXML
    private Label adminUserName;
    @FXML
    private Label adminEmail;
    @FXML
    private Label adminName;
    public void displayAccount(String name, String email, String userName) {
        adminUserName.setText(userName);
        adminEmail.setText(email);
        adminName.setText(name);
    }

    public void onLogout() {
        AuthServices.logout(Main.currentUser);
        // load start page
        LibraryApplication.loadStartPage();
    }
}

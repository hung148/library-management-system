package com.main.controller.admin;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AdminAccount {
    @FXML
    private Label adminUserName;
    @FXML
    private Label adminEmail;
    @FXML
    private Label adminName;
    public void displayAccount(String userName, String email, String name) {
        adminUserName.setText(userName);
        adminEmail.setText(email);
        adminName.setText(name);
    }
}

package com.main.controller.admin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;

import java.util.Date;
import java.util.ResourceBundle;

public class AdminHomepage implements Initializable {
    public Label date;
    private final Date currentDate = new Date();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        date.setText(currentDate.toString());
    }


}

package com.main.controller.member;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MemberHomepage implements Initializable {

    public Label temp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        temp.setText("""
                Display here...\s
                due date status of borrowed book \s
                overdue fee
                \s""");

    }
}

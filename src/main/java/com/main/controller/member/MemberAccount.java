package com.main.controller.member;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MemberAccount {
    @FXML
    private Label memberName;
    @FXML
    private Label memberUserName;
    @FXML
    private Label memberEmail;

    public void setMemberData(String name, String email, String userName) {
        memberName.setText(userName);
        memberUserName.setText(email);
        memberEmail.setText(name);
    }
}

package com.main.controller.member;

import com.main.services.AuthServices;
import com.main.view.LibraryApplication;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import static com.main.controller.MemberLoginController.member;

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

    public void onLogout() {
        AuthServices.logout(member);
        // load start page
        LibraryApplication.loadStartPage();
    }
}   

package com.main.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class MemberController {
    @FXML
    private Pane userDisplay;
    public void setUserDisplay(Node node) {
        userDisplay.getChildren().setAll(node);
    }

    @FXML
    private void clickBook() {
        MemberNavigator.navigateUser(MemberNavigator.memberBooks);
    }
    @FXML
    private void clickAccount() {
        MemberNavigator.navigateUser(MemberNavigator.memberAccount);
    }
}

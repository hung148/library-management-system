package com.main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class UserController {
    @FXML
    private Pane userDisplay;
    public void setUserDisplay(Node node) {
        userDisplay.getChildren().setAll(node);
    }

    @FXML
    private void clickBook() {
        UserNavigator.navigateUser(UserNavigator.userBooks);
    }
    @FXML
    private void clickAccount() {
        UserNavigator.navigateUser(UserNavigator.userAccount);
    }
}

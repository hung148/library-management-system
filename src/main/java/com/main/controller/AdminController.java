package com.main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Node;

import javafx.scene.layout.Pane;


public class AdminController {
    @FXML
    private Pane adminDisplay;


    public void setAdminMenu(Node node) {
       adminDisplay.getChildren().setAll(node);
    }
    @FXML
    private void clickAccount(ActionEvent event) {
        //click this open a new scene of admin-account.fxml
        AdminNavigator.navigateAdmin(AdminNavigator.adminAccount);
    }
    @FXML
    private void clickBook(ActionEvent event) {
        //click this --> open a new scene of admin-book.fxml
        AdminNavigator.navigateAdmin(AdminNavigator.adminBooks);

    }
    @FXML
    private void clickMembers(ActionEvent event) {
        //click this --> open a new scene of admin-members.fxml
        AdminNavigator.navigateAdmin(AdminNavigator.adminMembers);
    }
}

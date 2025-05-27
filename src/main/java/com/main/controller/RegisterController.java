package com.main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import com.main.view.LibraryApplication;

public class RegisterController {

    @FXML
    private void onRegisterClick() {
    //need to add code here to create new member
    }


    //Cancel click goes back to Member login page
    @FXML
    private void onCancelClick(ActionEvent event) throws IOException {
        //fxml files are all under LibraryApplication.class?
        FXMLLoader loader = new FXMLLoader(LibraryApplication.class.getResource("login-page.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        LibraryApplication.addCSS(scene);
        stage.setScene(scene);
        stage.show();

    }
}

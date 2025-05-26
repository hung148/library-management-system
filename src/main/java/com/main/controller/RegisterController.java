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

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private void onRegisterClick() {

    }
    @FXML
    private void onCancelClick(ActionEvent event) throws IOException {
        //fxml files are all under LibraryApplication.class?
        FXMLLoader loader = new FXMLLoader(LibraryApplication.class.getResource("login-page.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }
}

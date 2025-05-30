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
import java.util.Objects;

import com.main.view.LibraryApplication;

public class RegisterController {

    @FXML
    public void onRegisterClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("register-page.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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

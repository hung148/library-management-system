package com.main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import com.main.view.LibraryApplication;

public class RegisterController {

    private FXMLLoader loader;
    @FXML
    private void onRegisterClick() {

    }
    @FXML
    private void onCancelClick() throws IOException {
        //fxml files are all under LibraryApplication.class?
        loader = new FXMLLoader(LibraryApplication.class.getResource("login-page.fxml"));
        LibraryApplication.stage.setScene(new Scene(loader.load()));
        LibraryApplication.stage.show();

    }
}

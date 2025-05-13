package com.bookkeeper.libraryui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {
    @FXML
    private Button cancel;

    @FXML
    private Button register;


    @FXML
    private void onRegisterClick() {

    }
    @FXML
    private void onCancelClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-page.fxml"));
        Stage stage = (Stage) this.cancel.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }
}

package com.main.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import com.main.view.LibraryApplication;

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
        FXMLLoader login_loader =  new FXMLLoader(LibraryApplication.class.getResource("login-page.fxml"));
        Stage stage = (Stage) this.cancel.getScene().getWindow();
        stage.setScene(new Scene(login_loader.load()));
        stage.show(); 
    }
}

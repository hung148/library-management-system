/* Example of opening a second scene (window) on button click on the first scene
---this is method in the controller class of the first scene----

    public void openScene2(ActionEvent event) throws IOException
    // Load the FXML file for the second scene
    FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene2.fxml"));
    // Create a new stage for the second scene
    Stage stage = new Stage();
    stage.setScene(new Scene(loader.load()));
    // Set the title for the second scene
    stage.setTitle("Scene 2");
    // Show the second scene
    stage.show();

    To connect properties and methods from this class to the fxml file, add these to the Label in the fxml
    <Button fx:id="btnOpenScene2" text="Open Scene 2" onAction="#openScene2"/>

* */

package com.main.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import com.main.view.LibraryApplication;

//2 event buttons: log in and register
public class LoginController {
    private Button login;
    @FXML
    private Button registerPage;

    @FXML
    private void onRegisterClick(ActionEvent event) {
        try {
            showRegisterPage();
        } catch (IOException e) {
            throw new RuntimeException("Page does not exist?");
        }
    }

    private void showRegisterPage() throws IOException {
        FXMLLoader register_loader = new FXMLLoader(LibraryApplication.class.getResource("register-page.fxml"));
        Stage stage = (Stage) registerPage.getScene().getWindow();
        stage.setScene(new Scene(register_loader.load()));
        stage.show();
    }
}

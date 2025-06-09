package com.main.controller;

import com.main.respository.LibraryDAO;
import com.main.entity.Member;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import com.main.view.LibraryApplication;

public class RegisterController {

    public Button register;

    @FXML
    public TextField lastNameField;

    @FXML
    public TextField libraryIdField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    private final LibraryDAO libraryDAO;

    public RegisterController() {
        libraryDAO = new LibraryDAO();
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String name = nameField.getText();
        String email = emailField.getText();

        if (username.isEmpty() || password.isEmpty() || name.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill in all fields.");
            return;
        }

        try {
            libraryDAO.addMember(email, password, username, name, 0.0);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Member registered successfully.");

            // Optional: Clear the form
            usernameField.clear();
            passwordField.clear();
            nameField.clear();
            emailField.clear();
            lastNameField.clear();
            libraryIdField.clear();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Could not register member.");
        }
    }

    // Cancel click goes back to Member login page
    @FXML
    private void onCancelClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(LibraryApplication.class.getResource("member-login.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        LibraryApplication.addCSS(scene);
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

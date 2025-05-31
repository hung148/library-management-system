package com.main.controller;

import com.main.entity.Book;
import com.main.entity.Member;
import com.main.entity.User;
import com.main.respository.LibraryDAO;
import com.main.view.LibraryApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.net.URL;
import java.net.URL;


import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

public class MemberLoginController {

    @FXML
    private TextField inputUsername;
    @FXML
    private PasswordField inputPassword;
    @FXML
    private Label alertLogin;

    private Member member;
    private Stage stage;
    private Scene scene;
    public static User user;
    public static final ObservableList<Book> books = FXCollections.observableArrayList();

    @FXML
    private void onSignInClick(ActionEvent event) {

        String username = inputUsername.getText();
        String password = inputPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            alertLogin.setText("Please enter both username and password.");
            return;
        }

        member = LibraryDAO.getMemberByUsername(username);
        if (member != null && member.getPassword().equals(password)) {
            alertLogin.setText("Login successful!");
            try {
                URL fxmlPath = getClass().getResource("/com/main/view/member-page.fxml");
                if (fxmlPath == null) {
                    alertLogin.setText("FXML file not found.");
                    return;
                }

                FXMLLoader loader = new FXMLLoader(fxmlPath);
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                LibraryApplication.addCSS(scene);
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
                alertLogin.setText("Failed to load member page.");
            }
        } else {
            alertLogin.setText("Invalid username or password.");
        }
    }

    // Click Back to go back to Start-page with the 2 options
    @FXML
    private void backToStart(MouseEvent event) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = LibraryApplication.loadStartPage();
        LibraryApplication.addCSS(scene);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onRegisterClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/main/view/register-page.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
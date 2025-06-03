package com.main.controller;

import com.main.entity.Admin;
import com.main.entity.Book;
import com.main.entity.User;
import com.main.respository.LibraryDAO;
import com.main.services.AuthServices;
import com.main.view.LibraryApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collections;

public class AdminLoginController {
    @FXML
    private TextField inputUsername;
    @FXML
    private PasswordField inputPassword;
    @FXML
    private Label alertLogin;

    private Admin admin;
    private Stage stage;
    private Scene scene;
    public static User user;
    public static final ObservableList<Book> books = FXCollections.observableArrayList();

    @FXML
    private void onSignInClick(ActionEvent event) throws IOException {
        String username = inputUsername.getText();
        String password = inputPassword.getText();
        admin = loadAdmin(username, password);

        if(admin != null) {
            user = admin;
            Collections.addAll(books, LibraryDAO.bookList());
            FXMLLoader loader = new FXMLLoader(LibraryApplication.class.getResource("admin-page.fxml"));
            Parent root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            LibraryApplication.addCSS(scene);
            stage.setScene(scene);
            stage.show();
        }
        else {
            alertLogin.setText("No Admin Found");
        }
    }

    private Admin loadAdmin(String username, String password) {
        admin = LibraryDAO.getAdminByUsername(username);
        if(admin != null) {
            if(AuthServices.checkPassword(password, admin.getPassword())) {
                return admin;
            }
            else {
                alertLogin.setText("Wrong Password");
            }
        }
        return null;
    }


    // click Back to go back to Start-page with the 2 options
    @FXML
    private void backToStart(MouseEvent event) throws IOException {
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = LibraryApplication.loadStartPage();
        LibraryApplication.addCSS(scene);
        stage.setScene(scene);
        stage.show();
    }
}

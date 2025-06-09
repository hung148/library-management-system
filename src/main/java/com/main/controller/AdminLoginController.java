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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.Collections;

public class AdminLoginController {
    @FXML
    private TextField inputUsername;
    @FXML
    private PasswordField inputPassword;
    @FXML
    private Label alertLogin;
    @FXML
    private AnchorPane rootPane;

    private Admin admin;
    public static User user;
    public static final ObservableList<Book> books = FXCollections.observableArrayList();

    @FXML
    private void onSignInClick(ActionEvent event) {
        String username = inputUsername.getText();
        String password = inputPassword.getText();
        admin = loadAdmin(username, password);

        if(admin != null) {
            user = admin;
            Collections.addAll(books, LibraryDAO.bookList());
            LibraryApplication.loadAdminPage();
        }
        else {
            alertLogin.setText("No Admin Found");
        }
    }

    private Admin loadAdmin(String username, String password) {
        return AuthServices.adminLogin(username, password);
    }


    // click Back to go back to Start-page with the 2 options
    @FXML
    private void backToStart(MouseEvent event) {
        LibraryApplication.loadStartPage();
    }
}

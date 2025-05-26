package com.main.controller;
import com.main.view.LibraryApplication;

import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;


import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import javax.swing.text.PlainDocument;
import java.io.IOException;


public class AdminController  {

    private FXMLLoader fxmlLoader;
    @FXML
    private BorderPane adminPage;


    @FXML
    private Label adminUserName;
    @FXML
    private Label adminEmail;
    @FXML
    private Label adminName;


    private void setAdmin() {
//        Thread thread = new Thread(() -> {
//            adminUserName.setText(LoginController.staticAdmin.getUsername());
//            adminEmail.setText(LoginController.staticAdmin.getEmail());
//            adminName.setText(LoginController.staticAdmin.getName());
//        });
//        thread.start();
        Platform.runLater(() -> {
            adminUserName.setText(LoginController.staticAdmin.getUsername());
            adminEmail.setText(LoginController.staticAdmin.getEmail());
            adminName.setText(LoginController.staticAdmin.getName());
        });
    }
//    @Override
////    public void initialize(URL location, ResourceBundle resources) {
////        setAdmin();
////
////    }


    @FXML
    private void clickAccount() throws IOException {
        //click this open a new scene of admin-account.fxml
//        loadAccount(LoginController.admin.getId();
        Thread thread = new Thread(() -> { new Runnable() {
            public void run() {
                adminUserName.setText(LoginController.staticAdmin.getUsername());
                adminEmail.setText(LoginController.staticAdmin.getEmail());
                adminName.setText(LoginController.staticAdmin.getName());
            }
        };
        });
        fxmlLoader = new FXMLLoader(LibraryApplication.class.getResource("admin/admin-account.fxml"));
            try {
                adminPage.setCenter(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
    @FXML
    private void clickBook() throws IOException {
        //click this --> open a new scene of admin-book.fxml
        fxmlLoader = new FXMLLoader(LibraryApplication.class.getResource("admin/admin-book.fxml"));
        adminPage.setCenter(fxmlLoader.load());

    }
    @FXML
    private void clickMembers() throws IOException {
        //click this --> open a new scene of admin-members.fxml
        fxmlLoader = new FXMLLoader(LibraryApplication.class.getResource("admin/admin-members.fxml"));
        adminPage.setCenter(fxmlLoader.load());
    }

//    private void loadAccount(int id) {
//        adminUserName.textProperty().bind(admin.getName());
//
//
//    }

}

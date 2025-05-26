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

package com.main.controller;


import com.main.entity.Admin;
import com.main.entity.Book;
import com.main.entity.User;

import com.main.respository.LibraryDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;


import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import com.main.view.LibraryApplication;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class LoginController {
    @FXML
    public Label alertLogin;
    @FXML
    private PasswordField inputPassword;
    @FXML
    private TextField inputUsername;
    private Admin admin;
    private Parent root;
    private Scene scene;
    private Stage stage;
    public static User user;
    public static final ObservableList<Book> books = FXCollections.observableArrayList();

    // method to open up Register page for new member
    @FXML
    private void onRegisterClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(LibraryApplication.class.getResource("register-page.fxml"));
        root = loader.load();
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    //open either admin-page or member-page accordingly
    @FXML
    private void onSignInClick(ActionEvent event) throws IOException {
        String username = inputUsername.getText();
        String password = inputPassword.getText();
        admin = loadAdmin(username, password);

        if(admin != null) {
            user = admin;
            Collections.addAll(books, LibraryDAO.bookList());
            FXMLLoader loader = new FXMLLoader(LibraryApplication.class.getResource("admin-page.fxml"));
            root = loader.load();
            stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
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
            if(admin.getPassword().equals(password)) {
                return admin;
            }
            else {
                alertLogin.setText("Wrong Password");
            }
        }
        return null;
    }

}

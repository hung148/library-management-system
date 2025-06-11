package com.main.controller;
import com.main.controller.admin.AdminAccount;

import com.main.controller.admin.AdminBook;
import com.main.view.LibraryApplication;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.main.controller.AdminLoginController.user;


public class AdminController implements Initializable {

    @FXML
    private BorderPane adminPane;
    @FXML

    private final FXMLLoader adminAccountLoader = new FXMLLoader(LibraryApplication.class.getResource("admin/admin-account.fxml"));
    private final FXMLLoader adminBookLoader = new FXMLLoader(LibraryApplication.class.getResource("admin/admin-book.fxml"));
    private final FXMLLoader adminMembersLoader = new FXMLLoader(LibraryApplication.class.getResource("admin/admin-members.fxml"));
    private final FXMLLoader adminHomeLoader = new FXMLLoader(LibraryApplication.class.getResource("admin/admin-home.fxml"));

    private Parent adminHomeRoot;
    private Parent adminAccountRoot;
    private Parent adminBookRoot;
    private Parent adminMembersRoot;

    private ObjectProperty<Node> centerDisplay;


    //click this open a new scene of admin-account.fxml
    @FXML
    private void clickAccount() throws IOException {
        centerDisplay.set(adminAccountRoot);
        AdminAccount account = adminAccountLoader.getController();
        account.displayAccount(user.getName(),user.getEmail(),user.getUsername());
    }


    //click this --> open a new scene of admin-book.fxml
    @FXML
    private void clickBook() throws IOException {
        centerDisplay.set(adminBookRoot);
    }

    //click this --> open a new scene of admin-members.fxml
    @FXML
    private void clickMembers() throws IOException {
        centerDisplay.set(adminMembersRoot);
    }
    @FXML
    private void clickHomepage()  throws IOException {
        centerDisplay.set(adminHomeRoot);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            adminHomeRoot = adminHomeLoader.load();
            adminAccountRoot = adminAccountLoader.load();
            adminBookRoot = adminBookLoader.load();
            adminMembersRoot = adminMembersLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        centerDisplay = new SimpleObjectProperty<>(adminHomeRoot);
        adminPane.centerProperty().bind(centerDisplay);

    }
}

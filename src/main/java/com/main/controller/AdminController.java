package com.main.controller;


import com.main.view.LibraryApplication;
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
import java.util.ResourceBundle;


public class AdminController implements Initializable {
    @FXML
    private BorderPane adminPane;
    @FXML
    public static final FXMLLoader adminAccountLoader = new FXMLLoader(LibraryApplication.class.getResource("admin/admin-account.fxml"));
    @FXML
    public static final FXMLLoader adminBookLoader = new FXMLLoader(LibraryApplication.class.getResource("admin/admin-book.fxml"));
    @FXML
    public static final FXMLLoader adminMembersLoader = new FXMLLoader(LibraryApplication.class.getResource("admin/admin-members.fxml"));
    @FXML
    private final FXMLLoader adminHomeLoader = new FXMLLoader(LibraryApplication.class.getResource("admin/admin-home.fxml"));

    private Parent adminHomeRoot;
    private Parent adminAccountRoot;
    private Parent adminBookRoot;
    private Parent adminMembersRoot;

    private ObjectProperty<Node> centerDisplay;

    @FXML
    private void clickAccount() throws IOException {
        centerDisplay.set(adminAccountRoot);
    }
    @FXML
    private void clickBook() throws IOException {
        centerDisplay.set(adminBookRoot);
    }
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
        //adminHome shows up once adminPage opens
        centerDisplay = new SimpleObjectProperty<>(adminHomeRoot);
        adminPane.centerProperty().bind(centerDisplay);

        System.out.println("initializer from admin controller: " + Thread.currentThread().getName());

    }

}

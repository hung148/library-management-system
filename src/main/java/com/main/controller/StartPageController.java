package com.main.controller;

import com.main.view.LibraryApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class StartPageController {
    public Label member;
    public Label admin;
    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private Rectangle memberSignin;
    @FXML
    private Rectangle adminSignin;


    //click Member to login page for Member with register
    @FXML
    private void clickMember(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(LibraryApplication.class.getResource("/com/main/view/member-login.fxml"));
        root = loader.load();
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //click Admin to login as Admin
    @FXML
    private void clickAdmin(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(LibraryApplication.class.getResource("admin-login.fxml"));
        root = loader.load();
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showMember(MouseEvent event) {
        member.translateXProperty().bind(memberSignin.translateXProperty());
    }

    public void showAdmin(MouseEvent event) {
    }
}

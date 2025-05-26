package com.main.controller;
import com.main.controller.admin.AdminAccount;
import com.main.entity.Admin;
import com.main.entity.User;
import com.main.view.LibraryApplication;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import static com.main.controller.LoginController.user;


public class AdminController  {

    @FXML
    private BorderPane adminPage;

    @FXML
    private void clickAccount(ActionEvent event) throws IOException {
        //click this open a new scene of admin-account.fxml
        FXMLLoader loader = new FXMLLoader(LibraryApplication.class.getResource("admin/admin-account.fxml"));
        adminPage.setCenter(loader.load());
        AdminAccount account = loader.getController();
        account.displayAccount(user.getName(),user.getUsername(),user.getEmail());

    }
    @FXML
    private void clickBook() throws IOException {
        //click this --> open a new scene of admin-book.fxml
        FXMLLoader fxmlLoader = new FXMLLoader(LibraryApplication.class.getResource("admin/admin-book.fxml"));
        adminPage.setCenter(fxmlLoader.load());

    }
    @FXML
    private void clickMembers() throws IOException {
        //click this --> open a new scene of admin-members.fxml
        FXMLLoader fxmlLoader = new FXMLLoader(LibraryApplication.class.getResource("admin/admin-members.fxml"));
        adminPage.setCenter(fxmlLoader.load());
    }


}

package com.main.controller;
import com.main.controller.admin.AdminAccount;

import com.main.controller.admin.AdminBook;
import com.main.entity.Book;
import com.main.respository.LibraryDAO;
import com.main.view.LibraryApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static com.main.controller.LoginController.user;


public class AdminController  {

    @FXML
    private BorderPane adminPage;


    ////click this open a new scene of admin-account.fxml
    @FXML
    private void clickAccount() throws IOException {
        FXMLLoader loader = new FXMLLoader(LibraryApplication.class.getResource("admin/admin-account.fxml"));
        adminPage.setCenter(loader.load());
        AdminAccount account = loader.getController();
        account.displayAccount(user.getName(),user.getEmail(),user.getUsername());

    }
    //click this --> open a new scene of admin-book.fxml
    @FXML
    private void clickBook() throws IOException {
        FXMLLoader loader = new FXMLLoader(LibraryApplication.class.getResource("admin/admin-book.fxml"));
        adminPage.setCenter(loader.load());
        AdminBook book = loader.getController();
        book.displayBooks(LoginController.books);

    }

    //click this --> open a new scene of admin-members.fxml
    @FXML
    private void clickMembers() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LibraryApplication.class.getResource("admin/admin-members.fxml"));
        adminPage.setCenter(fxmlLoader.load());
    }

}

package com.main.controller;

import com.main.view.LibraryApplication;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class UserNavigator {
    public static final String userMenu = "user-page.fxml";
    public static final String userBooks = "user/user-book.fxml";
    public static final String userAccount = "user/user-account.fxml";

    private static UserController userController;

    public static void setUserController(UserController userController) {
        UserNavigator.userController = userController;
    }

    public static void navigateUser(String fxml)  {
        try {
            userController.setUserDisplay(FXMLLoader.load(LibraryApplication.class.getResource(fxml)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
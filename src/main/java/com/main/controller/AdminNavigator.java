package com.main.controller;

import com.main.view.LibraryApplication;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class AdminNavigator {
    public static final String adminMenu = "admin-page.fxml";
    public static final String adminBooks = "admin/admin-book.fxml";
    public static final String adminMembers = "admin/admin-members.fxml";
    public static final String adminAccount = "admin/admin-account.fxml";

    private static AdminController adminController;

    public static void setAdminController(AdminController adminController) {
        AdminNavigator.adminController = adminController;
    }
    public static void navigateAdmin(String fxml)  {
        try {
            adminController.setAdminMenu(FXMLLoader.load(LibraryApplication.class.getResource(fxml)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

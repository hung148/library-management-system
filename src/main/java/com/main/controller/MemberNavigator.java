package com.main.controller;

import com.main.view.LibraryApplication;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class MemberNavigator {
    public static final String memberMenu = "member-page.fxml";
    public static final String memberBooks = "member/member-book.fxml";
    public static final String memberAccount = "member/member-account.fxml";

    private static MemberController memberController;

    public static void setUserController(MemberController memberController) {
        MemberNavigator.memberController = memberController;
    }

    public static void navigateUser(String fxml)  {
        try {
            memberController.setUserDisplay(FXMLLoader.load(LibraryApplication.class.getResource(fxml)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
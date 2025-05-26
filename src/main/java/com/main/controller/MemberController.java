package com.main.controller;

import com.main.view.LibraryApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;


import java.io.IOException;

public class MemberController {
    public BorderPane memberPage;
    private FXMLLoader loader;

    @FXML
    private void clickBook() throws IOException {
        loader = new FXMLLoader(LibraryApplication.class.getResource("member/member-book.fxml"));
        memberPage.setCenter(loader.load());
    }
    @FXML
    private void clickAccount() throws IOException {
        loader = new FXMLLoader(LibraryApplication.class.getResource("member/member-account.fxml"));
        memberPage.setCenter(loader.load());
    }
}

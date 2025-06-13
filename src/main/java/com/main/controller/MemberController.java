package com.main.controller;

import com.main.controller.member.MemberAccount;
import com.main.entity.Book;
import com.main.respository.LibraryDAO;
import com.main.view.LibraryApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;


import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

import static com.main.controller.MemberLoginController.member;

public class MemberController implements Initializable {
    public BorderPane memberPage;
    private FXMLLoader loader;
    public static ObservableList<Book> currentBorrowed = FXCollections.observableArrayList();
    public static final ObservableList<Book> pastBorrowed = FXCollections.observableArrayList();

    @FXML
    private void clickBook() throws IOException {
        if(!currentBorrowed.isEmpty()){
            currentBorrowed = FXCollections.observableArrayList();
        }
        loader = new FXMLLoader(LibraryApplication.class.getResource("member/member-book.fxml"));
        memberPage.setCenter(loader.load());
    }
    @FXML
    private void clickAccount() throws IOException {
        loader = new FXMLLoader(LibraryApplication.class.getResource("member/member-account.fxml"));
        memberPage.setCenter(loader.load());
        MemberAccount account = loader.getController();
        account.setMemberData(member.getName(), member.getEmail(), member.getUsername());
    }

    public void setHomePage() {
        loader = new FXMLLoader(LibraryApplication.class.getResource("member/member-home.fxml"));
        try {
            memberPage.setCenter(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setHomePage();

    }

    @FXML
    private void clickHomepage(ActionEvent event) throws IOException {
        loader = new FXMLLoader(LibraryApplication.class.getResource("member/member-home.fxml"));
        memberPage.setCenter(loader.load());
    }
}

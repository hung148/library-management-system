package com.main.controller.admin;

import com.main.entity.Book;
import com.main.view.LibraryApplication
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;


//display table of books with author (last name, first name), ISBN, genres, and unique library ID with add and remove functions
public class AdminBook {
    public TableColumn<Book, Integer> totalCol;
    public TableColumn<Book, Integer> availCol;
    public TableColumn<Book,String> publishCol;
    @FXML
    private TableView<Book> viewBooks ;
    @FXML
    private TableColumn<Book,String> titleCol;
    @FXML
    private TableColumn<Book,String> authorCol;
    @FXML
    private TableColumn<Book,String> isbnCol;

    private final FXMLLoader popupLoader = new FXMLLoader(LibraryApplication.class.getResource("admin/add-book-popup.fxml"));


    public void displayBooks(ObservableList<Book> books) {
        //PropertyValueFactory(*name*) calls getter of Book so getter methods need to match the parameter *name*
        titleCol.setCellValueFactory(new PropertyValueFactory<>("_title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("_author"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("_ISBN"));
        publishCol.setCellValueFactory(new PropertyValueFactory<>("_publisher"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("TotalCopies"));
        availCol.setCellValueFactory(new PropertyValueFactory<>("AvailableCopies"));
        viewBooks.getItems().addAll(books);

    }

    //pop-up windows to add book
    public void clickAdd(ActionEvent event) throws IOException {
        Stage addBookPopUp = new Stage();
        //need to finish the task in this popup before going back to main Stage
        addBookPopUp.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(popupLoader.load());
        addBookPopUp.setScene(scene);
        addBookPopUp.setTitle("Adding New Book");
        addBookPopUp.setResizable(false);
        addBookPopUp.showAndWait();
    }

    //perhaps "Remove" function can be inside Edit?
    public void clickRemove(ActionEvent event) {
        
    }


    public void clickEdit(ActionEvent event) {
    }
}

package com.main.controller.admin;

import com.main.entity.Book;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;



//display table of books with author (last name, first name), ISBN, genres, and unique library ID with add and remove functions
public class AdminBook {
    @FXML
    private TableView<Book> viewBooks ;
    @FXML
    private TableColumn<Book,String> titleCol;
    @FXML
    private TableColumn<Book,String> authorCol;
    @FXML
    private TableColumn<Book,String> isbnCol;


    public void displayBooks(ObservableList<Book> books) {
        //PropertyValueFactory(*name*) calls getter of Book so getter methods need to match the parameter *name*
        titleCol.setCellValueFactory(new PropertyValueFactory<>("_title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("_author"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("_ISBN"));
        viewBooks.getItems().addAll(books);

    }
}

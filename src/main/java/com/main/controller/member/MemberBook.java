package com.main.controller.member;

import com.main.entity.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MemberBook {
    //search book and list view for book result
    @FXML
    private TextField searchBook;
    @FXML
    private ListView<Book> resultBook;


    //First table: Current Borrow
    @FXML
    private TableView<Book> currentBooks;
    @FXML
    private TableColumn<Book,String> authorCol;
    @FXML
    private TableColumn<Book,String> isbnCol;
    @FXML
    private TableColumn<Book,String> titleCol;


    //Second table: Past Borrow
    @FXML
    private TableView<Book> pastBooks;
    @FXML
    private TableColumn<Book,String> isbnCol1;
    @FXML
    private TableColumn<Book,String> titleCol1;
    @FXML
    private TableColumn<Book,String> authorCol1;

    //type a book name/author/isbn in searchBook and then click Search button then search results will show in resultBook
    @FXML
    private void onClickSearch(ActionEvent event) {
    }

    //select book in resultBook and then click Borrow button then book will show up in currentBook
    @FXML
    private void onClickBorrow(ActionEvent event) {
    }

    //select one book currentBook then click Return button then book will be removed from currentBook and show up in pastBooks
    @FXML
    private void onClickReturn(ActionEvent event) {
    }


    //method to display Current Borrow --- TableView currentBooks


    //method to display Past Borrow --- TableView pastBooks
}

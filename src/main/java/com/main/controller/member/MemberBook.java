package com.main.controller.member;

import com.main.entity.Book;
import com.main.respository.LibraryDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.beans.property.SimpleStringProperty;

import static com.main.services.LibraryServices.searchBook;


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
    @FXML
    private TableColumn<Book,String> publisherCol;

    //Second table: Past Borrow
    @FXML
    private TableView<Book> pastBooks;
    @FXML
    private TableColumn<Book,String> isbnCol1;
    @FXML
    private TableColumn<Book,String> titleCol1;
    @FXML
    private TableColumn<Book,String> authorCol1;
    @FXML
    private TableColumn<Book,String> publisherCol1;

    // Book lists
    private ObservableList<Book> allBooks = FXCollections.observableArrayList();
    private ObservableList<Book> currentBorrowed = FXCollections.observableArrayList();
    private ObservableList<Book> pastBorrowed = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Setup column bindings
        titleCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get_title()));
        isbnCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get_ISBN()));
        authorCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get_author()));
        publisherCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get_publisher()));

        titleCol1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get_title()));
        isbnCol1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get_ISBN()));
        authorCol1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get_author()));
        publisherCol1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get_publisher()));

        // Sample data
        allBooks.addAll(
                new Book("1234567890", "Java Basics", "John Doe", "Tech Press", 3),
                new Book("9876543210", "Effective Java", "Joshua Bloch", "Pearson", 5),
                new Book("5555555555", "Clean Code", "Robert Martin", "Prentice Hall", 4)
        );

        // Set initial table contents
        currentBooks.setItems(currentBorrowed);
        pastBooks.setItems(pastBorrowed);
    }

    //type a book name/author/isbn in searchBook and then click Search button then search results will show in resultBook
    @FXML
    private void onClickSearch(ActionEvent event) {
        String keyword = searchBook.getText().trim();

        if (keyword.isEmpty()) {
            resultBook.setItems(FXCollections.observableArrayList(allBooks));
            return;
        }

        try {
            Book[] bookList = searchBook(keyword, keyword, keyword, keyword);
            ObservableList<Book> results = bookList != null && bookList.length > 0
                    ? FXCollections.observableArrayList(bookList)
                    : FXCollections.observableArrayList();

            resultBook.setItems(results);

            // Fallback to local data if no results from DAO
            if (results.isEmpty()) {
                ObservableList<Book> localResults = allBooks.filtered(book ->
                        book.get_title().toLowerCase().contains(keyword.toLowerCase()) ||
                                book.get_author().toLowerCase().contains(keyword.toLowerCase()) ||
                                book.get_ISBN().toLowerCase().contains(keyword.toLowerCase()));
                resultBook.setItems(localResults);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultBook.setItems(FXCollections.observableArrayList());
        }
    }

    //select book in resultBook and then click Borrow button then book will show up in currentBook
    @FXML
    private void onClickBorrow(ActionEvent event) {
        Book selected = resultBook.getSelectionModel().getSelectedItem();
        if (selected != null && !currentBorrowed.contains(selected)) {
            currentBorrowed.add(selected);
            resultBook.getItems().remove(selected);
            displayCurrentBorrow();
        }
    }

    //select one book currentBook then click Return button then book will be removed from currentBook and show up in pastBooks
    @FXML
    private void onClickReturn(ActionEvent event) {
        Book selected = currentBooks.getSelectionModel().getSelectedItem();
        if (selected != null) {
            currentBorrowed.remove(selected);
            pastBorrowed.add(selected);
            displayCurrentBorrow();
            displayPastBorrow();
        }
    }

    //method to display Current Borrow --- TableView currentBooks
    private void displayCurrentBorrow() {
        currentBooks.setItems(null);
        currentBooks.setItems(currentBorrowed);
    }

    //method to display Past Borrow --- TableView pastBooks
    private void displayPastBorrow() {
        pastBooks.setItems(null);
        pastBooks.setItems(pastBorrowed);
    }


}

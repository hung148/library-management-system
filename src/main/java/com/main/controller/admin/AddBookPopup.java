package com.main.controller.admin;

import com.main.controller.AdminController;
import com.main.entity.Book;
import com.main.model.BookListModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddBookPopup {
    @FXML
    private TextField addTitle;
    @FXML
    private TextField addAuthor;
    @FXML
    private TextField addISBN;
    @FXML
    private TextField addPublisher;
    @FXML
    private TextField addTotal;
    private final AdminBook adminBook = AdminController.adminBookLoader.getController();
    private BookListModel bookList;

    public void setBookListModel(BookListModel bookList) {
        this.bookList = bookList;
    }

    //need to add new book into the current library....
    private Book newBook() {
        Book book = new Book();
        book.set_title(addTitle.getText());
        book.set_author(addAuthor.getText());
        book.set_ISBN(addISBN.getText());
        book.set_publisher(addPublisher.getText());
        int copies = Integer.parseInt(addTotal.getText());
        book.setTotalCopies(copies);
        book.setAvailableCopies(copies);
        return book;
    }

    @FXML
    private void onAddBookClick(ActionEvent event) {
        bookList.getList().add(newBook());
        bookList.updateList(newBook());
        adminBook.getViewBooks().setItems(bookList.getList());
        adminBook.getAddBookStage().close();
    }

    @FXML
    private void onCancelClick(ActionEvent event) {
        adminBook.getAddBookStage().close();
    }
}



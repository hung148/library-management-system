package com.main.model;

import com.main.entity.Book;
import com.main.respository.LibraryDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collections;

/* This class is used as an information carrier from LibraryDAO to the controller classes
*  to help:
*   + avoid direct access to the LibraryDAO database
*   + one LibraryDAO is generated when a user(admin/member) is logged in
*   + generate a new LibraryDAO only when a book is added or removed
*   +
* */
public class BookListModel {
    private final ObservableList<Book> list;

    public BookListModel() {
        this.list = FXCollections.observableArrayList();
        Collections.addAll(this.list, LibraryDAO.bookList());
    }

    public ObservableList<Book> getList() {
        return this.list;
    }
    public void updateList(Book book) {
        LibraryDAO.addBook(book.get_ISBN(),book.get_title(),book.get_author(),book.get_publisher(),book.getTotalCopies());
    }
}

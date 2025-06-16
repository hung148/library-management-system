package com.main.controller.admin;

import java.net.URL;
import java.util.ResourceBundle;

import com.main.controller.AdminController;
import com.main.entity.Book;
import com.main.model.BookListModel;
import com.main.respository.LibraryDAO;
import com.main.view.LibraryApplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditBookPopup implements Initializable {
    private Book book;

    @FXML private TextField isbnField;
    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private TextField publisherField;
    @FXML private Spinner<Integer> totalCopiesSpinner;
    @FXML private Spinner<Integer> availableCopiesSpinner;

    private final AdminBook adminBook = AdminController.adminBookLoader.getController();
    private BookListModel bookListModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set value factories for both spinners
        totalCopiesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 1));
        availableCopiesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 1));
    }

    public void setBook(Book book) {
        this.book = book;

        // populate fields with existing data
        isbnField.setText(book.get_ISBN());
        titleField.setText(book.get_title());
        authorField.setText(book.get_author());
        publisherField.setText(book.get_publisher());
        totalCopiesSpinner.getValueFactory().setValue(book.getTotalCopies());
        availableCopiesSpinner.getValueFactory().setValue(book.getAvailableCopies());
    }

    public void setBookListModel(BookListModel model) {
        this.bookListModel = model;
    }

    @FXML
    private void saveChanges(ActionEvent event) {
        // update book data from fields
        String originalISBN = book.get_ISBN();
        System.out.println(titleField.getText());
        book.set_ISBN(isbnField.getText());
        book.set_title(titleField.getText());
        book.set_author(authorField.getText());
        book.set_publisher(publisherField.getText());
        book.setTotalCopies(totalCopiesSpinner.getValue());
        book.setAvailableCopies(availableCopiesSpinner.getValue());

        // persist changes (e.g. to database if needed)
        LibraryDAO.updateBook(originalISBN, book);

        LibraryApplication.showAlert(AlertType.INFORMATION, "Success", "Book updated successfully.");
        
        // close the popup
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        // update view 
        bookListModel = new BookListModel();
        adminBook.getViewBooks().setItems(bookListModel.getList());
    }

    @FXML
    private void onCancelClick(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
}

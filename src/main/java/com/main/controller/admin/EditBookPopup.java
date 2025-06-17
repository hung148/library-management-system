package com.main.controller.admin;

import com.main.controller.AdminController;
import com.main.entity.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class EditBookPopup {
    private final AdminBook adminBook = AdminController.adminBookLoader.getController();
    // the popup already contains a book with information in the TextField
    private Book currentBook;
    private int bookIndex;
    @FXML
    private TextField editTitle;
    @FXML
    private TextField editAuthor;
    @FXML
    private TextField editISBN;
    @FXML
    private TextField editPublisher;
    @FXML
    private TextField editTotal;
    @FXML
    private TextField editAvail;


    @FXML
    private void onCancelClick(ActionEvent event) {
        //this function ignore all changes in TextField  --> do nothing to the textfield info
        this.adminBook.getAddEditStage().close();
    }
    @FXML
    private void onFinishClick(ActionEvent event) {
        //new book info will appear in the edited book's row
            this.currentBook.set_title(editTitle.getText());
            this.currentBook.set_author(editAuthor.getText());
            this.currentBook.set_ISBN(editISBN.getText());
            this.currentBook.set_publisher(editPublisher.getText());
            int copies = Integer.parseInt(editTotal.getText());
            if(copies >= 0) {
                this.currentBook.setTotalCopies(copies);
                this.currentBook.setAvailableCopies(copies);
            }
            adminBook.getBookList().updateBook(this.currentBook);
            adminBook.getViewBooks().getItems().set(this.bookIndex, this.currentBook);
            adminBook.getAddEditStage().close();
    }

    @FXML
    private void onRemoveClick(ActionEvent event) throws Exception {
        adminBook.getBookList().removeFromList(this.currentBook);
        adminBook.getViewBooks().getItems().remove(this.bookIndex);
        this.adminBook.getEditBookStage().close();
    }

    //load and set currentBook in the AdminBook class before opening editBookPopup
    public void loadEditBook(Book book) {
        this.currentBook = book;
        this.editTitle.setText(this.currentBook.get_title());
        this.editAuthor.setText(this.currentBook.get_author());
        this.editISBN.setText(this.currentBook.get_ISBN());
        this.editPublisher.setText(this.currentBook.get_publisher());
        this.editTotal.setText(String.valueOf(this.currentBook.getTotalCopies()));
        this.editAvail.setText(String.valueOf(this.currentBook.getAvailableCopies()));
    }

    public void setBookIndex(int bookIndex) {
        this.bookIndex = bookIndex;
    }

}

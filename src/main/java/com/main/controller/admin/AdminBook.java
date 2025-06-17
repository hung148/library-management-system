package com.main.controller.admin;

import com.main.entity.Book;
import com.main.model.BookListModel;
import com.main.view.LibraryApplication;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


//display table of books with author (last name, first name), ISBN, genres, and unique library ID with add and remove functions
public class AdminBook implements Initializable {
    @FXML
    private Label noBookSelectedAlert;
    @FXML
    private TableColumn<Book, Integer> totalCol;
    @FXML
    private TableColumn<Book, Integer> availCol;
    @FXML
    private TableColumn<Book, String> publishCol;
    @FXML
    private TableView<Book> viewBooks;
    @FXML
    private TableColumn<Book, String> titleCol;
    @FXML
    private TableColumn<Book, String> authorCol;
    @FXML
    private TableColumn<Book, String> isbnCol;
    private final FXMLLoader addBookLoader = new FXMLLoader(LibraryApplication.class.getResource("admin/add-book-popup.fxml"));
    private Parent addBookRoot;
    private final FXMLLoader editBookLoader = new FXMLLoader(LibraryApplication.class.getResource("admin/edit-book-popup.fxml"));
    private Parent editBookRoot;
    private Stage addEditStage;
    private BookListModel bookList = new BookListModel();
    private Scene addScene;
    private Scene editScene;

    public BookListModel getBookList() {
        return bookList;
    }

    public void setAdminBookList(BookListModel bookList) {
        this.bookList = bookList;
    }

    public TableView<Book> getViewBooks() {
        return viewBooks;
    }

    public Stage getAddEditStage() {
        return addEditStage;
    }

    //run in the loadInfoForAdminThread which start in the AdminLoginController class
    public void setViewBooks(BookListModel booklist) {
        //PropertyValueFactory(*name*) calls getter of Book so getter methods need to match the parameter *name*
        titleCol.setCellValueFactory(new PropertyValueFactory<>("_title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("_author"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("_ISBN"));
        publishCol.setCellValueFactory(new PropertyValueFactory<>("_publisher"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("TotalCopies"));
        availCol.setCellValueFactory(new PropertyValueFactory<>("AvailableCopies"));
        viewBooks.getItems().addAll(booklist.getList());
    }

    //pop-up windows to add book
    public void clickAdd(ActionEvent event) throws IOException {

        addEditStage = new Stage();
        //need to finish the task in this popup before going back to main Stage
        addEditStage.initModality(Modality.APPLICATION_MODAL);
        if (addBookRoot.getScene() == null) {
            addScene = new Scene(addBookRoot);

        }
        AddBookPopup addBookPopup = addBookLoader.getController();
        addBookPopup.setBookList(this.bookList);
        addEditStage.setScene(addScene);
        addEditStage.setTitle("Add New Book");
        addEditStage.setResizable(false);
        addEditStage.showAndWait();
    }

    public void clickEdit(ActionEvent event) throws IOException {
        this.noBookSelectedAlert.setVisible(false);
        try {
            addEditStage = new Stage();
            addEditStage.initModality(Modality.APPLICATION_MODAL);
            if (editBookRoot.getScene() == null) {
                editScene = new Scene(editBookRoot);
            }
            EditBookPopup editBookPopup = editBookLoader.getController();
            Book editBook = viewBooks.getSelectionModel().getSelectedItem();
            editBookPopup.setBookIndex(this.viewBooks.getSelectionModel().getFocusedIndex());
            editBookPopup.loadEditBook(editBook);
            addEditStage.setScene(editScene);
            addEditStage.setTitle("Edit or Remove Book");
            addEditStage.setResizable(false);
            addEditStage.showAndWait();
        } catch (NullPointerException e) {
            this.noBookSelectedAlert.applyCss();
            this.noBookSelectedAlert.setVisible(true);
            this.noBookSelectedAlert.setText("Please select a Book to Edit or Remove");
        }
        viewBooks.refresh();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            try {
                addBookRoot = addBookLoader.load();
                editBookRoot = editBookLoader.load();
                System.out.println("This is when popup pages are loaded");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public Stage getEditBookStage() {
        return addEditStage;
    }
}

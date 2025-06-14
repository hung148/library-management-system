package com.main.controller.admin;

import com.main.entity.Book;
import com.main.model.BookListModel;
import com.main.view.LibraryApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;


//display table of books with author (last name, first name), ISBN, genres, and unique library ID with add and remove functions
public class AdminBook {
    @FXML
    private TableColumn<Book, Integer> totalCol;
    @FXML
    private TableColumn<Book, Integer> availCol;
    @FXML
    private TableColumn<Book,String> publishCol;
    @FXML
    private TableView<Book> viewBooks ;
    @FXML
    private TableColumn<Book,String> titleCol;
    @FXML
    private TableColumn<Book,String> authorCol;
    @FXML
    private TableColumn<Book,String> isbnCol;
    public final FXMLLoader addBookLoader = new FXMLLoader(LibraryApplication.class.getResource("admin/add-book-popup.fxml"));
    private Stage addBookStage;
    private BookListModel bookList;



    public void setAdminBookList(BookListModel bookList) {
        this.bookList = bookList;
    }
    public TableView<Book> getViewBooks() {
        return viewBooks;
    }
    public Stage getAddBookStage() {
        return addBookStage;
    }

    //run in the loadInfoForAdminThread which start in the AdminLoginController class
    //for loading the tableView with infor
    public void setViewBooks(@NotNull BookListModel booklist) {
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
        addBookStage = new Stage();
        //need to finish the task in this popup before going back to main Stage
        addBookStage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(addBookLoader.load());
        AddBookPopup addBookPopup = addBookLoader.getController();
        addBookPopup.setBookListModel(bookList);
//        ((AddBookPopup)addBookLoader.getController()).setBookListModel(this.bookList);
        addBookStage.setScene(scene);
        addBookStage.setTitle("Adding New Book");
        addBookStage.setResizable(false);
        addBookStage.showAndWait();

    }

    //perhaps "Remove" function can be inside Edit?
    public void clickRemove(ActionEvent event) {
        
    }


    public void clickEdit(ActionEvent event) {
    }
}

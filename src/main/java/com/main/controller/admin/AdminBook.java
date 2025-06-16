package com.main.controller.admin;

import com.main.entity.Book;
import com.main.model.BookListModel;
import com.main.respository.LibraryDAO;
import com.main.view.LibraryApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;


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
        FXMLLoader addBookLoader = new FXMLLoader(getClass().getResource("/com/main/view/admin/add-book-popup.fxml"));
        addBookStage = new Stage();
        //need to finish the task in this popup before going back to main Stage
        addBookStage.initModality(Modality.APPLICATION_MODAL);
        addBookStage.initOwner(LibraryApplication.stage); // Set the owner to prevent full screen inheritance
        addBookStage.setResizable(false); // Disable resizing to maintain fixed size
        Scene scene = new Scene(addBookLoader.load());
        AddBookPopup addBookPopup = addBookLoader.getController();
        addBookPopup.setBookListModel(bookList);
//        ((AddBookPopup)addBookLoader.getController()).setBookListModel(this.bookList);
        addBookStage.setScene(scene);
        addBookStage.setTitle("Adding New Book");
        addBookStage.showAndWait();

    }
    // may be we can choose a book first 
    private Book selected;
    public void clickRemoveOrEdit(ActionEvent event) {
        selected = viewBooks.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // display option to remove or edit here
            // Create a dialog to choose between Edit or Remove
            Alert choiceDialog = new Alert(Alert.AlertType.CONFIRMATION);
            if (LibraryApplication.stage != null) {
                choiceDialog.initOwner(LibraryApplication.stage); // Set the owner to prevent full screen inheritance
            }
            choiceDialog.setResizable(false); // Disable resizing to maintain fixed size
            choiceDialog.setTitle("Edit or Remove");
            choiceDialog.setHeaderText("What do you want to do with the selected book?");
            String content = ("Book detail: \n" + 
                            "\tISBN: " + selected.get_ISBN() + "\n" +
                            "\tTitle: " + selected.get_title() + "\n" +
                            "\tAuthor: " + selected.get_author() + "\n" +
                            "\tPublisher: " + selected.get_publisher() + "\n" +
                            "\tTotal copies: " + selected.getTotalCopies() + "\n" +
                            "\tTotal avialable: " + selected.getAvailableCopies() + "\n\n" +
                            "Choose an option:");
            choiceDialog.setContentText(content);

            ButtonType editButton = new ButtonType("Edit");
            ButtonType removeButton = new ButtonType("Remove");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            choiceDialog.getButtonTypes().setAll(editButton, removeButton, cancelButton);

            Optional<ButtonType> result = choiceDialog.showAndWait();

            if (result.isPresent()) {
                if (result.get() == editButton) {
                    clickEdit(event); // delegate to clickEdit
                } else if (result.get() == removeButton) {
                    clickRemove(event); // delegate to clickRemove
                }
            }

        } else {
            // display warning to choose a book first 
            LibraryApplication.showAlert(AlertType.WARNING, "No Book Selected", "Please select a book to edit or remove");
        }
    }

    //perhaps "Remove" function can be inside Edit?
    public void clickRemove(ActionEvent event) {
        System.out.println("Running remove");
        // remove 
        if (selected != null) {
            LibraryDAO.removeBook(selected.get_ISBN());
            LibraryApplication.showAlert(AlertType.INFORMATION, "Remove book", selected.get_title() + " is removed successfully.");
            bookList = new BookListModel();
            getViewBooks().setItems(bookList.getList());
        }
    }


    public void clickEdit(ActionEvent event) {
        System.out.println("Running edit");
        // display option to edit title, author, publisher, ISBN, total copies, total avialable
        try {
            // Load edit-book-popup.fxml (create this if not already made)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/main/view/admin/edit-book-popup.fxml"));
            Parent root = loader.load();
            // Get controller and pass the selected book
            EditBookPopup controller = loader.getController();
            controller.setBook(selected);
            controller.setBookListModel(bookList); // if needed to update list or data source

            // Set up and show modal window
            Stage editStage = new Stage();
            editStage.initModality(Modality.APPLICATION_MODAL);
            editStage.initOwner(LibraryApplication.stage);
            editStage.setScene(new Scene(root));
            editStage.setTitle("Edit Book");
            editStage.setResizable(false);
            editStage.showAndWait();

            // Refresh view in case data changed
            getViewBooks().refresh();

        } catch (IOException e) {
            e.printStackTrace();
            LibraryApplication.showAlert(AlertType.ERROR, "Edit Error", "Could not open edit window.");
        }
    }
}

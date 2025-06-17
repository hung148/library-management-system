package com.main.controller.member;

import com.main.entity.Book;
import com.main.entity.BorrowedBook;
import com.main.entity.Member;
import com.main.model.BookListModel;
import com.main.respository.LibraryDAO;
import com.main.view.LibraryApplication;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import static com.main.services.LibraryServices.searchBook;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.main.app.Main;
import com.main.controller.AdminController;
import com.main.controller.admin.AdminBook;


public class MemberBook {
    //add these columns once dueDate and returnDate columns are set up in LibraryDAO
    @FXML
    public TableColumn<BorrowedBook, String> dueCol;
    @FXML
    public TableColumn<BorrowedBook, String> returnCol;

    @FXML
    private TextField searchBook;
    @FXML
    private ListView<Book> resultBook;


    //First table: Current Borrow
    @FXML
    private TableView<BorrowedBook> currentBooks;
    @FXML
    private TableColumn<BorrowedBook,String> authorCol;
    @FXML
    private TableColumn<BorrowedBook,String> isbnCol;
    @FXML
    private TableColumn<BorrowedBook,String> titleCol;
    @FXML
    private TableColumn<BorrowedBook,String> publisherCol;

    //Second table: Past Borrow
    @FXML
    private TableView<BorrowedBook> pastBooks;
    @FXML
    private TableColumn<BorrowedBook,String> isbnCol1;
    @FXML
    private TableColumn<BorrowedBook,String> titleCol1;
    @FXML
    private TableColumn<BorrowedBook,String> authorCol1;
    @FXML
    private TableColumn<BorrowedBook,String> publisherCol1;

    // Third table: late book table if there is a book that is late prevent more borrow until the fine is pay
    @FXML
    private TableView<BorrowedBook> lateBooks;
    @FXML
    private TableColumn<BorrowedBook,String> isbnCol2;
    @FXML
    private TableColumn<BorrowedBook,String> titleCol2;
    @FXML
    private TableColumn<BorrowedBook,String> authorCol2;
    @FXML
    private TableColumn<BorrowedBook,String> publisherCol2;
    // column for return date
    @FXML
    private TableColumn<BorrowedBook, LocalDate> returnDateCol;
    // cloumn for deadline
    @FXML
    private TableColumn<BorrowedBook, LocalDate> deadlineCol;
    // cloumn for date count from deadline to returndate or just from deadline if havent return
    @FXML
    private TableColumn<BorrowedBook, Integer> daysLateCol;
    // column for isPayFine and fine true or false in the database already have
    @FXML 
    private TableColumn<BorrowedBook, Double> fineCol;
    @FXML
    private TableColumn<BorrowedBook, Boolean> payFineCol;
    // column for button to payfine and return if havent return or just payfine if havent pay
    @FXML
    private TableColumn<BorrowedBook, Void> actionCol;

    // fourth table: lost book table prevent more borrow until the fine is pay 
    @FXML 
    private TableView<BorrowedBook> lostBooks;
    @FXML
    private TableColumn<BorrowedBook,String> isbnCol3;
    @FXML
    private TableColumn<BorrowedBook,String> titleCol3;
    @FXML
    private TableColumn<BorrowedBook,String> authorCol3;
    @FXML
    private TableColumn<BorrowedBook,String> publisherCol3;
    // column for return date
    @FXML
    private TableColumn<BorrowedBook, LocalDate> returnDateAfterLostCol;
    // cloumn for deadline
    @FXML
    private TableColumn<BorrowedBook, LocalDate> duedateCol;
    @FXML
    private TableColumn<BorrowedBook, Integer> daysLostCol;
    // column for isPayFine and fine true or false in the database already have
    @FXML 
    private TableColumn<BorrowedBook, Double> fineLostCol;
    @FXML
    private TableColumn<BorrowedBook, Boolean> payFineLostCol;
    // column for button to payfine and return if havent return or just payfine if havent pay
    @FXML
    private TableColumn<BorrowedBook, Void> actionLostCol;

    // Book lists
    private ObservableList<Book> allBooks = FXCollections.observableArrayList();
    private ObservableList<BorrowedBook> currentBorrowed = FXCollections.observableArrayList();
    private ObservableList<BorrowedBook> pastBorrowed = FXCollections.observableArrayList();
    private ObservableList<BorrowedBook> lateBorrowed = FXCollections.observableArrayList();
    private ObservableList<BorrowedBook> lostBorrowed = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Setup column bindings
        titleCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBook().get_title()));
        isbnCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBook().get_ISBN()));
        authorCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBook().get_author()));
        publisherCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBook().get_publisher()));
        dueCol.setCellValueFactory(data -> {
            LocalDate deadline = data.getValue().getDeadline();
            return new SimpleStringProperty(deadline != null ? deadline.toString() : "");
        });

        titleCol1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBook().get_title()));
        isbnCol1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBook().get_ISBN()));
        authorCol1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBook().get_author()));
        publisherCol1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBook().get_publisher()));
        returnCol.setCellValueFactory(data -> {
            LocalDate returnedDate = data.getValue().getReturnedDate();
            return new SimpleStringProperty(returnedDate != null ? returnedDate.toString() : "");
        });

        isbnCol2.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBook().get_ISBN()));
        titleCol2.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBook().get_title()));
        authorCol2.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBook().get_author()));
        publisherCol2.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBook().get_publisher()));

        returnDateCol.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getReturnedDate()));
        deadlineCol.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDeadline()));

        daysLateCol.setCellValueFactory(data -> {
            BorrowedBook book = data.getValue();
            LocalDate deadline = book.getDeadline();
            LocalDate returnedDate = book.getReturnedDate();
            if (deadline == null) return new SimpleIntegerProperty(0).asObject();

                LocalDate compareDate = book.isPayFine()
                    ? returnedDate
                    : (returnedDate != null ? returnedDate : LocalDate.now());

            int daysLate = (int) ChronoUnit.DAYS.between(deadline, compareDate);
            daysLate = Math.max(daysLate, 0); // no negative days
            return new SimpleIntegerProperty(daysLate).asObject();
        });
        fineCol.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getLateFine()).asObject());
        payFineCol.setCellValueFactory(data -> new SimpleBooleanProperty(data.getValue().isPayFine()));

        payFineCol.setCellFactory(tc -> new CheckBoxTableCell<>());

        addButtonToTable();

        isbnCol3.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBook().get_ISBN()));
        titleCol3.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBook().get_title()));
        authorCol3.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBook().get_author()));
        publisherCol3.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBook().get_publisher()));
        returnDateAfterLostCol.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getReturnedDate()));
        duedateCol.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDeadline()));

        daysLostCol.setCellValueFactory(data -> {
            BorrowedBook book = data.getValue();
            LocalDate deadline = book.getDeadline();
            LocalDate returnedDate = book.getReturnedDate();
            if (deadline == null) return new SimpleIntegerProperty(0).asObject();

                LocalDate compareDate = book.isPayFineAfterLost()
                    ? (returnedDate != null ? returnedDate : LocalDate.now())
                    : (returnedDate != null ? returnedDate : LocalDate.now());

            int daysLost = (int) ChronoUnit.DAYS.between(deadline, compareDate);
            daysLost = Math.max(daysLost, 0); // no negative days
            return new SimpleIntegerProperty(daysLost).asObject();
        });
        fineLostCol.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getLostFine()).asObject());
        payFineLostCol.setCellValueFactory(data -> new SimpleBooleanProperty(data.getValue().isPayFineAfterLost()));

        payFineLostCol.setCellFactory(tc -> new CheckBoxTableCell<>());

        addButtonToLostTable();

        // local book
        allBooks.addAll(
                LibraryDAO.bookList() 
        );
        // update local book if there is new book added
        Thread update = new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (allBooks.size() != LibraryDAO.bookList().length) {
                        allBooks.clear();
                        allBooks.addAll(
                                LibraryDAO.bookList() 
                        );
                    }
                    try {
                        sleep((long)1000); // update every one second
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        update.setDaemon(true); // stop when app exit have to do this before start thread
        update.start();
        
        helpInitialize();
        Thread updateLate = new Thread() {
            @Override
            public void run() {
                while(true) {
                    for (BorrowedBook book : LibraryDAO.searchBorrowedBooksListByMember(Main.currentUser.getId())) {
                        if (book.getStatus().equalsIgnoreCase("late")) {
                            for (BorrowedBook current : currentBooks.getItems()) {
                                if (current.getId() == book.getId()) {
                                    lateBorrowed.add(book);
                                    currentBorrowed.remove(current);
                                    displayCurrentBorrow();
                                    displayLateBorrow();
                                    break;
                                }
                            }
                            
                        } else if (book.getStatus().equalsIgnoreCase("lost")) {
                            
                            for (BorrowedBook current : currentBooks.getItems()) {
                                System.out.println(current.getId() == book.getId());
                                if (current.getId() == book.getId()) {
                                    lostBorrowed.add(book);
                                    currentBorrowed.remove(current);
                                    displayCurrentBorrow();
                                    displayLostBorrow();
                                    break;
                                }
                            }
                            for (BorrowedBook current : lateBooks.getItems()) {
                                if (current.getId() == book.getId()) {
                                    lostBorrowed.add(book);
                                    lateBorrowed.remove(current);
                                    displayLateBorrow();
                                    displayLostBorrow();
                                    break;
                                }
                            }
                        }
                    } 
                    try {
                        sleep((long)1000); // update every one second
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        updateLate.setDaemon(true);
        updateLate.start();
    }

    private void helpInitialize() {
        List<BorrowedBook> currentbooks = new ArrayList<>();
        List<BorrowedBook> pastbooks = new ArrayList<>();
        List<BorrowedBook> latebooks = new ArrayList<>();
        List<BorrowedBook> lostbooks = new ArrayList<>();
        for (BorrowedBook book : LibraryDAO.searchBorrowedBooksListByMember(Main.currentUser.getId())) {
            if (book.getStatus().equalsIgnoreCase("pending")) {
                currentbooks.add(book);
            } else if (book.getStatus().equalsIgnoreCase("returned")) {
                pastbooks.add(book);
            } else if (book.getStatus().equalsIgnoreCase("late")) {
                latebooks.add(book);
            } else if (book.getStatus().equalsIgnoreCase("lost")) {
                lostbooks.add(book);
            }
        }
        currentBorrowed.clear();
        pastBorrowed.clear();
        lateBorrowed.clear();
        lostBorrowed.clear();
        currentBorrowed.addAll(currentbooks);
        pastBorrowed.addAll(pastbooks);
        lateBorrowed.addAll(latebooks);
        lostBorrowed.addAll(lostbooks);
        currentBooks.setItems(null);
        pastBooks.setItems(null);
        lateBooks.setItems(null);
        lostBooks.setItems(null);
        currentBooks.setItems(currentBorrowed);
        pastBooks.setItems(pastBorrowed);
        lateBooks.setItems(lateBorrowed);
        lostBooks.setItems(lostBorrowed);
    }

    private void addButtonToLostTable() {
        actionLostCol.setCellFactory(col -> new TableCell<>() {
            private final Button payFineButton = new Button("Pay Fine");
            private final Button found = new Button("Return if found");
            private final HBox pane = new HBox(5, payFineButton, found);

            {
                payFineButton.setOnAction(event -> {
                    BorrowedBook book = getTableView().getItems().get(getIndex());
                    System.out.println("Paying fine for: " + book.getBook().get_title());
                    double balance = Main.currentUser.getBalance();
                    if (balance >= book.getLostFine()) {
                        LibraryDAO.payFine(Main.currentUser.getId(), Main.currentUser.getBalance(), 0);
                        lostBorrowed.clear();
                        for (BorrowedBook Bbook : LibraryDAO.searchBorrowedBooksListByMember(Main.currentUser.getId())) {
                            if (Bbook.getStatus().equalsIgnoreCase("lost")) {
                                lostBorrowed.add(Bbook);
                            }
                        } 
                        displayLostBorrow();
                    } else {
                        // pop up for user to enter more money 
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.initOwner(LibraryApplication.stage);
                        alert.initModality(Modality.WINDOW_MODAL);
                        alert.setResizable(false);
                        alert.setTitle("Insufficient Balance");
                        alert.setHeaderText("You don't have enough balance to pay the fine.");
                        alert.setContentText("Would you like to add more money to your account?");
                        ButtonType yesButton = new ButtonType("Yes");
                        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                        alert.getButtonTypes().setAll(yesButton, noButton);

                        alert.showAndWait().ifPresent(response -> {
                            if (response == yesButton) {
                                openTopUpDialog(); // Call a method that handles recharging
                            }
                        });
                    }
                });

                found.setOnAction(event -> {
                    BorrowedBook book = getTableView().getItems().get(getIndex());
                    System.out.println("Returning book: " + book.getBook().get_title());
                    LocalDate returnedDate = LocalDate.now();
                    LibraryDAO.returnBorrowedBook(Main.currentUser.getId(), book, returnedDate);
                    // update quantity
                    book.getBook().setAvailableCopies(book.getBook().getAvailableCopies() + 1);
                    LibraryDAO.updateBook(book.getBook().get_ISBN(), book.getBook());
                    book = LibraryDAO.searchBorrowedBookByMember(book.getMemberID(), book.getBook().get_ISBN());
                    // Refresh admin book view if applicable
                    if (AdminController.adminBookLoader != null) {
                        AdminBook adminBook = AdminController.adminBookLoader.getController();
                        BookListModel bookList = new BookListModel();
                        adminBook.setAdminBookList(bookList);
                        adminBook.setViewBooks(bookList); // Update admin book TableView
                    }
                    lostBorrowed.clear();
                    for (BorrowedBook Bbook : LibraryDAO.searchBorrowedBooksListByMember(Main.currentUser.getId())) {
                        if (Bbook.getStatus().equalsIgnoreCase("lost")) {
                            lostBorrowed.add(Bbook);
                        }
                    } 
                    displayLostBorrow();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    BorrowedBook book = getTableView().getItems().get(getIndex());
                    // Show or hide buttons conditionally
                    payFineButton.setDisable(book.isPayFineAfterLost());
                    found.setDisable(book.getReturnedDate() != null);
                    setGraphic(pane);
                    if (book.getReturnedDate() != null) {
                        found.setText("Already Returned");
                    } else {
                        found.setText("Return if found");
                    }
                }
            }
        });
    }

    private void addButtonToTable() {
        actionCol.setCellFactory(col -> new TableCell<>() {
            private final Button payFineButton = new Button("Pay Fine & Return if haven't");
            private final HBox pane = new HBox(5, payFineButton);

            {
                payFineButton.setOnAction(event -> {
                    BorrowedBook book = getTableView().getItems().get(getIndex());
                    System.out.println("Paying fine for: " + book.getBook().get_title());
                    double balance = Main.currentUser.getBalance();
                    System.out.println(balance);
                    System.out.println(book.getLateFine());
                    if (balance >= book.getLateFine()) {
                        LibraryDAO.payFine(Main.currentUser.getId(), Main.currentUser.getBalance(), 1);
                        // Your return book logic here
                        if (book.getReturnedDate() == null) {
                            System.out.println("Returning book: " + book.getBook().get_title());
                            LocalDate returnedDate = LocalDate.now();
                            LibraryDAO.returnBorrowedBook(Main.currentUser.getId(), book, returnedDate);
                            // update quantity
                            book.getBook().setAvailableCopies(book.getBook().getAvailableCopies() + 1);
                            LibraryDAO.updateBook(book.getBook().get_ISBN(), book.getBook());
                            book = LibraryDAO.searchBorrowedBookByMember(book.getMemberID(), book.getBook().get_ISBN());
                            // Refresh admin book view if applicable
                            if (AdminController.adminBookLoader != null) {
                                AdminBook adminBook = AdminController.adminBookLoader.getController();
                                BookListModel bookList = new BookListModel();
                                adminBook.setAdminBookList(bookList);
                                adminBook.setViewBooks(bookList); // Update admin book TableView
                            }
                        }
                        lateBorrowed.clear();
                        for (BorrowedBook Bbook : LibraryDAO.searchBorrowedBooksListByMember(Main.currentUser.getId())) {
                            if (Bbook.getStatus().equalsIgnoreCase("late")) {
                                lateBorrowed.add(Bbook);
                            }
                        } 
                        displayLateBorrow();
                    } else {
                        // pop up to add more money
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.initOwner(LibraryApplication.stage);
                        alert.initModality(Modality.WINDOW_MODAL);
                        alert.setResizable(false);
                        alert.setTitle("Insufficient Balance");
                        alert.setHeaderText("You don't have enough balance to pay the fine.");
                        alert.setContentText("Would you like to add more money to your account?");
                        ButtonType yesButton = new ButtonType("Yes");
                        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                        alert.getButtonTypes().setAll(yesButton, noButton);

                        alert.showAndWait().ifPresent(response -> {
                            if (response == yesButton) {
                                openTopUpDialog(); // Call a method that handles recharging
                            }
                        });
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    BorrowedBook book = getTableView().getItems().get(getIndex());
                    // Show or hide buttons conditionally
                    payFineButton.setDisable(book.isPayFine() || book.getReturnedDate() != null);
                    setGraphic(pane);
                }
            }
        });
    }
    private void openTopUpDialog() {
        Dialog<Double> dialog = new Dialog<>();
        dialog.initOwner(LibraryApplication.stage);
        dialog.setResizable(false);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setTitle("Top Up Balance");

        Label label = new Label("Enter amount to add: ");
        TextField amountField = new TextField();
        HBox content = new HBox(10, label, amountField);
        content.setPadding(new Insets(20));

        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                try {
                    return Double.parseDouble(amountField.getText());
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            return null;
        });

        Optional<Double> result = dialog.showAndWait();
        result.ifPresent(amount -> {
            if (amount > 0) {
                double newBalance = Main.currentUser.getBalance() + amount;
                Main.currentUser.setBalance(newBalance);
                LibraryDAO.updateMember(Main.currentUser.getId(), (Member)Main.currentUser);
                Alert success = new Alert(Alert.AlertType.INFORMATION, "Balance updated successfully!");
                success.showAndWait();
            } else {
                Alert error = new Alert(Alert.AlertType.ERROR, "Invalid amount entered.");
                error.showAndWait();
            }
        });
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
            for(Book book : bookList) {
                book.printBookInfo();
            }
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
        double totalFine = 0;
        for (BorrowedBook book : LibraryDAO.searchBorrowedBooksListByMember(Main.currentUser.getId())) {
            if (book.getStatus().equalsIgnoreCase("late")) {
                if (!book.isPayFine()) {
                    totalFine += book.getLateFine();
                }
            }
            if (book.getStatus().equalsIgnoreCase("lost")) {
                if (!book.isPayFineAfterLost()) {
                    totalFine += book.getLostFine();
                }
            }
        }
        if (totalFine > 0) {
            LibraryApplication.showAlert(AlertType.WARNING, "Pay Fine", "Outstanding fine: " + totalFine + ". Please pay fine before continue to borrow");
            return;
        }
        Book selectedBook = resultBook.getSelectionModel().getSelectedItem();
        
        if (selectedBook == null) {
             LibraryApplication.showAlert(AlertType.WARNING, "no book selected", "Please select a book to borrow");
             return;
        }
        
        for (BorrowedBook book : currentBooks.getItems()) {
            if (book.getBook().get_ISBN().equals(selectedBook.get_ISBN())) {
                LibraryApplication.showAlert(AlertType.WARNING, "Already Borrowed", "You have already added this book.");
                return;
            }
        }

        if (selectedBook.getAvailableCopies() <= 0) {
            LibraryApplication.showAlert(AlertType.WARNING, "Unavailable", "No available copies of this book.");
            return;
        }

        // Create a dialog for due date selection
        Dialog<LocalDate> dialog = new Dialog<>();
        dialog.initOwner(LibraryApplication.stage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setResizable(false);
        dialog.setTitle("Set Due Date");
        dialog.setHeaderText("Choose the due date for borrowing");

        // Use DatePicker as content
        DatePicker datePicker = new DatePicker();
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                LocalDate today = LocalDate.now().minusWeeks(3);
                LocalDate twoWeeksLater = today.plusWeeks(3);

                // Disable dates before today or after 2 weeks from now
                setDisable(empty || date.isBefore(today) || date.isAfter(twoWeeksLater));
            }
        });
        datePicker.setValue(LocalDate.now().plusWeeks(2)); // default due in 2 weeks

        // Set dialog content
        VBox dialogVBox = new VBox(10, new Label("Due Date:"), datePicker);
        dialogVBox.setPadding(new Insets(10));
        dialog.getDialogPane().setContent(dialogVBox);

        // Add OK and Cancel buttons
        ButtonType okButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // Convert result
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return datePicker.getValue();
            }
            return null;
        });

        // Show dialog and wait
        Optional<LocalDate> result = dialog.showAndWait();

        if (result.isEmpty()) {
            return; // User cancelled
        }

        LocalDate dueDate = result.get();
        BorrowedBook selected = new BorrowedBook(Main.currentUser.getId(), selectedBook, LocalDate.now(), dueDate);
        LibraryDAO.addBorrowedBook(Main.currentUser.getId(), selected.getBook(), dueDate);
        selected = LibraryDAO.searchBorrowedBookByMember(Main.currentUser.getId(), selected.getBook().get_ISBN());
        currentBorrowed.add(selected);
        // reduce the number of avialable 
        selected.getBook().setAvailableCopies(selected.getBook().getAvailableCopies() - 1);
        // Refresh admin book view if applicable
        if (AdminController.adminBookLoader != null) {
            AdminBook adminBook = AdminController.adminBookLoader.getController();
            BookListModel bookList = new BookListModel();
            adminBook.setAdminBookList(bookList);
            adminBook.setViewBooks(bookList); // Update admin book TableView
        }
        LibraryDAO.updateBook(selected.getBook().get_ISBN(), selected.getBook());
    }

    //select one book currentBook then click Return button then book will be removed from currentBook and show up in pastBooks
    @FXML
    private void onClickReturn(ActionEvent event) {
        BorrowedBook selected = currentBooks.getSelectionModel().getSelectedItem();
        if (selected == null) {
             LibraryApplication.showAlert(AlertType.WARNING, "no book selected", "Please select a book to return");
             return;
        }
        if (selected != null) {
            currentBorrowed.remove(selected);
            displayCurrentBorrow();
            // update database
            LocalDate returnedDate = LocalDate.now();
            LibraryDAO.returnBorrowedBook(Main.currentUser.getId(), LibraryDAO.searchBorrowedBookByMember(Main.currentUser.getId(), selected.getBook().get_ISBN()), returnedDate);
            BorrowedBook test = LibraryDAO.searchBorrowedBookByMember(Main.currentUser.getId(), selected.getBook().get_ISBN());
            System.out.println(test.getStatus());
            // update quantity
            selected.getBook().setAvailableCopies(selected.getBook().getAvailableCopies() + 1);
            LibraryDAO.updateBook(selected.getBook().get_ISBN(), selected.getBook());
            // Refresh admin book view if applicable
            if (AdminController.adminBookLoader != null) {
                AdminBook adminBook = AdminController.adminBookLoader.getController();
                BookListModel bookList = new BookListModel();
                adminBook.setAdminBookList(bookList);
                adminBook.setViewBooks(bookList); // Update admin book TableView
            }
            BorrowedBook updated = LibraryDAO.searchBorrowedBookByMember(
                Main.currentUser.getId(), 
                selected.getBook().get_ISBN()
            );
            pastBorrowed.add(updated);
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

    private void displayLateBorrow() {
        lateBooks.setItems(null);
        lateBooks.setItems(lateBorrowed);
    }

    private void displayLostBorrow() {
        lostBooks.setItems(null);
        lostBooks.setItems(lostBorrowed);
    }

}

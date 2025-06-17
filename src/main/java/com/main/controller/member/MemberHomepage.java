package com.main.controller.member;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.main.app.Main;
import com.main.entity.BorrowedBook;
import com.main.respository.LibraryDAO;

public class MemberHomepage implements Initializable {

    public Label temp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            if (Main.currentUser != null) {
                List<BorrowedBook> borrowed = LibraryDAO.searchBorrowedBooksListByMember(Main.currentUser.getId());
                if (borrowed.isEmpty()) {
                    // UI update code here
                    temp.setText("You have no borrowed books.");
                    return;
                }

                StringBuilder display = new StringBuilder("Important:\n\n");
                for (BorrowedBook book : borrowed) {
                    if (book.getStatus().equalsIgnoreCase("lost") && book.isPayFine() == false) {
                        String title = book.getBook().get_title();
                        LocalDate due = book.getDeadline();
                        String status = book.getStatus();
                        double fine = book.getLateFine();
                        double lostfine = book.getLostFine();

                        display.append("• Title: ").append(title).append("\n")
                            .append("  \tDue Date: ").append(due).append("\n")
                            .append("  \tStatus: ").append(status != null ? status : "lost").append("\n")
                            .append("  \tOverdue Fee: $").append(String.format("%.2f", fine)).append("\n")
                            .append("  \tLost Fee: $").append(String.format("%.2f", lostfine)).append("\n");
                    }
                    else if (book.getStatus().equalsIgnoreCase("late") && book.isPayFine() == false) {
                        String title = book.getBook().get_title();
                        LocalDate due = book.getDeadline();
                        String status = book.getStatus();
                        double fine = book.getLateFine();
                        double lostfine = book.getLostFine();

                        display.append("• Title: ").append(title).append("\n")
                            .append("  \tDue Date: ").append(due).append("\n")
                            .append("  \tStatus: ").append(status != null ? status : "late").append("\n")
                            .append("  \tOverdue Fee: $").append(String.format("%.2f", fine)).append("\n")
                            .append("  \tLost Fee: $").append(String.format("%.2f", lostfine)).append("\n\n");
                    }
                }
                display.append("\n\nCurrent Borrowed Books:\n\n");
                for (BorrowedBook book : borrowed) {
                    if (book.getStatus().equalsIgnoreCase("pending")) {
                        String title = book.getBook().get_title();
                        LocalDate due = book.getDeadline();
                        String status = book.getStatus();
                        double fine = book.getLateFine();
                        double lostfine = book.getLostFine();

                        display.append("• Title: ").append(title).append("\n")
                            .append("  \tDue Date: ").append(due).append("\n")
                            .append("  \tStatus: ").append(status != null ? status : "pending").append("\n")
                            .append("  \tOverdue Fee: $").append(String.format("%.2f", fine)).append("\n")
                            .append("  \tLost Fee: $").append(String.format("%.2f", lostfine)).append("\n\n");
                    }
                }
                
                temp.setText(display.toString());
            }
        });
    }
}

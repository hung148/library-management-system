package com.main.controller;



import com.main.app.Main;

import com.main.controller.admin.*;

import com.main.entity.Admin;
import com.main.entity.Book;
import com.main.model.BookListModel;
import com.main.respository.LibraryDAO;
import com.main.services.AuthServices;
import com.main.view.LibraryApplication;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

public class AdminLoginController implements Initializable {
    @FXML
    private TextField inputUsername;
    @FXML
    private PasswordField inputPassword;
    @FXML
    private Label alertLogin;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private AnchorPane leftPane;
    @FXML
    private AnchorPane rightPane;

    private PauseTransition debounce = new PauseTransition(Duration.millis(120));
    private Timeline centerAnimation;

    @Override
    public void initialize(URL location, ResourceBundle resouces) {
        alertLogin.getStyleClass().add("alertLogin");
        // dropshadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.7));
        rightPane.setEffect(dropShadow);

        Platform.runLater(() -> {
            LibraryApplication.stage.setMinWidth(400);
            LibraryApplication.stage.setMinHeight(570);
        });

        rootPane.widthProperty().addListener((obs, oldW, newW) -> {
            debounce.setOnFinished(e -> updateLayout(newW.doubleValue(), rootPane.getHeight()));
            debounce.playFromStart();
        });

        rootPane.heightProperty().addListener((obs, oldH, newH) -> {
            debounce.setOnFinished(e -> updateLayout(rootPane.getWidth(), newH.doubleValue()));
            debounce.playFromStart();
        });

        Platform.runLater(() -> {
            LibraryApplication.stage.fullScreenProperty().addListener((obs, wasFullScreen, isNowFullScreen) -> {
                if (isNowFullScreen) {
                    System.out.println("Entered full screen mode.");
                    updateLayout(rootPane.getWidth(), rootPane.getHeight());
                    System.out.println(rightPane.getLayoutX() + " " + rightPane.getLayoutY());
                } else {
                    System.out.println("Exited full screen mode.");
                    updateLayout(rootPane.getWidth(), rootPane.getHeight());
                    System.out.println(rightPane.getLayoutX() + " " + rightPane.getLayoutY()); // this is still update right
                }
            });
        });

    }

    private void updateLayout(double width, double height) {
        // Adjust layout based on width and height
        System.out.println("Width: " + width + ", Height: " + height);
        AnchorPane.clearConstraints(rightPane);
        animateToCenter(width, height);
    }

    private void animateToCenter(double width, double height) {
        // Cancel any previous animation
        if (centerAnimation != null) {
            centerAnimation.stop();
        }
        rightPane.layout();

        double rootWidth = width;
        double rootHeight = height;

        double targetWidth = 350;
        double targetHeight = 400;
        double targetX = (rootWidth - targetWidth) / 2;
        double targetY = (rootHeight - targetHeight) / 2;

        KeyValue widthKV = new KeyValue(rightPane.prefWidthProperty(), targetWidth, Interpolator.EASE_BOTH);
        KeyValue heightKV = new KeyValue(rightPane.prefHeightProperty(), targetHeight, Interpolator.EASE_BOTH);
        KeyValue xKV = new KeyValue(rightPane.layoutXProperty(), targetX, Interpolator.EASE_BOTH);
        KeyValue yKV = new KeyValue(rightPane.layoutYProperty(), targetY, Interpolator.EASE_BOTH);

        KeyFrame keyFrame = new KeyFrame(Duration.millis(500), widthKV, heightKV, xKV, yKV);
        centerAnimation = new Timeline(keyFrame);

        // Wait for layout to be ready
        centerAnimation.setOnFinished(e -> {
            Platform.runLater(() -> {
                // Force a re-layout to finalize the animation effect
                rightPane.requestLayout();
            });
        });
        centerAnimation.setDelay(Duration.millis(100));
        centerAnimation.play();
    }

    private Admin admin;
    public static final ObservableList<Book> books = FXCollections.observableArrayList();
    private final BookListModel bookList = new BookListModel();


    @FXML
    private void onSignInClick(ActionEvent event) {
        String username = inputUsername.getText();
        String password = inputPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            alertLogin.setText("* Please enter both username and password.");
            setAlertLoginToEmpty();
            return;
        }

        admin = loadAdmin(username, password);

        if(admin != null) {
            if (admin.getEmail().equals(Main.firstAdminGmail)) {
                if (password.equals(Main.firstAdminPass)) {
                    // require to change password
                    showChangePasswordDialog(LibraryApplication.stage, username);
                }
            }
            clear();
            //load books and admin's info using Thread bc it is a one-time action
            //Thread runs once login successfully
            Thread loadInfoForAdminThread = new Thread(() -> {
                AdminAccount adminAccount = AdminController.adminAccountLoader.getController();
                adminAccount.displayAccount(admin.getName(),admin.getEmail(),admin.getUsername());
                AdminBook adminBook = AdminController.adminBookLoader.getController();
                adminBook.setAdminBookList(bookList);
                adminBook.setViewBooks(bookList);  //set the tableView in adminBook
            });
            loadInfoForAdminThread.start();
            LibraryApplication.loadAdminPage();
        }
        else {
            alertLogin.setText("* Invalid username or password.");
            setAlertLoginToEmpty();
        }
    }



    public void showChangePasswordDialog(Stage owner, String username) {
        Stage dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setTitle("Change Password");
        dialog.setResizable(false);

        PasswordField newPass = new PasswordField();
        newPass.setPromptText("New password");
        PasswordField confirmPass = new PasswordField();
        confirmPass.setPromptText("Confirm password");

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        Button submit = new Button("Change");
        submit.setOnAction(e -> {
            String pass = newPass.getText();
            String confirm = confirmPass.getText();

            if (pass.isEmpty() || confirm.isEmpty()) {
                errorLabel.setText("Fields cannot be empty.");
            } else if (!pass.equals(confirm)) {
                errorLabel.setText("Passwords do not match.");
            } else {
                Admin newAdmin = new Admin(admin);
                newAdmin.setHashPassword(AuthServices.generateHashedPassword(newPass.getText()));
                System.out.print(LibraryDAO.getAdminById(admin.getId()).getHashPassword());
                LibraryDAO.updateAdmin(admin.getId(), newAdmin);
                Main.currentUser = newAdmin;
                admin = newAdmin;
                System.out.print(LibraryDAO.getAdminById(admin.getId()).getHashPassword());
                dialog.close();
            }
        });

        VBox layout = new VBox(10, new Label("Change your password:"), newPass, confirmPass, errorLabel, submit);
        layout.setStyle("-fx-padding: 15; -fx-background-color: white;");
        dialog.setScene(new Scene(layout, 300, 200));
        dialog.showAndWait(); // block until closed
    }


    private void setAlertLoginToEmpty() {
        Thread stop = new Thread() {
            @Override
            public void run() {
                // wait for 1 minute then set alertLogin text to empty
                try {
                    Thread.sleep(60_000); // wait 1 minute (60,000 ms)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Update UI on JavaFX thread
                Platform.runLater(() -> {
                    alertLogin.setText("");
                    System.out.println("Set alertLogin to empty");
                });
            }
        };
        stop.setDaemon(true); // Optional: stops thread when app exits
        stop.start();
    }

    private Admin loadAdmin(String username, String password) {
        return AuthServices.adminLogin(username, password);
    }


    // click Back to go back to Start-page with the 2 options
    @FXML
    private void backToStart(MouseEvent event) {
        clear();
        LibraryApplication.loadStartPage();
    }

    private void clear() {
        inputPassword.clear();
        inputUsername.clear();
        alertLogin.setText("");
    }
}

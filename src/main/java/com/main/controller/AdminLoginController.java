package com.main.controller;

import com.main.entity.Admin;
import com.main.entity.Book;
import com.main.entity.User;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
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
    public static User user;
    public static final ObservableList<Book> books = FXCollections.observableArrayList();

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
            user = admin;
            Collections.addAll(books, LibraryDAO.bookList());
            LibraryApplication.loadAdminPage();
        }
        else {
            alertLogin.setText("* Invalid username or password.");
            setAlertLoginToEmpty();
        }
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
        LibraryApplication.loadStartPage();
    }
}

package com.main.controller;

import com.main.respository.LibraryDAO;
import com.main.services.AuthServices;
import com.main.services.LibraryServices;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.main.view.LibraryApplication;

public class RegisterController implements Initializable {

    public Button register;

    @FXML
    public TextField lastNameField;

    @FXML
    public TextField libraryIdField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private AnchorPane rootPane;

    @FXML 
    private AnchorPane rightPane;

    @FXML
    private AnchorPane leftPane;

    private PauseTransition debounce = new PauseTransition(Duration.millis(120));
    private Timeline centerAnimation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // drop shadow 
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

        double targetWidth = 323.5;
        double targetHeight = 423;
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

    @FXML
    private void handleRegister(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String name = nameField.getText();
        String email = emailField.getText();

        if (username.isEmpty() || password.isEmpty() || name.isEmpty()) {
            LibraryApplication.showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill in all fields.");
            return;
        }

        if (AuthServices.memberRegister(email, password, username, name, 0.0)) {
            LibraryApplication.showAlert(Alert.AlertType.INFORMATION, "Success", "Member registered successfully.");
            // redirect back to member login page for user to login
            LibraryApplication.loadLoginMemberPage();
            // Optional: Clear the form
            usernameField.clear();
            passwordField.clear();
            nameField.clear();
            emailField.clear();
            lastNameField.clear();
            libraryIdField.clear();
        } else {
            LibraryApplication.showAlert(Alert.AlertType.ERROR, "Database Error", "Could not register member.");
        }
    }

    // Cancel click goes back to Member login page
    @FXML
    private void onCancelClick(ActionEvent event) throws IOException {
        LibraryApplication.loadLoginMemberPage();
    }

}

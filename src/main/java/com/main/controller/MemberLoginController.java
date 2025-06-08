/* Example of opening a second scene (window) on button click on the first scene
---this is method in the controller class of the first scene----

    public void openScene2(ActionEvent event) throws IOException
    // Load the FXML file for the second scene
    FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene2.fxml"));
    // Create a new stage for the second scene
    Stage stage = new Stage();
    stage.setScene(new Scene(loader.load()));
    // Set the title for the second scene
    stage.setTitle("Scene 2");
    // Show the second scene
    stage.show();

    To connect properties and methods from this class to the fxml file, add these to the Label in the fxml
    <Button fx:id="btnOpenScene2" text="Open Scene 2" onAction="#openScene2"/>

* */

package com.main.controller;


import com.main.entity.Book;
import com.main.entity.Member;
import com.main.entity.User;
import com.main.services.AuthServices;

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

import java.net.URL;
import java.util.ResourceBundle;

import com.main.view.LibraryApplication;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;


public class MemberLoginController implements Initializable {
    @FXML
    public Label alertLogin;
    @FXML
    private PasswordField inputPassword;
    @FXML
    private TextField inputUsername;
    @FXML
    private AnchorPane rightPane;
    @FXML
    private AnchorPane leftPane;
    @FXML
    private AnchorPane rootPane;

    public static User user;
    public static Member member;
    public static final ObservableList<Book> books = FXCollections.observableArrayList();
    private PauseTransition debounce = new PauseTransition(Duration.millis(120));
    private Timeline centerAnimation;

    @Override
    public void initialize(URL location, ResourceBundle resuorces) {
        alertLogin.getStyleClass().add("alertLogin");
        // add drop shadow effect
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

    // method to open up Register page for new member
    @FXML
    private void onRegisterClick(ActionEvent event) {
        LibraryApplication.loadRegisterPage();
    }

    //click Back to go back to Start-page with the 2 options
    @FXML
    private void backToStart(MouseEvent event) {
        LibraryApplication.loadStartPage();
    }

    @FXML
    private void onSignInClick(ActionEvent event) {
        System.out.println("Sign In clicked");

        String username = inputUsername.getText();
        String password = inputPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            alertLogin.setText("* Please enter both username and password.");
            setAlertLoginToEmpty();
            return;
        }

        member = loadMember(username, password);
        if (member != null) {
            alertLogin.setText("Login successful!");
            setAlertLoginToEmpty();
            LibraryApplication.loadMemberPage();
        } else {
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

    private Member loadMember(String username, String password) {
        return AuthServices.memberLogin(username, password);
    }
}


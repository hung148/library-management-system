package com.main.controller;

import com.main.view.LibraryApplication;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class StartPageController implements Initializable {
    @FXML
    public Label member;
    @FXML
    public Label admin;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private AnchorPane leftPane;

    @FXML 
    private AnchorPane rightPane;

    @FXML
    private Rectangle memberSignin;
    @FXML
    private Rectangle adminSignin;

    private Timeline centerAnimation;
    
    private PauseTransition debounce = new PauseTransition(Duration.millis(120));

    // this automatically run when fxml load
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addHoverEffect(memberSignin, member);
        addHoverEffect(adminSignin, admin);
        addClickEffect(memberSignin, member);
        addClickEffect(adminSignin, admin);

        // Create drop shadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);             // Blur radius
        dropShadow.setOffsetX(5);             // Horizontal offset
        dropShadow.setOffsetY(5);             // Vertical offset
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.7));  // Shadow color with opacity
        rightPane.setEffect(dropShadow);
        
        Platform.runLater(() -> {
            LibraryApplication.stage.setMinWidth(280);
            LibraryApplication.stage.setMinHeight(400);
        });

        AnchorPane.setTopAnchor(rightPane, 0.0);
        AnchorPane.setRightAnchor(rightPane, 0.0);
        AnchorPane.setBottomAnchor(rightPane, 0.0);

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

    private void addHoverEffect(Rectangle rect, Label lbl) {
        lbl.setOnMouseEntered(e -> {
            rect.setStyle(
                "-fx-scale-x: 1.05; " +
                "-fx-scale-y: 1.05; ");
        });

        lbl.setOnMouseExited(e -> {
            rect.setStyle("");
        });
    }

    private void addClickEffect(Rectangle rect, Label lbl) {
        lbl.setOnMousePressed(e -> {
            lbl.setStyle("-fx-translate-y: 2px; ");
            rect.setStyle("-fx-translate-y: 2px; ");
        });

        lbl.setOnMouseReleased(e -> {
            lbl.setStyle("");
            rect.setStyle("");
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

        double targetWidth = Math.max(256.5, rootWidth * 0.5); // prevent too small width
        double targetHeight = Math.max(200, rootHeight * 0.7); // prevent too small height
        if (targetWidth > 325.0) {
            targetWidth = 325.0; // prevent too big
        } 
        if (targetHeight > 391) {
            targetHeight = 391.0; // prvent too big
        }
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


    //click Member to login page for Member with register
    @FXML
    private void clickMember(MouseEvent event) {
        LibraryApplication.loadLoginMemberPage();
    }

    //click Admin to login as Admin
    @FXML
    private void clickAdmin(MouseEvent event) {
        LibraryApplication.loadLoginMemberPage();
    }
}

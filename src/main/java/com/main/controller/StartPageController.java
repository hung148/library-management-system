package com.main.controller;

import com.main.view.LibraryApplication;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartPageController implements Initializable {
    @FXML
    public Label member;
    @FXML
    public Label admin;
    private Stage stage;
    private Scene scene;
    private Parent root;

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
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }

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
            stage.setMinWidth(310);
            stage.setMinHeight(400);
        });

        AnchorPane.setTopAnchor(rightPane, 0.0);
        AnchorPane.setRightAnchor(rightPane, 0.0);
        AnchorPane.setBottomAnchor(rightPane, 0.0);

        rootPane.widthProperty().addListener((obs, oldW, newW) -> {
            updateLayout(newW.doubleValue(), rootPane.getHeight());
        });

        rootPane.heightProperty().addListener((obs, oldH, newH) -> {
            updateLayout(rootPane.getWidth(), newH.doubleValue());
        });

        Platform.runLater(() -> {
            stage.fullScreenProperty().addListener((obs, wasFullScreen, isNowFullScreen) -> {
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
        if (width < 650) {
            AnchorPane.clearConstraints(rightPane);
            animateToCenter(width, height);
        } 
        else {
            rightPane.setStyle("-fx-background-color: rgb(244, 236, 236); -fx-background-radius: 0;"); 
            AnchorPane.setTopAnchor(rightPane, 0.0);
            AnchorPane.setRightAnchor(rightPane, 0.0);
            AnchorPane.setBottomAnchor(rightPane, 0.0);
        }
    }

    private void animateToCenter(double width, double height) {
        double rootWidth = width;
        double rootHeight = height;

        double targetWidth = Math.max(300, rootWidth * 0.5);  // prevent too small width
        double targetHeight = Math.max(200, rootHeight * 0.7); // prevent too small height

        double targetX = (rootWidth - targetWidth) / 2;
        double targetY = (rootHeight - targetHeight) / 2;

        // Optional: make it resizable and round corners 45, 90, 39 244, 236, 236
        rightPane.setStyle("-fx-background-color: rgb(244, 236, 236); -fx-background-radius: 20;"); 

        KeyValue widthKV = new KeyValue(rightPane.prefWidthProperty(), targetWidth, Interpolator.EASE_BOTH);
        KeyValue heightKV = new KeyValue(rightPane.prefHeightProperty(), targetHeight, Interpolator.EASE_BOTH);
        KeyValue xKV = new KeyValue(rightPane.layoutXProperty(), targetX, Interpolator.EASE_BOTH);
        KeyValue yKV = new KeyValue(rightPane.layoutYProperty(), targetY, Interpolator.EASE_BOTH);

        KeyFrame keyFrame = new KeyFrame(Duration.millis(500), widthKV, heightKV, xKV, yKV);
        Timeline timeline = new Timeline(keyFrame);

        // Wait for layout to be ready
        timeline.setOnFinished(e -> {
            Platform.runLater(() -> {
                // Force a re-layout to finalize the animation effect
                rightPane.requestLayout();
            });
        });
        
        timeline.setDelay(Duration.millis(100)); // Delay before animation starts
        timeline.play();
    }


    //click Member to login page for Member with register
    @FXML
    private void clickMember(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(LibraryApplication.class.getResource("/com/main/view/member-login.fxml"));
        root = loader.load();
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //click Admin to login as Admin
    @FXML
    private void clickAdmin(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(LibraryApplication.class.getResource("admin-login.fxml"));
        root = loader.load();
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showMember(MouseEvent event) {
        member.translateXProperty().bind(memberSignin.translateXProperty());
    }

    public void showAdmin(MouseEvent event) {
    }
}

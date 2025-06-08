//URL cssURL = getClass().getResource("/org/jamesd/examples/css/style.css");
// scene.getStylesheets().add(cssURL.toExternalForm());
package com.main.view;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;


public class LibraryApplication extends Application {

    public static Stage stage;
    public static Scene scene;
    public static FXMLLoader startPageloader = new FXMLLoader(LibraryApplication.class.getResource("start-page.fxml"));
    public static Parent startPageroot;
    public static FXMLLoader memberPageloader = new FXMLLoader(LibraryApplication.class.getResource("/com/main/view/member-login.fxml"));
    public static Parent memberPageroot;
    public static FXMLLoader adminPageloader = new FXMLLoader(LibraryApplication.class.getResource("admin-login.fxml"));
    public static Parent adminPageroot;
    public static FXMLLoader registerPageloader = new FXMLLoader(LibraryApplication.class.getResource("register-page.fxml"));
    public static Parent registerPageroot;

    @Override
    public void start(Stage stage) throws IOException  {
        LibraryApplication.stage = stage;
        try {
            startPageroot = startPageloader.load();
            memberPageroot = memberPageloader.load();
            adminPageroot = adminPageloader.load();
            registerPageroot = registerPageloader.load();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        scene = new Scene(startPageroot);
        addCSS(scene);
        stage.setWidth(650.0);
        stage.setHeight(544.0);
        stage.setScene(scene);
        stage.show();
    }

    //static so can re-use 
    public static void loadStartPage() throws IOException {
        long before = System.currentTimeMillis();
        // transition to new page animation
        FadeTransition fadeOut = new FadeTransition(Duration.millis(50), scene.getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.7);
        fadeOut.setOnFinished(e -> {
            scene.setRoot(startPageroot);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(50), startPageroot);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        fadeOut.play();
        System.out.println("Switch time: " + (System.currentTimeMillis() - before) + "ms");
        // if more than 100ms mean lag
    }

    public static void loadAdminPage() throws IOException {
        long before = System.currentTimeMillis();
        FadeTransition fadeOut = new FadeTransition(Duration.millis(50), scene.getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.7);
        fadeOut.setOnFinished(e -> {
            scene.setRoot(adminPageroot);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(50), adminPageroot);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        fadeOut.play();
        System.out.println("Switch time: " + (System.currentTimeMillis() - before) + "ms");
        // if more than 100ms mean lag
    }

    public static void loadMemberPage() throws IOException {
        long before = System.currentTimeMillis();
        FadeTransition fadeOut = new FadeTransition(Duration.millis(50), scene.getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.7);
        fadeOut.setOnFinished(e -> {
            scene.setRoot(memberPageroot);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(50), memberPageroot);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        fadeOut.play();
        System.out.println("Switch time: " + (System.currentTimeMillis() - before) + "ms");
        // if more than 100ms mean lag
    }

    public static void loadRegisterPage() throws IOException {
        long before = System.currentTimeMillis();
        FadeTransition fadeOut = new FadeTransition(Duration.millis(50), scene.getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.7);
        fadeOut.setOnFinished(e -> {
            scene.setRoot(registerPageroot);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(50), registerPageroot);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        fadeOut.play();
        System.out.println("Switch time: " + (System.currentTimeMillis() - before) + "ms");
        // if more than 100ms mean lag
    }

    public static void addCSS(Scene scene) {
        final URL cssURL = LibraryApplication.class.getResource("pageStyle.css");
        assert cssURL != null;
        scene.getStylesheets().add(cssURL.toExternalForm());
    }

}


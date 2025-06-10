//URL cssURL = getClass().getResource("/org/jamesd/examples/css/style.css");
// scene.getStylesheets().add(cssURL.toExternalForm());
package com.main.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class LibraryApplication extends Application {

    // The main application stage and scene
    public static Stage stage;
    public static Scene scene;

    // Loaders for different FXML pages
    public static FXMLLoader startPageloader = new FXMLLoader(LibraryApplication.class.getResource("start-page.fxml"));
    public static FXMLLoader memberLoginPageloader = new FXMLLoader(LibraryApplication.class.getResource("/com/main/view/member-login.fxml"));
    public static FXMLLoader adminLoginPageloader = new FXMLLoader(LibraryApplication.class.getResource("admin-login.fxml"));
    public static FXMLLoader registerPageloader = new FXMLLoader(LibraryApplication.class.getResource("register-page.fxml"));
    public static FXMLLoader memberPageloader = new FXMLLoader(LibraryApplication.class.getResource("member-page.fxml"));
    public static FXMLLoader adminPageloader = new FXMLLoader(LibraryApplication.class.getResource("admin-page.fxml"));
    
    // Root nodes (Parent) for each page
    public static Parent startPageroot;
    public static Parent memberLoginPageroot;
    public static Parent adminLoginPageroot;
    public static Parent registerPageroot;
    public static Parent memberPageroot;
    public static Parent adminPageroot;

    // A single StackPane used to hold all pages for fast switching
    public static StackPane root; 

    // List to keep track of all loaded pages
    public static ArrayList<Parent> pages = new ArrayList<>(); 

    @Override
    public void start(Stage stage) throws IOException  {
        LibraryApplication.stage = stage; // Save reference to the main stage
        root = new StackPane(); // Create the shared root pane

        try {
            // Load and store all pages only once at startup for faster switching
            startPageroot = startPageloader.load();
            pages.add(startPageroot);
            memberLoginPageroot = memberLoginPageloader.load();
            pages.add(memberLoginPageroot);
            adminLoginPageroot = adminLoginPageloader.load();
            pages.add(adminLoginPageroot);
            registerPageroot = registerPageloader.load();
            pages.add(registerPageroot);
            memberPageroot = memberPageloader.load();
            pages.add(memberPageroot);
            adminPageroot = adminPageloader.load();
            pages.add(adminPageroot);

            // Set all pages to invisible initially
            setVisible(false);

            // Add all pages to the StackPane root
            for (Parent page : pages) {
                root.getChildren().add(page);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // Show the start page
        startPageroot.toFront();
        startPageroot.setVisible(true);

        // Set the scene using the root StackPane and apply CSS
        scene = new Scene(root);
        addCSS(scene);

        // Set initial stage dimensions and show
        stage.setWidth(650.0);
        stage.setHeight(544.0);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Set all pages' visibility to the given condition
     */
    private static void setVisible(boolean con) {
        for (Parent page : pages) {
            page.setVisible(con);
        }
    }

    //static so can re-use 
    /**
     * Load the start page: hide all others, then show this
     */
    public static void loadStartPage() {
        long before = System.currentTimeMillis();
        setVisible(false);
        startPageroot.toFront();
        startPageroot.setVisible(true);
        System.out.println("Switch time: " + (System.currentTimeMillis() - before) + "ms");
        // if more than 100ms mean lag
    }

    /**
     * Load the member page
     */
    public static void loadMemberPage() {
        long before = System.currentTimeMillis();
        setVisible(false);
        memberPageroot.toFront();
        memberPageroot.setVisible(true);
        System.out.println("Switch time: " + (System.currentTimeMillis() - before) + "ms");
    }

    /**
     * Load the admin page
     */
    public static void loadAdminPage() {
        long before = System.currentTimeMillis();
        setVisible(false);
        adminPageroot.toFront();
        adminPageroot.setVisible(true);
        System.out.println("Switch time: " + (System.currentTimeMillis() - before) + "ms");
    }

    /**
     * Load the admin login page
     */
    public static void loadLoginAdminPage() {
        long before = System.currentTimeMillis();
        setVisible(false);
        adminLoginPageroot.toFront();
        adminLoginPageroot.setVisible(true);
        System.out.println("Switch time: " + (System.currentTimeMillis() - before) + "ms");
    }

    /**
     * Load the member login page
     */
    public static void loadLoginMemberPage() {
        long before = System.currentTimeMillis();
        setVisible(false);
        memberLoginPageroot.toFront();
        memberLoginPageroot.setVisible(true);
        System.out.println("Switch time: " + (System.currentTimeMillis() - before) + "ms");
    }

    /**
     * Load the register page
     */
    public static void loadRegisterPage() {
        long before = System.currentTimeMillis();
        setVisible(false);
        registerPageroot.toFront();
        registerPageroot.setVisible(true);
        System.out.println("Switch time: " + (System.currentTimeMillis() - before) + "ms");
    }

    /**
     * Apply CSS stylesheet to the scene
     */
    public static void addCSS(Scene scene) {
        final URL cssURL = LibraryApplication.class.getResource("pageStyle.css");
        assert cssURL != null;
        scene.getStylesheets().add(cssURL.toExternalForm());
    }

    public static void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);

        // Optional: apply custom style to dialog
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(LibraryApplication.class.getResource("pageStyle.css").toExternalForm());
        dialogPane.getStyleClass().add("custom-alert");
        Label content = new Label(message);
        content.setWrapText(true);
        alert.getDialogPane().setContent(content);

        alert.showAndWait();
    }

}


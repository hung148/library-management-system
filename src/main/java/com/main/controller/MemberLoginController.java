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
import com.main.respository.LibraryDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;


import java.io.IOException;
import java.net.URL;


import com.main.view.LibraryApplication;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class MemberLoginController {
    @FXML
    public Label alertLogin;
    @FXML
    private PasswordField inputPassword;
    @FXML
    private TextField inputUsername;


    private Parent root;
    private Scene scene;
    private Stage stage;
    public static User user;
    public static Member member;
    public static final ObservableList<Book> books = FXCollections.observableArrayList();

    // method to open up Register page for new member
    @FXML
    private void onRegisterClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(LibraryApplication.class.getResource("register-page.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        LibraryApplication.addCSS(scene);
        stage.setScene(scene);
        stage.show();
    }

    //click Back to go back to Start-page with the 2 options
    @FXML
    private void backToStart(MouseEvent event) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = LibraryApplication.loadStartPage();
        LibraryApplication.addCSS(scene);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onSignInClick(ActionEvent event) throws IOException {
        System.out.println("Sign In clicked");

        String username = inputUsername.getText();
        String password = inputPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            alertLogin.setText("Please enter both username and password.");
            return;
        }

        member = loadMember(username, password);
        if (member != null && member.getPassword().equals(password)) {
            alertLogin.setText("Login successful!");
            try {
                URL fxmlPath = getClass().getResource("/com/main/view/member-page.fxml");
                if (fxmlPath == null) {
                    alertLogin.setText("FXML file not found.");
                    return;
                }

                FXMLLoader loader = new FXMLLoader(fxmlPath);
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                LibraryApplication.addCSS(scene);
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
                alertLogin.setText("Failed to load member page.");
            }
        } else {
            alertLogin.setText("Invalid username or password.");
        }
    }

    private Member loadMember(String username, String password) {
        member = LibraryDAO.getMemberByUsername(username);
        if (member != null) {
            if (member.getPassword().equals(password)) {
                return member;
            } else {
                alertLogin.setText("Wrong Password");
            }
        }
        return null;
    }
}


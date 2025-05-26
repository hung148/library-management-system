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


import com.main.entity.Admin;
import com.main.respository.DBInitializer;
import com.main.respository.LibraryDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.main.view.LibraryApplication;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LoginController {
    @FXML
    private PasswordField inputPassword;
    @FXML
    private TextField inputUsername;
    private FXMLLoader loader;
    //idk if it's okay to do this but need to create admin object to carries all current info
    public static Admin staticAdmin;


    // method to open up Register page for new member
    @FXML
    private void onRegisterClick() throws IOException {
        loader = new FXMLLoader(LibraryApplication.class.getResource("register-page.fxml"));
        LibraryApplication.stage.setScene(new Scene(loader.load()));
        LibraryApplication.stage.show();
    }

    //open either admin-page or member-page accordingly
    @FXML
    private void onSignInClick() throws IOException {
        staticAdmin = loadAdmin();
        if(staticAdmin != null) {
            System.out.println("admin:" +staticAdmin.getId());
            loader = new FXMLLoader(LibraryApplication.class.getResource("admin-page.fxml"));
            LibraryApplication.stage.setScene(new Scene(loader.load()));
            LibraryApplication.stage.show();
        }
    }

    private Admin loadAdmin() {
        String username = inputUsername.getText();
        String password = inputPassword.getText();
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DBInitializer.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Admin admin = new Admin();
                admin.setId(rs.getInt("id"));
                admin.setEmail(rs.getString("email"));
                admin.setUsername(rs.getString("username"));
                admin.setName(rs.getString("name"));
                admin.setPassword(rs.getString("password"));
                admin.setType(rs.getString("type"));
                admin.setStatus(rs.getString("status"));
                admin.setBalance(rs.getDouble("balance"));
                return admin;
            } else {
                System.out.println("admin not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null ;
    }

}

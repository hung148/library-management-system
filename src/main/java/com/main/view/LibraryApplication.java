//URL cssURL = getClass().getResource("/org/jamesd/examples/css/style.css");
// scene.getStylesheets().add(cssURL.toExternalForm());
package com.main.view;

import com.main.controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;


public class LibraryApplication extends Application {
    private FXMLLoader loader;
    @Override
    public void start(Stage stage) throws IOException  {
//        FXMLLoader login_loader = new FXMLLoader(getClass().getResource("login-page.fxml"));
//        Scene scene = new Scene(login_loader.load(),390, 562);
//        URL cssURL = getClass().getResource("pageStyle.css");
//        assert cssURL != null;
//        scene.getStylesheets().add(cssURL.toExternalForm());
//        stage.setScene(scene);
//        stage.setResizable(false);
//        stage.show();
        loadAdminPage(stage);
//        loadUserPage(stage);

    }
    private void loadAdminPage(Stage stage) throws IOException {
        loader= new FXMLLoader();
        BorderPane pane = loader.load(LibraryApplication.class.getResourceAsStream(AdminNavigator.adminMenu));
        AdminController adminController = loader.getController();
        AdminNavigator.setAdminController(adminController);
        Scene adminScene = new Scene(pane);
        stage.setScene(adminScene);
        stage.show();
    }

    private void loadUserPage(Stage stage) throws IOException {
        loader= new FXMLLoader();
        BorderPane pane = loader.load(LibraryApplication.class.getResourceAsStream(UserNavigator.userMenu));
        UserController userController = loader.getController();
        UserNavigator.setUserController(userController);
        Scene userScene = new Scene(pane);
        stage.setScene(userScene);
        stage.show();
    }

}


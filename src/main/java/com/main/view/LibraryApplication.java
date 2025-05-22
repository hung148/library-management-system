//URL cssURL = getClass().getResource("/org/jamesd/examples/css/style.css");
// scene.getStylesheets().add(cssURL.toExternalForm());
package com.main.view;

import com.main.controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;


public class LibraryApplication extends Application {
    private final FXMLLoader loader = new FXMLLoader();
    private final URL cssURL = getClass().getResource("pageStyle.css");

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
//        loadAdminPage(stage);
        loadUserPage(stage);

    }
    private void loadAdminPage(Stage stage) throws IOException {
        BorderPane pane = loader.load(LibraryApplication.class.getResourceAsStream(AdminNavigator.adminMenu));
        AdminController adminController = loader.getController();
        AdminNavigator.setAdminController(adminController);
        Scene adminScene = new Scene(pane);
        adminScene.getStylesheets().add(cssURL.toExternalForm());
        stage.setScene(adminScene);
        stage.show();
    }

    private void loadUserPage(Stage stage) throws IOException {
        BorderPane pane = loader.load(LibraryApplication.class.getResourceAsStream(MemberNavigator.memberMenu));
        MemberController memberController = loader.getController();
        MemberNavigator.setUserController(memberController);
        Scene userScene = new Scene(pane);
        userScene.getStylesheets().add(cssURL.toExternalForm());
        stage.setScene(userScene);
        stage.show();
    }

}


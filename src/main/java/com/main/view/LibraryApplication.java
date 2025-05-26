//URL cssURL = getClass().getResource("/org/jamesd/examples/css/style.css");
// scene.getStylesheets().add(cssURL.toExternalForm());
package com.main.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class LibraryApplication extends Application {
    private FXMLLoader loader;
    private final URL cssURL = getClass().getResource("pageStyle.css");
    public final String loginPage = "login-page.fxml";
    public static Stage stage;


    @Override
    public void start(Stage stage) throws IOException  {
        LibraryApplication.stage = stage;
        loader = new FXMLLoader(getClass().getResource(loginPage));
        Scene scene = new Scene(loader.load());
        URL cssURL = getClass().getResource("pageStyle.css");
        assert cssURL != null;
        scene.getStylesheets().add(cssURL.toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

//        loadMemberPage(stage);


    }


    private void loadAdminPage(Stage stage) throws IOException {
        String adminMenu = "admin-page.fxml";
        loader = new FXMLLoader(getClass().getResource(adminMenu));
        Scene adminScene = new Scene(loader.load());
        assert cssURL != null;
        adminScene.getStylesheets().add(cssURL.toExternalForm());
        stage.setScene(adminScene);
        stage.show();
    }

    private void loadMemberPage(Stage stage) throws IOException {
        String membersMenu = "member-page.fxml";
        loader = new FXMLLoader(getClass().getResource(membersMenu));
        Scene membersScene = new Scene(loader.load());
        assert cssURL != null;
        membersScene.getStylesheets().add(cssURL.toExternalForm());
        stage.setScene(membersScene);
        stage.show();
    }

}


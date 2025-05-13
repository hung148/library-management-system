//URL cssURL = getClass().getResource("/org/jamesd/examples/css/style.css");
// scene.getStylesheets().add(cssURL.toExternalForm());
package com.main.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;


public class LibraryApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException  {
        FXMLLoader loader =  new FXMLLoader(LibraryApplication.class.getResource("login-page.fxml"));
        Scene scene = new Scene(loader.load());
        URL cssURL = getClass().getResource("pageStyle.css");
        scene.getStylesheets().add(cssURL.toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}


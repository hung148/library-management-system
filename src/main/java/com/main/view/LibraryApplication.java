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

    public static Stage stage;



    @Override
    public void start(Stage stage) throws IOException  {
        LibraryApplication.stage = stage;
        Scene scene = loadStartPage();
        addCSS(scene);
        stage.setScene(scene);
        stage.show();

    }

    //static so can re-use when using back or cancel button
    public static Scene loadStartPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(LibraryApplication.class.getResource("start-page.fxml"));
        return new Scene(loader.load());

    }

    public static void addCSS(Scene scene) {
        final URL cssURL = LibraryApplication.class.getResource("pageStyle.css");
        assert cssURL != null;
        scene.getStylesheets().add(cssURL.toExternalForm());
    }

}


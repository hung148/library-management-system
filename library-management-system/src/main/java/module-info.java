module com.main {
    requires java.sql;
    
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires javafx.graphics;

    opens com.main.view to javafx.fxml;
    opens com.main.control to javafx.fxml;
    exports com.main.control;
    exports com.main.view;
    exports com.main.model;
}

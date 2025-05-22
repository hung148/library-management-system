module com.main {  
    requires java.sql;  
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires javafx.graphics;
    requires java.desktop;

    opens com.main.view to javafx.fxml;
    opens com.main.controller to javafx.fxml;
    exports com.main.controller;
    exports com.main.view;
    exports com.main.controller.admin;
    opens com.main.controller.admin to javafx.fxml;
    opens com.main.controller.user to javafx.fxml;
    exports com.main.controller.user;
}

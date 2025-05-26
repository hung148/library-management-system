module com.main {  
    requires java.sql;  
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires javafx.graphics;
    requires java.desktop;
    requires annotations;

    opens com.main.view to javafx.fxml;
    opens com.main.controller to javafx.fxml;
    exports com.main.controller;
    exports com.main.view;
    exports com.main.entity;
    exports com.main.controller.admin;
    exports com.main.controller.member;
    opens com.main.controller.admin to javafx.fxml;
    opens com.main.controller.member to javafx.fxml;
}

module com.main {  
    requires java.sql;  
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires jbcrypt;
    requires org.apache.commons.text;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires javafx.graphics;
    requires java.desktop;
    requires annotations;
    requires javafx.base;
    requires jdk.incubator.vector;

    opens com.main.view to javafx.fxml;
    opens com.main.controller to javafx.fxml;
    exports com.main.controller;
    exports com.main.view;
    exports com.main.entity;
    exports com.main.controller.admin;
    exports com.main.controller.member;
    exports com.main.test; //for testing purpose, delete once done
    opens com.main.controller.admin to javafx.fxml;
    opens com.main.controller.member to javafx.fxml;
    exports com.main.services;
    exports com.main.app;
    exports com.main.respository;
}



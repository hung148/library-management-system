package com.main.app;

import com.main.respository.DBInitializer;
import com.main.respository.LibraryDAO;
import com.main.view.LibraryApplication;

public class Main {
    public static void main(String[] args) {
        // initialize database
        DBInitializer.initializeDB();
        // add first addmin
        if (LibraryDAO.getAdminList().length == 0) {
            LibraryDAO.addAdmin(
                "firstAdmin@gmail.com", 
                "firstAdminpass", // this may be bad for sercurity so require admin to change password on first log in
                "firstAdminUsername", 
                "firstAdminname", 
                0);
        }
        // You're calling Application.launch(args) from a class (Main) that does 
        // not extend Application. JavaFX expects the launch() call to come from 
        // the class that extends Application — or at least be referenced correctly.
        // In your Main.java, you must pass the actual Application subclass to the launch() method using reflection:
        LibraryApplication.launch(LibraryApplication.class, args);
    }
}

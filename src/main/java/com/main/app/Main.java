package com.main.app;

import java.sql.SQLException;

import com.main.respository.DBInitializer;
import com.main.respository.LibraryDAO;
import com.main.view.LibraryApplication;

public class Main {
    public static void main(String[] args) {
        // initialize database
        DBInitializer.initializeDB();
        // add first addmin
        if (LibraryDAO.getAdminList().length == 0) {
            try {
                LibraryDAO.addAdmin(
                "firstAdmin@gmail.com", 
                "firstAdminpass", // this may be bad for sercurity so require admin to change password on first log in
                "firstAdminUsername", 
                "firstAdminname", 
                0);
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        LibraryApplication.launch(LibraryApplication.class, args);
    }
}

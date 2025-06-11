package com.main.app;

import java.sql.SQLException;

import com.main.respository.DBInitializer;
import com.main.respository.LibraryDAO;
import com.main.view.LibraryApplication;

public class Main {
    public static String firstAdminGmail = "firstAdmin@gmail.com";
    public static String firstAdminPass = "firstAdminpass"; // require admin to change this password on log in
    public static String firstAdminUsername = "firstAdminUsername";
    public static String firstAdminName = "firstAdminname"; 
    public static double firstAdminBalance = 0;
    public static void main(String[] args) {
        // initialize database
        DBInitializer.initializeDB();
        // add first addmin
        if (LibraryDAO.getAdminList().length == 0) {
            try {
                LibraryDAO.addAdmin(
                firstAdminGmail,
                firstAdminPass, 
                firstAdminUsername,
                firstAdminName,
                firstAdminBalance);
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        LibraryApplication.launch(LibraryApplication.class, args);
    }
}

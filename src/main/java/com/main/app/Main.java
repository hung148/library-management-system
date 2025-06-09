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

        LibraryApplication.launch(LibraryApplication.class, args);
    }
}

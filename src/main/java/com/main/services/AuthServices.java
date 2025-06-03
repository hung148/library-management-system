package com.main.services;

import org.mindrot.jbcrypt.BCrypt;
public class AuthServices {
    public static String generateHashedPassword(String plainPass) {
        return BCrypt.hashpw(plainPass, BCrypt.gensalt());
    }

    // user input password
    // hash pasword is stored in database not the real password
    // use this function to check if user enter the right password
    public static Boolean checkPassword(String inputPass, String storedHash) {
        return BCrypt.checkpw(inputPass, storedHash);
    }

}

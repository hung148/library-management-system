package com.main.services;

import org.mindrot.jbcrypt.BCrypt;
public class AuthServices {
    public static String generateHashedPassword(String plainPass) {
        return BCrypt.hashpw(plainPass, BCrypt.gensalt());
    }

    public static Boolean checkPassword(String inputPass, String storedHash) {
        return BCrypt.checkpw(inputPass, storedHash);
    }

}

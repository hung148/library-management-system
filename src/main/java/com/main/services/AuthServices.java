package com.main.services;

import org.mindrot.jbcrypt.BCrypt;

import com.main.entity.Admin;
import com.main.entity.Member;
import com.main.respository.LibraryDAO;
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

    // member log in, return member if success and null if not
    public static Member memberLogin(String username, String password) {
        for (Member m : LibraryDAO.getMemberList()) {
            if (m.getUsername().equals(username.trim())) {
                if (checkPassword(password.trim(), m.getHashPassword())) {
                    System.out.println("Member log in successfully");
                    return m;
                } else {
                    System.out.println("Wrong password");
                    return null;
                }
            }
        }
        System.out.println("member not exist in database");
        return null;
    }
    public static Admin adminLogin(String username, String password) {
        for (Admin a : LibraryDAO.getAdminList()) {
            if (a.getUsername().equals(username.trim())) {
                if (checkPassword(password.trim(), a.getHashPassword())) {
                    System.out.println("Admin log in successfully");
                    return a;
                } else {
                    System.out.println("Wrong password");
                    return null;
                }
            }
        }
         System.out.println("member not exist in database");
        return null;
    }
    // register 

}

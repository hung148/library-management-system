package com.main.model;

import java.util.Scanner;

public class TestDB {
    public static void main(String[] args) {
        LibraryDAO.createDB();
        LibraryDAO.resetDB();
        // boolean loop = true;
        Scanner scan = new Scanner(System.in);
        System.out.println("Test library database");
        // login / signup test 
        boolean running = true;
        while(running) {
            System.out.println("1. Sign up");
            System.out.println("2. Login");
            System.out.println("3. Quit");
            System.out.print("Enter your option: ");
            int option = Integer.parseInt(scan.nextLine());
            User user = null;
            boolean login = false;
            switch(option) {
                case 1:
                    System.out.print("Enter your name: ");
                    String name = scan.nextLine();
                    System.out.print("Enter your email: ");
                    String email = scan.nextLine();
                    System.out.print("Enter your username: ");
                    String username = scan.nextLine();
                    System.out.print("Enter your password: ");
                    String password = scan.nextLine();
                    System.out.print("Are you admin or member(Type a or m): ");
                    String type = scan.nextLine();
                    if (type.equals("a")) {
                        type = "admin";                    
                    } else {
                        type = "member";
                    }
                    LibraryDAO.addUser(email, password, username, name, type, "normal", option);
                    System.out.println("Login: ");
                    System.out.print("Type(admin or memeber): ");
                    String checkType = scan.nextLine();
                    System.out.print("Username: ");
                    String checkUserName = scan.nextLine();
                    System.out.print("Password: ");
                    String checkPassword = scan.nextLine();
                    User[] users;
                    if (checkType.equals("admin")) {
                        users = LibraryDAO.getAdmins();
                    } else {
                        users = LibraryDAO.getMembers();
                    }
                    if (users.length <= 0) {
                        System.out.println("No user in database");
                    } else {
                        boolean found = false;
                        for (User checkuser : users) {
                            if (checkuser.getUsername().equals(checkUserName)) {
                                if (checkuser.getPassword().equals(checkPassword)) {
                                    System.out.println("Login successfully");
                                    login = true;
                                    user = checkuser; // Assign the logged-in user
                                    found = true;
                                    break;
                                }
                            }
                        }
                        if (!found) {
                            System.out.println("Login failed");
                        }
                    }
                    break;
                case 2:
                    System.out.println("Login: ");
                    System.out.print("Type(admin or memeber): ");
                    String checkTypeLogin = scan.nextLine();
                    System.out.print("Username: ");
                    String checkUserNameLogin = scan.nextLine();
                    System.out.print("Password: ");
                    String checkPasswordLogin = scan.nextLine();
                    User[] checkusers;
                    if (checkTypeLogin.equals("admin")) {
                        checkusers = LibraryDAO.getAdmins();
                    } else {
                        checkusers = LibraryDAO.getMembers();
                    }
                    if (checkusers.length <= 0) {
                        System.out.println("No user in database");
                    } else {
                        boolean found = false;
                        for (User checkuser : checkusers) {
                            if (checkuser.getUsername().equals(checkUserNameLogin)) {
                                if (checkuser.getPassword().equals(checkPasswordLogin)) {
                                    System.out.println("Login successfully");
                                    login = true;
                                    user = checkuser; // Assign the logged-in user
                                    found = true;
                                    break;
                                }
                            }
                        }
                        if (!found) {
                            System.out.println("Login failed");
                        }
                    }
                    break;
                case 3:
                    running = false;
                    break;
            }

            while (login) {
                if (user.getType().equals("admin")) {
                    // add book 
                    // remove book
                    // update book 
                    // view books
                    // view borrow books 
                    // add/ remove / update genre 
                    // view members
                    // delete members
                    // change member status 
                    // view personal info 
                } else { // member
                    // view book
                    // borrow book
                    // return book
                    // add balance 
                    // view personal info
                    // pay fine 
                }
            }
        }
        
        scan.close();
        LibraryDAO.resetDB();
    }
}
/*

*/

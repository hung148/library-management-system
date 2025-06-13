package com.main.respository;

import java.sql.*;

public class DBInitializer {
    private static String url = "jdbc:sqlite:library.db";
    // set up and initialize database
    public static void initializeDB() {
        // db file will be created in the project root if not exist
        try (Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()) {
            initializeUserTable(stmt);
            initializeBookTable(stmt);
            addLoginState(stmt);
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }

    // get connection
    public static Connection connect() {
        Connection conn = null;
        try {
            // db file will be created in the project root if not exist
            initializeDB();
            conn = DriverManager.getConnection(url);
            System.out.println("Connection established.");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
        return conn;
    }

    private static void initializeUserTable(Statement stmt) throws SQLException {
        String sql = ("CREATE TABLE IF NOT EXISTS users (" + 
        "id INTEGER PRIMARY KEY AUTOINCREMENT," + 
        "email TEXT NOT NULL UNIQUE," +
        "password TEXT NOT NULL," + 
        "username TEXT NOT NULL UNIQUE," + 
        "name TEXT NOT NULL," +
        "type TEXT CHECK(type IN ('admin', 'member')) NOT NULL," +
        "status TEXT CHECK(status IN ('normal', 'suspended')) NOT NULL DEFAULT 'NORMAL'," +
        "balance REAL NOT NULL DEFAULT 0.0," +
        "libraryID TEXT UNIQUE)");
        try {
            stmt.execute(sql);
            System.out.println("User table created or already exist.");
        } catch (SQLException e) {
            throw e;
        }
    }
    // add log in state to user boolean
    public static void addLoginState(Statement stmt) {
        // Check if the log in state exist
        String pramaQuery = "PRAGMA table_info(users)";
        Boolean loginStateExists = false;
        try (ResultSet rs = stmt.executeQuery(pramaQuery)) {
            while (rs.next()) {
                String existColumn = rs.getString("name");
                if (existColumn.equalsIgnoreCase("login")) {
                    loginStateExists = true;
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // Add the column if it does not exist
        if (!loginStateExists) {
            String alterQuery = "ALTER TABLE users ADD COLUMN login BOOLEAN DEFAULT 0";
            try {
                stmt.executeUpdate(alterQuery);
                System.out.println("Log in state is added to users table");
            }  catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // initialize book table 
    private static void initializeBookTable(Statement stmt) throws SQLException {
    	String sql = ("CREATE TABLE IF NOT EXISTS bookTable (" +
	    "isbn TEXT UNIQUE PRIMARY KEY," +
    	"title TEXT NOT NULL," + 
	    "author TEXT NOT NULL," +
    	"publisher TEXT NOT NULL," +
    	"totalCopies INTEGER," +
	    "availableCopies INTEGER)");
    	
    	try {
            stmt.execute(sql);
            System.out.println("bookTable table created or already exist.");
        } catch (SQLException e) {
            throw e;
        }
    }

    // initialize borrowed book table
    private static void initializeBorrowedBookTable(Statement stmt) throws SQLException {
    	String sql = ("CREATE TABLE IF NOT EXISTS borrowedBook (" +
	    "id INTEGER UNIQUE PRIMARY KEY," +
	    "memberId INTEGER NOT NULL," +             
	    "isbn TEXT NOT NULL," +    
    	"status TEXT CHECK(status IN ('pending', 'late', 'returned', 'lost')) NOT NULL," + 
    	"dateIssued TEXT NOT NULL," +
    	"deadline TEXT NOT NULL," +
    	"returnedDate TEXT," + 
    	"lateFine REAL NOT NULL DEFAULT 0.0," + 
    	"lostFine REAL NOT NULL," + 
    	"FOREIGN KEY(isbn) REFERENCES bookTable(isbn)," +
	    "FOREIGN KEY(memberId) REFERENCES users(id))");
    	
    	try {
            stmt.execute(sql);
            System.out.println("borrowedBook table created or already exist.");
        } catch (SQLException e) {
            throw e;
        }
    }
}

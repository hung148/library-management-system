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
        "balance REAL NOT NULL DEFAULT 0.0)");
        try {
            stmt.execute(sql);
            System.out.println("User table created or already exist.");
        } catch (SQLException e) {
            throw e;
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
}

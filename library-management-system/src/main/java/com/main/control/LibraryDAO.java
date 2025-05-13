package com.main.control;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.main.model.*;

public class LibraryDAO {
    private final static String url = "jdbc:sqlite:library.db";
    
    // create database 
    public static void createDB() {
        
        try(Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()) {
            createUserTable(stmt);
            createBookTable(stmt);
            createGenreTable(stmt);
            createBookGenreTable(stmt);
            createBorrowBookTable(stmt);
            System.out.println("Library database created!");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void createBorrowBookTable(Statement stmt) throws SQLException {
        String borrowBookTable = 
            "CREATE TABLE IF NOT EXISTS borrowed_books (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "book_isbn TEXT NOT NULL, " +
            "member_id INTEGER NOT NULL, " +
            "borrow_date TEXT NOT NULL, " +
            "deadline TEXT NOT NULL, " +
            "actual_return_date TEXT, " + // can be NULL if not returned yet
            "status TEXT NOT NULL CHECK (" +
            "status IN (" +
            "'pending', 'returned', 'pending late', 'returned late', 'lost'" +
            ")), " +
            "fine_late REAL DEFAULT 0.0, " +
            "fine_lost REAL DEFAULT 0.0, " +
            "FOREIGN KEY (book_isbn) REFERENCES books(isbn) ON DELETE RESTRICT, " +
            "FOREIGN KEY (member_id) REFERENCES users(id) ON DELETE RESTRICT" +
            ");";
        try {
            stmt.executeUpdate(borrowBookTable);
        } catch (SQLException e) {
            throw e;
        }
    }
    private static void createUserTable(Statement stmt) throws SQLException {
        String userTable = ("CREATE TABLE IF NOT EXISTS " + 
        "users (id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
        "email TEXT NOT NULL UNIQUE, " + 
        "password TEXT NOT NULL, " + 
        "username TEXT NOT NULL UNIQUE, " +
        "name TEXT NOT NULL, " + 
        "type TEXT NOT NULL CHECK(type IN ('admin', 'member')), " + 
        "status TEXT NOT NULL CHECK(status IN ('normal', 'suspended')), " + 
        "balance DOUBLE DEFAULT 0.0)");
        try {
            stmt.executeUpdate(userTable);
        } catch (SQLException e) {
            throw e;
        }
    }

    private static void createGenreTable(Statement stmt) throws SQLException {
        String genreTable = ("CREATE TABLE IF NOT EXISTS " + "genres (id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
        "name TEXT UNIQUE )");
        try {
            stmt.execute(genreTable);
        } catch (SQLException e) {
            throw e;
        }
    }

    private static void createBookTable(Statement stmt) throws SQLException {
        String bookTable = ("CREATE TABLE IF NOT EXISTS " +
        "books (isbn TEXT PRIMARY KEY, title TEXT NOT NULL, author TEXT NOT NULL, " +
        "publisher TEXT NOT NULL, " + 
        "total_copies INTEGER NOT NULL DEFAULT 0 CHECK (total_copies >= 0), " + 
        "available_copies INTEGER NOT NULL DEFAULT 0 CHECK (available_copies >= 0)" + ")");
        try {
            stmt.execute(bookTable);
        } catch (SQLException e) {
            throw e;
        }
    }

    // relationship between book and genre a book can have many genre 
    private static void createBookGenreTable(Statement stmt) throws SQLException {
        String bookGenreTable = ("CREATE TABLE IF NOT EXISTS " + "book_genres (book_isbn TEXT, " + 
        "genre_id INTEGER, " + "PRIMARY KEY (book_isbn, genre_id), " + 
        "FOREIGN KEY (book_isbn) REFERENCES books(isbn) ON DELETE CASCADE, " + 
        "FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE" + ")");
        try {
            stmt.execute(bookGenreTable);
        } catch (SQLException e) {
            throw e;
        }
    }

    // Add user
    public static void addUser(String email, String password, String username, String name, String type, String status, double balance) {
        String sql = "INSERT INTO users (email, password, username, name, type, status, balance) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try(Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // add user
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.setString(3, username);
            pstmt.setString(4, name);
            pstmt.setString(5, type);
            pstmt.setString(6, status);
            pstmt.setDouble(7, balance);
            pstmt.executeUpdate();
            System.out.println("User added successfully");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Remove user
    public static void removeUser(User user) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             pstmt.setInt(1, user.getId());
             int affectedRows = pstmt.executeUpdate();
             if (affectedRows > 0) {
                System.out.println("User removed successfully");
             } else {
                System.out.println("User not found.");
             }
        } catch (SQLException e) {
            System.out.println("Error removing user: " + e.getMessage());
        }
    }

    // Update user
    public static void updateUser(int oldId, String email, String password, String username, String name, String type, String status, double balance) {
        // id, email, password, username, name, type, status, balance
        String sql = "UPDATE users SET email = ?, password = ?, username = ?, name = ?, type = ?, status = ?, balance = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.setString(3, username);
            pstmt.setString(4, name);
            pstmt.setString(5, type);
            pstmt.setString(6, status);
            pstmt.setDouble(7, balance);
            pstmt.setInt(8, oldId);
            int affected = pstmt.executeUpdate();

            if (affected > 0) {
                System.out.println("user updated successfully");
            } else {
                System.out.println("No user found with the given ID");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // get admins
    public static Admin[] getAdmins() {
        List<Admin> adminList = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE type = 'admin'";

        try (Connection conn = DriverManager.getConnection(url);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Admin admin = new Admin(
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("username"),
                    rs.getString("name"),
                    rs.getInt("id"),
                    rs.getDouble("balance")
                );
                adminList.add(admin);
            }

        } catch (SQLException e) {
            System.out.println("Error getting admins: " + e.getMessage());
        }
        return adminList.toArray(new Admin[0]);
    }

    // get members
    public static Member[] getMembers() {
        List<Member> memberList = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE type = 'member'";

        try (Connection conn = DriverManager.getConnection(url);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Member member = new Member(
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("username"),
                    rs.getString("name"),
                    rs.getInt("id"),
                    rs.getString("status"),
                    rs.getDouble("balance")
                );
                memberList.add(member);
            }

        } catch (SQLException e) {
            System.out.println("Error getting members: " + e.getMessage());
        }

        return memberList.toArray(new Member[0]);
    }

    // borrow book 
    public static void borrowBook(int user_id, String book_isbn, int duration) {
        if (duration < 0) {
            System.out.println("Invalid duration");
            return;
        }
        String checkBookSql = "SELECT available_copies FROM books WHERE isbn = ?";
        String insertBorrowSql = "INSERT INTO borrowed_books (book_isbn, member_id, borrow_date, deadline, status) VALUES (?, ?, ?, ?, ?)";
        String updateBookSql = "UPDATE books SET available_copies = available_copies - 1 WHERE isbn = ?";

        try (Connection conn = DriverManager.getConnection(url)) {
            conn.setAutoCommit(false); // Start transaction
            // Normally, each SQL statement is automatically committed (saved) 
            // to the database after it runs. By calling setAutoCommit(false), you tell the database: "Wait — don’t save any of the changes yet. I’ll tell you when I’m done."
            /*
            You do three steps:
            Check available copies.
            Insert a new borrowed_books record.
            Update the book's available_copies.
            If one of them fails and auto-commit is on, the others still apply — and your data becomes inconsistent.

            With auto-commit off:

            You can do all 3 steps.
            Then call conn.commit() to apply them all at once.
            If any step fails, you can call conn.rollback() to undo all changes.
             */

             // 1. Check if the book has available copies
            int availableCopies = 0;
            try (PreparedStatement pstmt = conn.prepareStatement(checkBookSql)) {
                pstmt.setString(1, book_isbn);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        availableCopies = rs.getInt("available_copies");
                        if (availableCopies <= 0) {
                            System.out.println("No available copies for book ISBN: " + book_isbn);
                            return;
                        }
                    } else {
                        System.out.println("Book not found with ISBN: " + book_isbn);
                        return;
                    }
                }
            }

            // 2. Insert into borrowed_books
            String borrowDate = java.time.LocalDate.now().toString();
            String deadline = java.time.LocalDate.now().plusDays(duration).toString();

            try (PreparedStatement pstmt = conn.prepareStatement(insertBorrowSql)) {
                pstmt.setString(1, book_isbn);
                pstmt.setInt(2, user_id);
                pstmt.setString(3, borrowDate);
                pstmt.setString(4, deadline);
                pstmt.setString(5, "pending");
                pstmt.executeUpdate();
            }

            // 3. Decrement available_copies
            try (PreparedStatement pstmt = conn.prepareStatement(updateBookSql)) {
                pstmt.setString(1, book_isbn);
                pstmt.executeUpdate();
            }

            conn.commit();
            System.out.println("Book borrowed successfully");
        } catch (SQLException e) {
            System.out.println("Error borrowing book: " + e.getMessage());
        }
    }

    // delete borrow book 
    public static void deleteBorrowBook(int user_id, String book_isbn) {
        String checkBorrowSql = "SELECT actual_return_date FROM borrowed_books WHERE member_id = ? AND book_isbn = ?";
        String deleteBorrowSql = "DELETE FROM borrowed_books WHERE member_id = ? AND book_isbn = ?";
        String updateBookSql = "UPDATE books SET available_copies = available_copies + 1 WHERE isbn = ?";

        try (Connection conn = DriverManager.getConnection(url)) {
            conn.setAutoCommit(false); // Start transaction

            boolean shouldRestoreCopy = false;

            // 1. Check if book was returned or not
            try (PreparedStatement pstmt = conn.prepareStatement(checkBorrowSql)) {
                pstmt.setInt(1, user_id);
                pstmt.setString(2, book_isbn);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String returnDate = rs.getString("actual_return_date");
                        if (returnDate == null || returnDate.isEmpty()) {
                            shouldRestoreCopy = true; // Book not returned yet, need to increment available_copies
                        }
                    } else {
                        System.out.println("No borrow record found for user and book.");
                        return;
                    }
                }
            }

            // 2. Delete the borrow record
            try (PreparedStatement pstmt = conn.prepareStatement(deleteBorrowSql)) {
                pstmt.setInt(1, user_id);
                pstmt.setString(2, book_isbn);
                pstmt.executeUpdate();
            }

            // 3. Restore the available copy if the book wasn't returned
            if (shouldRestoreCopy) {
                try (PreparedStatement pstmt = conn.prepareStatement(updateBookSql)) {
                    pstmt.setString(1, book_isbn);
                    pstmt.executeUpdate();
                }
            }

            conn.commit();
            System.out.println("Borrow record deleted successfully.");

        } catch (SQLException e) {
            System.out.println("Error deleting borrow record: " + e.getMessage());
        }
    }

    // get every borrow book
    public static BorrowedBook[] getBorrowedBooks() {
        String sql = "SELECT * FROM borrowed_books";
        List<BorrowedBook> list = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                BorrowedBook book = new BorrowedBook(
                    rs.getInt("id"), 
                    rs.getString("book_isbn"),
                    rs.getInt("member_id"),
                    rs.getString("borrow_date"),
                    rs.getString("deadline"),
                    rs.getString("actual_return_date"),
                    rs.getString("status"),
                    rs.getDouble("fine_late"),
                    rs.getDouble("fine_lost"));
                list.add(book);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching borrowed books: " + e.getMessage());
        }

        return list.toArray(new BorrowedBook[0]);
    }

    // get borrow book of member 
    public static BorrowedBook[] gBorrowedBooksMember(int user_id) {
        String sql = "SELECT * FROM borrowed_books WHERE member_id = ?";
        List<BorrowedBook> list = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, user_id);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    BorrowedBook book = new BorrowedBook(
                    rs.getInt("id"), 
                    rs.getString("book_isbn"),
                    rs.getInt("member_id"),
                    rs.getString("borrow_date"),
                    rs.getString("deadline"),
                    rs.getString("actual_return_date"),
                    rs.getString("status"),
                    rs.getDouble("fine_late"),
                    rs.getDouble("fine_lost"));
                    list.add(book);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving borrow records for user " + user_id + ": " + e.getMessage());
        }

        return list.toArray(new BorrowedBook[0]);
    }

    // pay fine
    public static void payFineLate(int user_id, String book_isbn) {
        String selectFineSQL = "SELECT fine_late FROM borrowed_books WHERE member_id = ? AND book_isbn = ?";
        String updateBalanceSQL = "UPDATE users SET balance = balance - ? WHERE id = ?";
        String clearFineSQL = "UPDATE borrowed_books SET fine_late = 0.0 WHERE member_id = ? AND book_isbn = ?";
        String getBalanceSQL = "SELECT balance FROM users WHERE id = ?";
        String suspendUserSQL = "UPDATE users SET status = 'suspended' WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url)) {
            conn.setAutoCommit(false); // Start transaction

            double fineLate = 0.0;

            // 1. Get fine_late
            try (PreparedStatement pstmt = conn.prepareStatement(selectFineSQL)) {
                pstmt.setInt(1, user_id);
                pstmt.setString(2, book_isbn);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        fineLate = rs.getDouble("fine_late");
                    } else {
                        System.out.println("No borrow record found.");
                        return;
                    }
                }
            }

            // 2. Update user balance
            try (PreparedStatement pstmt = conn.prepareStatement(updateBalanceSQL)) {
                pstmt.setDouble(1, fineLate);
                pstmt.setInt(2, user_id);
                pstmt.executeUpdate();
            }

            // 3. Set fine_late to 0
            try (PreparedStatement pstmt = conn.prepareStatement(clearFineSQL)) {
                pstmt.setInt(1, user_id);
                pstmt.setString(2, book_isbn);
                pstmt.executeUpdate();
            }

            // 4. Check balance
            double balance = 0.0;
            try (PreparedStatement pstmt = conn.prepareStatement(getBalanceSQL)) {
                pstmt.setInt(1, user_id);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        balance = rs.getDouble("balance");
                    }
                }
            }

            // 5. Suspend user if balance < 0
            if (balance < 0) {
                try (PreparedStatement pstmt = conn.prepareStatement(suspendUserSQL)) {
                    pstmt.setInt(1, user_id);
                    pstmt.executeUpdate();
                    System.out.println("User suspended due to negative balance.");
                }
            }

            conn.commit(); // All changes are saved
            System.out.println("Late fine paid and balance updated.");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void payFineLost(int user_id, String book_isbn) {
        String selectFineSQL = "SELECT fine_lost FROM borrowed_books WHERE member_id = ? AND book_isbn = ?";
        String updateBalanceSQL = "UPDATE users SET balance = balance - ? WHERE id = ?";
        String clearFineSQL = "UPDATE borrowed_books SET fine_lost = 0.0 WHERE member_id = ? AND book_isbn = ?";
        String getBalanceSQL = "SELECT balance FROM users WHERE id = ?";
        String suspendUserSQL = "UPDATE users SET status = 'suspended' WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url)) {
            conn.setAutoCommit(false); // Start transaction

            double fineLost = 0.0;

            // 1. Get fine_lost
            try (PreparedStatement pstmt = conn.prepareStatement(selectFineSQL)) {
                pstmt.setInt(1, user_id);
                pstmt.setString(2, book_isbn);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        fineLost = rs.getDouble("fine_lost");
                    } else {
                        System.out.println("No borrow record found.");
                        return;
                    }
                }
            }

            // 2. Update user balance
            try (PreparedStatement pstmt = conn.prepareStatement(updateBalanceSQL)) {
                pstmt.setDouble(1, fineLost);
                pstmt.setInt(2, user_id);
                pstmt.executeUpdate();
            }

            // 3. Set fine_lost to 0
            try (PreparedStatement pstmt = conn.prepareStatement(clearFineSQL)) {
                pstmt.setInt(1, user_id);
                pstmt.setString(2, book_isbn);
                pstmt.executeUpdate();
            }

            // 4. Check balance
            double balance = 0.0;
            try (PreparedStatement pstmt = conn.prepareStatement(getBalanceSQL)) {
                pstmt.setInt(1, user_id);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        balance = rs.getDouble("balance");
                    }
                }
            }

            // 5. Suspend user if balance < 0
            if (balance < 0) {
                try (PreparedStatement pstmt = conn.prepareStatement(suspendUserSQL)) {
                    pstmt.setInt(1, user_id);
                    pstmt.executeUpdate();
                    System.out.println("User suspended due to negative balance.");
                }
            }

            conn.commit(); // All changes are saved
            System.out.println("Lost fine paid and balance updated.");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // member return book function
    //  It updates the borrow status, sets the return date, and increases the 
    // available copies of the returned book
    // deadline and CURRENT_DATE are both stored in a comparable format (like YYYY-MM-DD)
    public static void returnBook(int user_id, String book_isbn) {
        String updateReturnSQL = "UPDATE borrowed_books " +
            "SET actual_return_date = CURRENT_DATE, " +
            "status = CASE " +
            "WHEN deadline < CURRENT_DATE THEN 'returned late' " +
            "ELSE 'returned' END " +
            "WHERE member_id = ? AND book_isbn = ? AND status IN ('pending', 'pending late')";

        String updateBookSQL = "UPDATE books SET available_copies = available_copies + 1 WHERE isbn = ?";

        try (Connection conn = DriverManager.getConnection(url)) {
            conn.setAutoCommit(false); // start transaction

            // 1. Update borrowed_books status and return date
            try (PreparedStatement pstmt = conn.prepareStatement(updateReturnSQL)) {
                pstmt.setInt(1, user_id);
                pstmt.setString(2, book_isbn);
                int affected = pstmt.executeUpdate();
                if (affected == 0) {
                    System.out.println("No matching borrowed book found to return.");
                    conn.rollback();
                    return;
                }
            }

            // 2. Increase available_copies
            try (PreparedStatement pstmt = conn.prepareStatement(updateBookSQL)) {
                pstmt.setString(1, book_isbn);
                pstmt.executeUpdate();
            }

            conn.commit();
            System.out.println("Book returned successfully.");
        } catch (SQLException e) {
            System.out.println("Error returning book: " + e.getMessage());
        }
    }


    public static void addBook(Book book, Genre[] genres) {
        // book is not valid 
        if(!bookValidation(book)) {
            return;
        }

        String checkSql = "SELECT COUNT(*) FROM books WHERE isbn = ?";
        String sql = "INSERT INTO books (isbn, title, author, publisher, total_copies, available_copies) VALUES (?, ?, ?, ?, ?, ?)";
        
        try(Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            // Check if ISBN exists
            checkStmt.setString(1, book.getIsbn());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("ISBN already exists. Book not added.");
                return;
            }

            // add book
            pstmt.setString(1, book.getIsbn());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getAuthor());
            pstmt.setString(4, book.getPublisher());
            pstmt.setInt(5, book.getTotalCopies());
            pstmt.setInt(6, book.getAvailableCopies());
            pstmt.executeUpdate();
            System.out.println("Book added successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // add genre 
        String checkGenreSql = "SELECT COUNT(*) FROM genres WHERE id = ?";
        String genreSql = "INSERT INTO genres(name) VALUES (?)";
        try(Connection conn = DriverManager.getConnection(url);
            PreparedStatement genrePstmt = conn.prepareStatement(genreSql);
            PreparedStatement checkGenrePstmt = conn.prepareStatement(checkGenreSql)) {
            for (Genre genre : genres) {
                // check genre exist 
                checkGenrePstmt.setInt(1, genre.getGenreId());
                ResultSet rs = checkGenrePstmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("genre already exists. genre not added.");
                    continue;
                } else {
                    genrePstmt.setString(1, genre.getName());
                    genrePstmt.executeUpdate();
                    System.out.println("genre added successfully");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // add book_genre 
        String checkBookGenreSql = "SELECT COUNT(*) FROM book_genres WHERE book_isbn = ? AND genre_id = ?";
        String bookGenreSql = "INSERT INTO book_genres(book_isbn, genre_id) VALUES (?, ?)";
        try(Connection conn = DriverManager.getConnection(url);
            PreparedStatement bookgenrePstmt = conn.prepareStatement(bookGenreSql);
            PreparedStatement checkBookGenrePstmt = conn.prepareStatement(checkBookGenreSql)) {
            for (Genre genre : genres) {
                checkBookGenrePstmt.setString(1, book.getIsbn());
                checkBookGenrePstmt.setInt(2, genre.getGenreId());
                ResultSet rs = checkBookGenrePstmt.executeQuery();

                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("book_genre already exists for: " + book.getIsbn() + " - " + genre.getName());
                    continue;
                }

                bookgenrePstmt.setString(1, book.getIsbn());
                bookgenrePstmt.setInt(2, genre.getGenreId());
                bookgenrePstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    } 

    // remove book 
    public static void removeBook(Book book) {
        if (!bookValidation(book)) {
            return;
        }

        String sql = "DELETE FROM books WHERE isbn = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             pstmt.setString(1, book.getIsbn());
             int affectedRows = pstmt.executeUpdate();
             if (affectedRows > 0) {
                System.out.println("Book removed successfully");
             } else {
                System.out.println("Book not found.");
             }
        } catch (SQLException e) {
            System.out.println("Error removing book: " + e.getMessage());
        }
    }

    // update book
    public static void updateBook(String oldIsbn, Book book, Genre[] genres) {
        if (!bookValidation(book)) {
            return;
        }
        String sql = "UPDATE books SET isbn = ?, title = ?, author = ?, publisher = ?, total_copies = ?, available_copies = ? WHERE isbn = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getIsbn());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getAuthor());
            pstmt.setString(4, book.getPublisher());
            pstmt.setInt(5, book.getTotalCopies());
            pstmt.setInt(6, book.getAvailableCopies());
            // If you don’t set the 7th parameter (i.e., oldIsbn), then JDBC 
            // doesn't know what to compare in the WHERE clause, and your update 
            // silently fails or throws an exception.
            // That will correctly match the row to update by its original ISBN. 
            // Without this, the update can't find the row and returns “No book found”.
            pstmt.setString(7, oldIsbn);
            int affected = pstmt.executeUpdate();

            if (affected > 0) {
                System.out.println("Book updated successfully");
            } else {
                System.out.println("No book found with the given ISBN");
            }

            // Update genres
            // Step 1: remove all existing genres for the book
            String deleteGenresSql = "DELETE FROM book_genres WHERE book_isbn = ?";
            try (PreparedStatement deleteStatement = conn.prepareStatement(deleteGenresSql)) {
                deleteStatement.setString(1, book.getIsbn());
                deleteStatement.executeUpdate();
            }

            // Step 2: insert new genres if they don't exist in genres table
            String checkGenreSql = "SELECT 1 FROM genres WHERE id = ? LIMIT 1";
            String insertGenreSql = "INSERT INTO genres(name) VALUES (?)";
            try (PreparedStatement checkGenreStmt = conn.prepareStatement(checkGenreSql);
                    PreparedStatement insertGenreStmt = conn.prepareStatement(insertGenreSql)) {
                    for (Genre genre : genres) {
                        checkGenreStmt.setInt(1, genre.getGenreId());
                        ResultSet genreRs = checkGenreStmt.executeQuery();
                        // genreRs.next() moves to the one and only row returned by SELECT 1 and return true.
                        // else it return false
                        if (!genreRs.next()) {
                            insertGenreStmt.setString(2, genre.getName());
                            insertGenreStmt.executeUpdate();
                            System.out.println("Genre added: " + genre.getName());
                        }
                    }
            }

            // Step 3: Add book_genre associations
            String insertBookGenreSql = "INSERT INTO book_genres(book_isbn, genre_id) VALUES (?, ?)";
            try (PreparedStatement bookGenreStmt = conn.prepareStatement(insertBookGenreSql)) {
                for (Genre genre : genres) {
                    bookGenreStmt.setString(1, book.getIsbn());
                    bookGenreStmt.setInt(2, genre.getGenreId());
                    bookGenreStmt.executeUpdate();
                }
            }

            System.out.println("book genres updates successfully");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void addGenres(Genre genre) {
        String checkGenreSql = "SELECT COUNT(*) FROM genres WHERE id = ?";
        String genreSql = "INSERT INTO genres(name) VALUES (?)";
        try(Connection conn = DriverManager.getConnection(url);
            PreparedStatement genrePstmt = conn.prepareStatement(genreSql);
            PreparedStatement checkGenrePstmt = conn.prepareStatement(checkGenreSql)) {
            // check genre exist 
            checkGenrePstmt.setInt(1, genre.getGenreId());
            ResultSet rs = checkGenrePstmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("genre already exists. genre not added.");
            } else {
                genrePstmt.setString(1, genre.getName());
                genrePstmt.executeUpdate();
                System.out.println("genre added successfully");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void removeGenres(Genre genre) {
        String sql = "DELETE FROM genres WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             pstmt.setInt(1, genre.getGenreId());
             int affectedRows = pstmt.executeUpdate();
             if (affectedRows > 0) {
                System.out.println("genre removed successfully");
             } else {
                System.out.println("genre not found.");
             }
        } catch (SQLException e) {
            System.out.println("Error removing genre: " + e.getMessage());
        }
    }

    public static void updateGenre(int oldId, Genre genre) {
        String sql = "UPDATE genres SET name = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, genre.getName());
            pstmt.setInt(2, oldId);
            int affected = pstmt.executeUpdate();

            if (affected > 0) {
                System.out.println("genre updated successfully");
            } else {
                System.out.println("No genre found with the given ID");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static BookGenre[] getBookGenres() {
        List<BookGenre> bookgenres = new ArrayList<>();
        String sql = "SELECT * FROM book_genres";

        try (Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String isbn = rs.getString("book_isbn");
                int genreId = rs.getInt("genre_id");
                
                // Create a new Book object and add it to the list
                BookGenre bookgenre = new BookGenre(isbn, genreId);
                bookgenres.add(bookgenre);
            }
       } catch (SQLException e) {
            System.out.println("Error retrieving book genres: " + e.getMessage());
       }

       return bookgenres.toArray(new BookGenre[0]);
    }

    public static Genre[] getGenres() {
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT * FROM genres";

        try (Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String name = rs.getString("name");
                int genreId = rs.getInt("id");
                
                // Create a new Book object and add it to the list
                Genre genre = new Genre(genreId, name);
                genres.add(genre);
            }
       } catch (SQLException e) {
            System.out.println("Error retrieving genres: " + e.getMessage());
       }

       return genres.toArray(new Genre[0]);
    }

    public static Book[] getBooks() {
       List<Book> books = new ArrayList<>();
       String sql = "SELECT * FROM books";
       
       try (Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String isbn = rs.getString("isbn");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String publiser = rs.getString("publisher");
                int totalCopies = rs.getInt("total_copies");
                int availableCopies = rs.getInt("available_copies");
                
                // Create a new Book object and add it to the list
                Book book = new Book(isbn, title, author, publiser, totalCopies, availableCopies);
                books.add(book);
            }
       } catch (SQLException e) {
            System.out.println("Error retrieving books: " + e.getMessage());
       }

       return books.toArray(new Book[0]);
    }

    public static void resetDB() {
        try(Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()) {
            // Drop tables if they exist
            stmt.executeUpdate("DROP TABLE IF EXISTS users");
            stmt.executeUpdate("DROP TABLE IF EXISTS books");
            stmt.executeUpdate("DROP TABLE IF EXISTS genres");
            stmt.executeUpdate("DROP TABLE IF EXISTS book_genres");
            stmt.executeUpdate("DROP TABLE IF EXISTS borrowed_books");
            createUserTable(stmt);
            createBookTable(stmt);
            createGenreTable(stmt);
            createBookGenreTable(stmt);
            createBorrowBookTable(stmt);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static boolean bookValidation(Book book) {

        if (book.getIsbn() == null || book.getIsbn().trim().isEmpty()) {
            System.out.println("Invalid ISBN");
            return false;
        }

        if(book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            System.out.println("Invalid title.");
            return false;
        } 

        if(book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            System.out.println("Invalid author.");
            return false;
        }

        if(book.getPublisher() == null || book.getPublisher().trim().isEmpty()) {
            System.out.println("Invalid publisher.");
            return false;
        }

        if (book.getTotalCopies() < 0) {
            System.out.println("Invalid total copies.");
            return false;
        }

        if (book.getAvailableCopies() < 0) {
            System.out.println("Invalid available copies.");
            return false;
        }

        return true;
    }
}
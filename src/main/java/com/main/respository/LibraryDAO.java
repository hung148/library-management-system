package com.main.respository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.main.app.Main;
import com.main.entity.Admin;
import com.main.entity.Book;
import com.main.entity.BorrowedBook;
import com.main.entity.Member;
import com.main.services.AuthServices;
public class LibraryDAO {
    private static final Object DB_LOCK = new Object();
    // get log in state
    public static void getLogin() {
        String query = "SELECT * FROM users WHERE login = 1 LIMIT 1";
        synchronized (DB_LOCK) {
            try (Connection conn = DBInitializer.connect();
                PreparedStatement stmt = conn.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    if (rs.getString("type").equals("admin")) {
                        Main.currentUser = new Admin();
                        Main.currentUser.setId(rs.getInt("id"));
                        Main.currentUser.setEmail(rs.getString("email"));
                        Main.currentUser.setHashPassword(rs.getString("password"));
                        Main.currentUser.setUsername(rs.getString("username"));
                        Main.currentUser.setName(rs.getString("name"));
                        Main.currentUser.setType(rs.getString("type"));
                        Main.currentUser.setStatus(rs.getString("status"));
                        Main.currentUser.setBalance(rs.getDouble("balance"));
                        Main.currentUser.setLibraryID(rs.getString("libraryID"));
                    } else {
                        Main.currentUser = new Member();
                        Main.currentUser.setId(rs.getInt("id"));
                        Main.currentUser.setEmail(rs.getString("email"));
                        Main.currentUser.setHashPassword(rs.getString("password"));
                        Main.currentUser.setUsername(rs.getString("username"));
                        Main.currentUser.setName(rs.getString("name"));
                        Main.currentUser.setType(rs.getString("type"));
                        Main.currentUser.setStatus(rs.getString("status"));
                        Main.currentUser.setBalance(rs.getDouble("balance"));
                        Main.currentUser.setLibraryID(rs.getString("libraryID"));
                    }
                }
            } catch (SQLException e) {  
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    // add, remove, get, update admin
    public static void addAdmin(String email, String password, String username, String name, double balance) throws SQLException {
        synchronized (DB_LOCK) {
            String sql = "INSERT INTO users (email, password, username, name, type, status, balance) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try(Connection conn = DBInitializer.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, email);
                pstmt.setString(2, AuthServices.generateHashedPassword(password));
                pstmt.setString(3, username);
                pstmt.setString(4, name);
                pstmt.setString(5, "admin");
                pstmt.setString(6, "normal");
                pstmt.setDouble(7, balance);
                pstmt.executeUpdate();
                System.out.println("Admin added successfully");
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        }
        
    }

    public static Admin getAdminById(int id) {
        String sql = "SELECT * FROM users WHERE id = ? AND type = 'admin'";
        try (Connection conn = DBInitializer.connect(); 
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Admin admin = new Admin();
                admin.setId(rs.getInt("id"));
                admin.setEmail(rs.getString("email"));
                admin.setUsername(rs.getString("username"));
                admin.setName(rs.getString("name"));
                admin.setHashPassword(rs.getString("password"));
                admin.setType(rs.getString("type"));
                admin.setStatus(rs.getString("status"));
                admin.setBalance(rs.getDouble("balance"));
                admin.setLoginState(rs.getBoolean("login"));
                return admin;
            } else {
                System.out.println("admin not found");
            } 
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Admin getAdminByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ? AND type = 'admin'";
        synchronized (DB_LOCK) {
            try (Connection conn = DBInitializer.connect(); 
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, email);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    Admin admin = new Admin();
                    admin.setId(rs.getInt("id"));
                    admin.setEmail(rs.getString("email"));
                    admin.setUsername(rs.getString("username"));
                    admin.setName(rs.getString("name"));
                    admin.setHashPassword(rs.getString("password"));
                    admin.setType(rs.getString("type"));
                    admin.setStatus(rs.getString("status"));
                    admin.setBalance(rs.getDouble("balance"));
                    admin.setLoginState(rs.getBoolean("login"));
                    return admin;
                } else {
                    System.out.println("admin not found");
                } 
            } catch(SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static Admin getAdminByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ? AND type = 'admin'";
        synchronized (DB_LOCK) {
            try (Connection conn = DBInitializer.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    Admin admin = new Admin();
                    admin.setId(rs.getInt("id"));
                    admin.setEmail(rs.getString("email"));
                    admin.setUsername(rs.getString("username"));
                    admin.setName(rs.getString("name"));
                    admin.setHashPassword(rs.getString("password"));
                    admin.setType(rs.getString("type"));
                    admin.setStatus(rs.getString("status"));
                    admin.setBalance(rs.getDouble("balance"));
                    admin.setLoginState(rs.getBoolean("login"));
                    return admin;
                } else {
                    System.out.println("Admin not found");
                }
            } 
            catch(SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    } 

    public static Admin[] getAdminList() {
        String sql = "SELECT * FROM users WHERE type = 'admin'";
        List<Admin> adminList = new ArrayList<>();
        synchronized (DB_LOCK) {
            try (Connection conn = DBInitializer.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Admin admin = new Admin();
                    admin.setId(rs.getInt("id"));
                    admin.setEmail(rs.getString("email"));
                    admin.setUsername(rs.getString("username"));
                    admin.setName(rs.getString("name"));
                    admin.setHashPassword(rs.getString("password"));
                    admin.setType(rs.getString("type"));
                    admin.setStatus(rs.getString("status"));
                    admin.setBalance(rs.getDouble("balance"));
                    admin.setLoginState(rs.getBoolean("login"));
                    adminList.add(admin);
                }
                System.out.println("Retrieve admin list sucessfully");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return adminList.toArray(new Admin[0]);
        }
    }

    public static void updateAdmin(int id, Admin admin) {
        String sql = "UPDATE users SET email = ?, password = ?, username = ?, name = ?, balance = ?, login = ? WHERE id = ? AND type = 'admin'";
        synchronized (DB_LOCK) {
            try (Connection conn = DBInitializer.connect(); 
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, admin.getEmail());
                    pstmt.setString(2, admin.getHashPassword());
                    pstmt.setString(3, admin.getUsername());
                    pstmt.setString(4, admin.getName());
                    pstmt.setDouble(5, admin.getBalance());
                    pstmt.setBoolean(6, admin.getLoginState());
                    pstmt.setInt(7, id);
                    pstmt.executeUpdate();
                    System.out.println("Update admin successfully");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
    }

    public static void updateAdmin(String email, Admin admin) {
        String sql = "UPDATE users SET email = ?, password = ?, username = ?, name = ?, balance = ?, login = ? WHERE email = ? AND type = 'admin'";
        synchronized (DB_LOCK) {
            try (Connection conn = DBInitializer.connect(); 
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, admin.getEmail());
                    pstmt.setString(2, admin.getHashPassword());
                    pstmt.setString(3, admin.getUsername());
                    pstmt.setString(4, admin.getName());
                    pstmt.setDouble(5, admin.getBalance());
                    pstmt.setBoolean(6, admin.getLoginState());
                    pstmt.setString(7, email);
                    pstmt.executeUpdate();
                    System.out.println("Update admin successfully");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
    }

    public static void removeAdmin(int id) {
        String sql = "DELETE FROM users WHERE id = ? AND type = 'admin'";
        synchronized (DB_LOCK) {
            try (Connection conn = DBInitializer.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Admin is removed");
                } else {
                    System.out.println("No admin found with id " + id + ".");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
    }

    public static void removeAdmin(String email) {
        String sql = "DELETE FROM users WHERE email = ? AND type = 'admin'";
        synchronized(DB_LOCK) {
            try (Connection conn = DBInitializer.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, email);
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Admin is removed");
                } else {
                    System.out.println("No admin found with email " + email + ".");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
    }

    // add, remove, get, update member
    public static Boolean addMember(String email, String password, String username, String name, double balance, String libraryID) {
        System.out.println("Attempting to add member: " + email);

        String sql = "INSERT INTO users (email, password, username, name, type, status, balance, libraryID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        synchronized (DB_LOCK) {
            try(Connection conn = DBInitializer.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                System.out.println("Executing SQL: " + sql);

                pstmt.setString(1, email);
                pstmt.setString(2, AuthServices.generateHashedPassword(password));
                pstmt.setString(3, username);
                pstmt.setString(4, name);
                pstmt.setString(5, "member");
                pstmt.setString(6, "normal");
                pstmt.setDouble(7, balance);
                pstmt.setString(8, libraryID);

                int rowsAffected = pstmt.executeUpdate();
                System.out.println("Rows affected: " + rowsAffected); // Debug log

                if (rowsAffected > 0) {
                    System.out.println("Member added successfully");
                    return true;
                } else {
                    System.out.println("No rows affected - member not added");
                }
                return false;
            } catch (SQLException e) {
                System.err.println("SQL Error adding member: " + e.getMessage()); // Better error logging
                e.printStackTrace();
                return false;
            }
        }
    }

    public static Member getMemberById(int id) {
        String sql = "SELECT * FROM users WHERE id = ? AND type = 'member'";
        synchronized (DB_LOCK) {
            try (Connection conn = DBInitializer.connect(); 
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    Member member = new Member();
                    member.setId(rs.getInt("id"));
                    member.setEmail(rs.getString("email"));
                    member.setUsername(rs.getString("username"));
                    member.setName(rs.getString("name"));
                    member.setHashPassword(rs.getString("password"));
                    member.setType(rs.getString("type"));
                    member.setStatus(rs.getString("status"));
                    member.setBalance(rs.getDouble("balance"));
                    member.setLibraryID(rs.getString("libraryID"));
                    member.setLoginState(rs.getBoolean("login"));
                    return member;
                } else {
                    System.out.println("member not found");
                } 
            } catch(SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static Member getMemberByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ? AND type = 'member'";
        synchronized (DB_LOCK) {
            try (Connection conn = DBInitializer.connect(); 
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, email);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    Member member = new Member();
                    member.setId(rs.getInt("id"));
                    member.setEmail(rs.getString("email"));
                    member.setUsername(rs.getString("username"));
                    member.setName(rs.getString("name"));
                    member.setHashPassword(rs.getString("password"));
                    member.setType(rs.getString("type"));
                    member.setStatus(rs.getString("status"));
                    member.setBalance(rs.getDouble("balance"));
                    member.setLibraryID(rs.getString("libraryID"));
                    member.setLoginState(rs.getBoolean("login"));
                    return member;
                } else {
                    System.out.println("member not found");
                } 
            } catch(SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static Member getMemberByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ? AND type = 'member'";
        synchronized (DB_LOCK) {
            try (Connection conn = DBInitializer.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    Member member = new Member();
                    member.setId(rs.getInt("id"));
                    member.setEmail(rs.getString("email"));
                    member.setUsername(rs.getString("username"));
                    member.setName(rs.getString("name"));
                    member.setHashPassword(rs.getString("password"));
                    member.setType(rs.getString("type"));
                    member.setStatus(rs.getString("status"));
                    member.setBalance(rs.getDouble("balance"));
                    member.setLibraryID(rs.getString("libraryID"));
                    member.setLoginState(rs.getBoolean("login"));
                    return member;
                } else {
                    System.out.println("Member not found");
                }
            } 
            catch(SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    } 

    public static Member[] getMemberList() {
        String sql = "SELECT * FROM users WHERE type = 'member'";
        List<Member> memberList = new ArrayList<>();
        synchronized (DB_LOCK) {
            try (Connection conn = DBInitializer.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Member member = new Member();
                    member.setId(rs.getInt("id"));
                    member.setEmail(rs.getString("email"));
                    member.setUsername(rs.getString("username"));
                    member.setName(rs.getString("name"));
                    member.setHashPassword(rs.getString("password"));
                    member.setType(rs.getString("type"));
                    member.setStatus(rs.getString("status"));
                    member.setBalance(rs.getDouble("balance"));
                    member.setLibraryID(rs.getString("libraryID"));
                    member.setLoginState(rs.getBoolean("login"));
                    memberList.add(member);
                }
                System.out.println("Retrieve member list sucessfully");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return memberList.toArray(new Member[0]);
        }
    }

    public static void updateMember(int id, Member member) {
        String sql = "UPDATE users SET email = ?, password = ?, username = ?, name = ?, type = ?, status = ?, balance = ?, login = ? WHERE id = ? AND type = 'member'";
        synchronized (DB_LOCK) {
            try (Connection conn = DBInitializer.connect(); 
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, member.getEmail());
                    pstmt.setString(2, member.getHashPassword());
                    pstmt.setString(3, member.getUsername());
                    pstmt.setString(4, member.getName());
                    pstmt.setString(5, member.getType());
                    pstmt.setString(6, member.getStatus());
                    pstmt.setDouble(7, member.getBalance());
                    pstmt.setBoolean(8, member.getLoginState());
                    pstmt.setInt(9, id);
                    pstmt.executeUpdate();
                    System.out.println("Update member successfully");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateMember(String email, Member member) {
        String sql = "UPDATE users SET email = ?, password = ?, username = ?, name = ?, type = ?, status = ?, balance = ?, login = ?, libraryID = ? WHERE email = ? AND type = 'member'";
        synchronized (DB_LOCK) {
            try (Connection conn = DBInitializer.connect(); 
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, member.getEmail());
                    pstmt.setString(2, member.getHashPassword());
                    pstmt.setString(3, member.getUsername());
                    pstmt.setString(4, member.getName());
                    pstmt.setString(5, member.getType());
                    pstmt.setString(6, member.getStatus());
                    pstmt.setDouble(7, member.getBalance());
                    pstmt.setBoolean(8, member.getLoginState());
                    pstmt.setString(9, member.getLibraryID());
                    pstmt.setString(10, email);
                    pstmt.executeUpdate();
                    System.out.println("Update member successfully");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void removeMember(int id) {
        String sql = "DELETE FROM users WHERE id = ? AND type = 'member'";
        synchronized (DB_LOCK) {
            try (Connection conn = DBInitializer.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Member is removed");
                } else {
                    System.out.println("No member found with id " + id + ".");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void removeMember(String email) {
        String sql = "DELETE FROM users WHERE email = ? AND type = 'member'";
        synchronized (DB_LOCK) {
            try (Connection conn = DBInitializer.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, email);
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Member is removed");
                } else {
                    System.out.println("No member found with email " + email + ".");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /*===================================================================================================================================*/
    // add, remove, get, update book
    public static void addBook(String isbn, String title, String author, String publisher, int totalCopies, double value) {
        String sql = "INSERT INTO bookTable (isbn, title, author, publisher, totalCopies, availableCopies, value) VALUES (?, ?, ?, ?, ?, ?, ?)";
        synchronized (DB_LOCK) {
            try(Connection conn = DBInitializer.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, isbn);
                pstmt.setString(2, title);
                pstmt.setString(3, author);
                pstmt.setString(4, publisher);
                pstmt.setInt(5, totalCopies);
                pstmt.setInt(6, totalCopies);
                pstmt.setDouble(7, value);
                pstmt.executeUpdate();
                System.out.println("Successfully added book.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addBookfromFile(String filepath) {
        synchronized (DB_LOCK) {
            try (FileReader reader = new FileReader(filepath);
                BufferedReader bufferedReader = new BufferedReader(reader)) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                    String[] results = line.split("\\|"); // have to use \\ to escape
                    for (int i = 0; i < results.length; i++) {
                        results[i] = results[i].trim();
                    }
                    System.out.println(Arrays.toString(results));
                    String title = results[0];
                    String author = results[1];
                    String publisher = results[2];
                    String ISBN = results[3];
                    int totalCopies = Integer.parseInt(results[4]);
                    double value = Double.parseDouble(results[5].substring(1));
                    addBook(ISBN, title, author, publisher, totalCopies, value);
                }
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public static Book getBook(String isbn) {
        String sql = "SELECT * FROM bookTable WHERE isbn = ?";
        synchronized (DB_LOCK) {
            try (Connection conn = DBInitializer.connect(); 
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, isbn);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    Book book = new Book(
                            rs.getString("isbn"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("publisher"),
                            rs.getInt("totalCopies")
                            );
                    book.setValue(rs.getDouble("value"));
                    book.setAvailableCopies(rs.getInt("availableCopies"));
                    return book;
                } else {
                    System.out.println("Book not found.");
                } 
            } catch(SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static Book[] bookList() {
        String sql = "SELECT * FROM bookTable";
        List<Book> bookList = new ArrayList<>();
        synchronized (DB_LOCK) {
            try (Connection conn = DBInitializer.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Book book = new Book(
                            rs.getString("isbn"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("publisher"),
                            rs.getInt("totalCopies")
                            );
                    book.setValue(rs.getDouble("value"));
                    book.setAvailableCopies(rs.getInt("availableCopies"));
                    bookList.add(book);
                }
                //System.out.println("Successfully retrieved book list.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return bookList.toArray(new Book[0]);
        }
    }

    public static void updateBook(String isbn, Book book) {
        String sql = "UPDATE bookTable SET isbn = ?, title = ?, author = ?, publisher = ?, totalCopies = ?, availableCopies = ?, value = ? WHERE isbn = ?";
        synchronized (DB_LOCK) {
            try (Connection conn = DBInitializer.connect(); 
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, book.get_ISBN());
                    pstmt.setString(2, book.get_title());
                    pstmt.setString(3, book.get_author());
                    pstmt.setString(4, book.get_publisher());
                    pstmt.setInt(5, book.getTotalCopies());
                    pstmt.setInt(6, book.getAvailableCopies());
                    pstmt.setDouble(7, book.getValue());
                    pstmt.setString(8, isbn);
                    pstmt.executeUpdate();
                    System.out.println("Successfully updated book.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void removeBook(String isbn) {
        String sql = "DELETE FROM bookTable WHERE isbn = ?";
        synchronized (DB_LOCK) {
            try (Connection conn = DBInitializer.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, isbn);
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Book is removed.");
                } else {
                    System.out.println("No book found with isbn " + isbn + ".");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /*===================================================================================================================================*/
    // add, remove, get, update borrowedBook table
    
    public static void addBorrowedBook(int memberID, Book book, LocalDate duedate) {
        String sql = "INSERT INTO borrowedBook (status, dateIssued, deadline, returnedDate, "
        		+ "lateFine, lostFine, isbn, memberId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        synchronized (DB_LOCK) {
            try(Connection conn = DBInitializer.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                LocalDate today = LocalDate.now();
                BorrowedBook newBorrowedBook = new BorrowedBook(memberID, book, today, duedate);
                newBorrowedBook.updateStatusAndFine();
                pstmt.setString(1, newBorrowedBook.getStatus());
                pstmt.setString(2, today.toString());
                pstmt.setString(3, duedate.toString());
                pstmt.setNull(4, java.sql.Types.VARCHAR);
                pstmt.setDouble(5, 0.0);
                pstmt.setDouble(6, 0.0);
                pstmt.setString(7, book.get_ISBN());
                pstmt.setInt(8, memberID);
                
                pstmt.executeUpdate();
                System.out.println("Successfully added borrowed book.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    public static void returnBorrowedBook(int memberID, BorrowedBook book, LocalDate returnedDate) {
    	String sql = "UPDATE borrowedBook SET status = ?, returnedDate = ?, lateFine = ?, lostFine = ? WHERE memberID = ? AND isbn = ?";
    	synchronized (DB_LOCK) {
            try (Connection conn = DBInitializer.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                BorrowedBook b1 = book;
                
                b1.setReturnedDate(returnedDate);
                b1.updateStatusAndFine();  

                pstmt.setString(1, b1.getStatus());
                pstmt.setString(2, returnedDate.toString());
                pstmt.setDouble(3, b1.getLateFine());
                pstmt.setDouble(4, b1.getLostFine());
                pstmt.setInt(5, memberID);
                pstmt.setString(6, book.getBook().get_ISBN());

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Borrowed book returned status updated successfully.");
                } else {
                    System.out.println("No borrowed book found with isbn " + book.getBook().get_ISBN() + ".");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void removeBorrowedBook(BorrowedBook book) {
    	String sql = "DELETE FROM borrowedBook WHERE isbn = ?";
        synchronized (DB_LOCK) {
            try (Connection conn = DBInitializer.connect();
                    PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, book.getBook().get_ISBN());
                    int affectedRows = pstmt.executeUpdate();
                    if (affectedRows > 0) {
                        System.out.println("Borrowed Book is removed.");
                    } else {
                        System.out.println("No borrowed book found with isbn " + book.getBook().get_ISBN() + ".");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    }

    public static List<BorrowedBook> searchBorrowedBooksListByMember(int memberID) {
        List<BorrowedBook> borrowedBooks = new ArrayList<>();
        String sql = "SELECT * FROM borrowedBook WHERE memberId = ?";
        synchronized (DB_LOCK) {
            try (Connection conn = DBInitializer.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, memberID);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    BorrowedBook borrowedBook = new BorrowedBook(
                        rs.getInt("memberId"),
                        getBook(rs.getString("isbn")),
                        LocalDate.parse(rs.getString("dateIssued")),
                        LocalDate.parse(rs.getString("deadline"))
                    );
                    // Safe parse returnedDate
                    String returnedDateStr = rs.getString("returnedDate");
                    if (returnedDateStr != null && !returnedDateStr.isBlank()) {
                        borrowedBook.setReturnedDate(LocalDate.parse(returnedDateStr));
                    } else {
                        borrowedBook.setReturnedDate(null);
                    }

                    borrowedBook.setPayFine(rs.getBoolean("isPayFine"));
                    borrowedBook.setReturnedAfterLate(rs.getBoolean("isReturned"));
                    borrowedBook.setPayFineAfterLost(rs.getBoolean("isPayFineAfterLost"));
                    borrowedBook.setReturnedAfterLost(rs.getBoolean("isReturnedAfterLost"));
                    borrowedBook.setId(rs.getInt("id"));
                    borrowedBooks.add(borrowedBook);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Step 2: Do post-processing outside DB lock
            for (BorrowedBook book : borrowedBooks) {
                book.updateStatusAndFine(); // safe now, even if it writes
            }

            return borrowedBooks;
        }
    }

    
    public static BorrowedBook searchBorrowedBookByMember(int memberID, String isbn) {
    	String sql = "SELECT * FROM borrowedBook WHERE memberId = ? AND isbn = ?";
        BorrowedBook borrowedBook = null;
        synchronized (DB_LOCK) {
            try (Connection conn = DBInitializer.connect(); 
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, memberID);
                pstmt.setString(2, isbn);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    borrowedBook = new BorrowedBook(
                            rs.getInt("memberId"),
                            getBook(rs.getString("isbn")),
                            LocalDate.parse(rs.getString("dateIssued")),
                            LocalDate.parse(rs.getString("deadline"))
                            );

                    // Safe parse returnedDate
                    String returnedDateStr = rs.getString("returnedDate");
                    if (returnedDateStr != null && !returnedDateStr.isBlank()) {
                        borrowedBook.setReturnedDate(LocalDate.parse(returnedDateStr));
                    } else {
                        borrowedBook.setReturnedDate(null);
                    }
                    borrowedBook.setPayFine(rs.getBoolean("isPayFine"));
                    borrowedBook.setReturnedAfterLate(rs.getBoolean("isReturned"));
                    borrowedBook.setPayFineAfterLost(rs.getBoolean("isPayFineAfterLost"));
                    borrowedBook.setReturnedAfterLost(rs.getBoolean("isReturnedAfterLost"));
                    borrowedBook.setId(rs.getInt("id"));
                } else {
                    System.out.println("No borrowed book found under memberID: " + memberID);
                } 
            } catch(SQLException e) {
                e.printStackTrace();
            }

            // Step 2: Do post-processing outside DB lock
            if (borrowedBook != null) {
                borrowedBook.updateStatusAndFine(); // safe now, even if it writes
                if (borrowedBook.getStatus().equalsIgnoreCase("lost")) {
                    System.out.println(borrowedBook.getBook().get_title());
                }
            }
            return borrowedBook;
        }
    }
    public static void updateBorrowedBookStatus(BorrowedBook borrow, String status) {
        String sql = "UPDATE borrowedBook SET status = ? WHERE memberId = ? AND isbn = ? AND status != 'returned'";
        synchronized (DB_LOCK) {
            try (Connection conn = DBInitializer.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, status);
                pstmt.setInt(2, borrow.getMemberID()); // or borrow.getMemberId() depending on your class
                pstmt.setString(3, borrow.getBook().get_ISBN());

                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Status updated to '" + status + "' for book: " + borrow.getBook().get_title());
                } else {
                    System.out.println("No rows updated. Either already returned or not found.");
                }

            } catch (SQLException e) {
                System.err.println("Error updating borrow status: " + e.getMessage());
            }
        }
    }
    public static BorrowedBook[] borrowedBookList() {
        String sql = "SELECT * FROM borrowedBook";
        List<BorrowedBook> borrowedBookList = new ArrayList<>();
        synchronized (DB_LOCK) {
            try (Connection conn = DBInitializer.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    BorrowedBook borrowedBook = new BorrowedBook(
                            rs.getInt("memberId"),
                            getBook(rs.getString("isbn")),
                            LocalDate.parse(rs.getString("dateIssued")),
                            LocalDate.parse(rs.getString("deadline"))
                            );
                    
                    String returned = rs.getString("returnedDate");
                    if (returned != null) {
                        borrowedBook.setReturnedDate(LocalDate.parse(returned));
                    }
                    
                    borrowedBook.setLostFine(rs.getDouble("lostFine"));
                    borrowedBook.setLateFine(rs.getDouble("lateFine"));
                    
                    borrowedBookList.add(borrowedBook);
                }
                //System.out.println("Successfully retrieved borrowed book list.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return borrowedBookList.toArray(new BorrowedBook[0]);
        }
    }
    
    public static double getLostFine(int memberID) {
    	String sql = "SELECT * FROM borrowedBook WHERE memberId = ?";
        synchronized (DB_LOCK) {
            try (Connection conn = DBInitializer.connect(); 
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, memberID);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getDouble("lostFine");
                } else {
                    System.out.println("There is no lost fine for memberID: " + memberID);
                } 
            } catch(SQLException e) {
                e.printStackTrace();
            }
            return 0.0;
        }
    }
    
    public static double getLateFine(int memberID) {
    	String sql = "SELECT * FROM borrowedBook WHERE memberId = ?";
        synchronized (DB_LOCK) {
            try (Connection conn = DBInitializer.connect(); 
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, memberID);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getDouble("lateFine");
                } else {
                    System.out.println("There is no late fine for memberID: " + memberID);
                } 
            } catch(SQLException e) {
                e.printStackTrace();
            }
            return 0.0;
        }
    }

    public static void updateBorrowedBookIsPayFine(BorrowedBook borrow, Boolean isPayFine) {
        String sql = "UPDATE borrowedBook SET isPayFine = ? WHERE memberId = ? AND isbn = ? AND status != 'returned'";
        synchronized (DB_LOCK) {
            try (Connection conn = DBInitializer.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setBoolean(1, isPayFine);
                pstmt.setInt(2, borrow.getMemberID()); // or borrow.getMemberId() depending on your class
                pstmt.setString(3, borrow.getBook().get_ISBN());

                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("isPayFine updated to '" + isPayFine + "' for book: " + borrow.getBook().get_title());
                } else {
                    System.out.println("No rows updated. Either already returned or not found.");
                }

            } catch (SQLException e) {
                System.err.println("Error updating borrow status: " + e.getMessage());
            }
        }
    }

    public static void updateBorrowedBookIsReturned(BorrowedBook borrow, Boolean isReturned) {
        String sql = "UPDATE borrowedBook SET isReturned = ? WHERE memberId = ? AND isbn = ? AND status != 'returned'";
        synchronized (DB_LOCK) {
            try (Connection conn = DBInitializer.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setBoolean(1, isReturned);
                pstmt.setInt(2, borrow.getMemberID()); // or borrow.getMemberId() depending on your class
                pstmt.setString(3, borrow.getBook().get_ISBN());

                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("isReturned updated to '" + isReturned + "' for book: " + borrow.getBook().get_title());
                } else {
                    System.out.println("No rows updated. Either already returned or not found.");
                }

            } catch (SQLException e) {
                System.err.println("Error updating borrow status: " + e.getMessage());
            }
        }
    }

    public static void updateBorrowedBookIsPayFineAfterLost(BorrowedBook borrow, Boolean isPayFineAfterLost) {
        String sql = "UPDATE borrowedBook SET isPayFineAfterLost = ? WHERE memberId = ? AND isbn = ? AND status != 'returned'";
        synchronized (DB_LOCK) {
            try (Connection conn = DBInitializer.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setBoolean(1, isPayFineAfterLost);
                pstmt.setInt(2, borrow.getMemberID()); // or borrow.getMemberId() depending on your class
                pstmt.setString(3, borrow.getBook().get_ISBN());

                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("isPayFineAfterLost updated to '" + isPayFineAfterLost + "' for book: " + borrow.getBook().get_title());
                } else {
                    System.out.println("No rows updated. Either already returned or not found.");
                }

            } catch (SQLException e) {
                System.err.println("Error updating borrow status: " + e.getMessage());
            }
        }
    }

    public static void updateBorrowedBookIsReturnedAfterLost(BorrowedBook borrow, Boolean isReturnedAfterLost) {
        String sql = "UPDATE borrowedBook SET isReturnedAfterLost = ? WHERE memberId = ? AND isbn = ? AND status != 'returned'";
        synchronized (DB_LOCK) {
            try (Connection conn = DBInitializer.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setBoolean(1, isReturnedAfterLost);
                pstmt.setInt(2, borrow.getMemberID()); // or borrow.getMemberId() depending on your class
                pstmt.setString(3, borrow.getBook().get_ISBN());

                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("isReturnedAfterLost updated to '" + isReturnedAfterLost + "' for book: " + borrow.getBook().get_title());
                } else {
                    System.out.println("No rows updated. Either already returned or not found.");
                }

            } catch (SQLException e) {
                System.err.println("Error updating borrow status: " + e.getMessage());
            }
        }
    }
    
    public static void payFine(int memberID, double amount, int fineChoice) {
    	String sql;
        String fineType;
        double fine;
        synchronized (DB_LOCK) {
            try (Connection conn = DBInitializer.connect()) {
                PreparedStatement pstmt = null;
                int rowsAffected = 0;

                if (fineChoice == 1) { // Pay late fine
                    fine = getLateFine(memberID);
                    double balance = amount - fine;
                    System.out.println(amount);
                    System.out.println(fine);
                    if (balance >= 0.0) {
                        sql = "UPDATE borrowedBook SET lateFine = ?, isPayFine = ? WHERE memberId = ?";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setDouble(1, balance);
                        pstmt.setBoolean(2, true);
                        pstmt.setInt(3, memberID);
                        Member member = getMemberById(memberID);
                        member.setBalance(balance);
                        updateMember(memberID, member);
                        rowsAffected = pstmt.executeUpdate();
                    } else {
                        System.out.println("Amount not enough for current late fine.");
                        return;
                    }

                    fineType = "late fine";

                } else { // Pay lost fine
                    fine = getLostFine(memberID);
                    double balance = amount - fine;

                    if (balance >= 0.0) {
                        sql = "UPDATE borrowedBook SET lostFine = ?, isPayFineAfterLost = ? WHERE memberId = ?";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setDouble(1, balance);
                        pstmt.setBoolean(2, true);
                        pstmt.setInt(3, memberID);
                        Member member = getMemberById(memberID);
                        member.setBalance(balance);
                        updateMember(memberID, member);
                        rowsAffected = pstmt.executeUpdate();
                    } else {
                        System.out.println("Amount exceeds current lost fine.");
                        return;
                    }

                    fineType = "lost fine";
                }

                if (rowsAffected > 0) {
                    System.out.println("New " + fineType + " balance for memberID " + memberID + ": " + (amount - fine));
                } else {
                    System.out.println("No " + fineType + " record found for memberID: " + memberID);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    public static void updateFine(int memberID, double fine, int fineChoice) {
    	String sql;
    	String fineType;
    	if (fineChoice == 1) { // if fineChoice == 1, update lateFine
    		sql = "UPDATE borrowedBook SET lateFine = ? WHERE memberId = ?";
    		fineType = "Late fine";
    	} else { // update lostFine
    		sql = "UPDATE borrowedBook SET lostFine = ? WHERE memberId = ?";
    		fineType = "Lost fine";
    	}
    	synchronized (DB_LOCK) {
            try (Connection conn = DBInitializer.connect(); 
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                if (fine > 0.0) {
                    pstmt.setDouble(1, fine);
                    pstmt.setInt(2, memberID);
                }
                
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println(fineType + " updated for memberID: " + memberID);
                } else {
                    System.out.println("Unsuccefully updated fine for memberID: " + memberID);
                } 
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

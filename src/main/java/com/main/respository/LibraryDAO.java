package com.main.respository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.main.entity.Admin;
import com.main.entity.Book;
import com.main.entity.BorrowedBook;
import com.main.entity.Member;
import com.main.services.AuthServices;
public class LibraryDAO {
    
    // add, remove, get, update admin
    public static void addAdmin(String email, String password, String username, String name, double balance) throws SQLException {
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

    public static Admin getAdminByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ? AND type = 'admin'";
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

    public static Admin[] getAdminList() {
        String sql = "SELECT * FROM users WHERE type = 'admin'";
        List<Admin> adminList = new ArrayList<>();
        
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

    public static void updateAdmin(int id, Admin admin) {
        String sql = "UPDATE users SET email = ?, password = ?, username = ?, name = ?, balance = ?, login = ? WHERE id = ? AND type = 'admin'";

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

    public static void updateAdmin(String email, Admin admin) {
        String sql = "UPDATE users SET email = ?, password = ?, username = ?, name = ?, balance = ?, login = ? WHERE email = ? AND type = 'admin'";

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

    public static void removeAdmin(int id) {
        String sql = "DELETE FROM users WHERE id = ? AND type = 'admin'";
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

    public static void removeAdmin(String email) {
        String sql = "DELETE FROM users WHERE email = ? AND type = 'admin'";
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

    // add, remove, get, update member
    public static Boolean addMember(String email, String password, String username, String name, double balance, String libraryID) {
        System.out.println("Attempting to add member: " + email);

        String sql = "INSERT INTO users (email, password, username, name, type, status, balance, libraryID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

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

    public static Member getMemberById(int id) {
        String sql = "SELECT * FROM users WHERE id = ? AND type = 'member'";
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

    public static Member getMemberByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ? AND type = 'member'";
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

    public static Member getMemberByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ? AND type = 'member'";
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

    public static Member[] getMemberList() {
        String sql = "SELECT * FROM users WHERE type = 'member'";
        List<Member> memberList = new ArrayList<>();
        
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

    public static void updateMember(int id, Member member) {
        String sql = "UPDATE users SET email = ?, password = ?, username = ?, name = ?, type = ?, status = ?, balance = ?, login = ? WHERE id = ? AND type = 'member'";

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

    public static void updateMember(String email, Member member) {
        String sql = "UPDATE users SET email = ?, password = ?, username = ?, name = ?, type = ?, status = ?, balance = ?, login = ?, libraryID = ? WHERE email = ? AND type = 'member'";

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

    public static void removeMember(int id) {
        String sql = "DELETE FROM users WHERE id = ? AND type = 'member'";
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

    public static void removeMember(String email) {
        String sql = "DELETE FROM users WHERE email = ? AND type = 'member'";
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
    
    /*===================================================================================================================================*/
    // add, remove, get, update book
    public static void addBook(String isbn, String title, String author, String publisher, int totalCopies) {
        String sql = "INSERT INTO bookTable (isbn, title, author, publisher, totalCopies, availableCopies) VALUES (?, ?, ?, ?, ?, ?)";

        try(Connection conn = DBInitializer.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, isbn);
            pstmt.setString(2, title);
            pstmt.setString(3, author);
            pstmt.setString(4, publisher);
            pstmt.setInt(5, totalCopies);
            pstmt.setInt(6, totalCopies);
            pstmt.executeUpdate();
            System.out.println("Successfully added book.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Book getBook(String isbn) {
        String sql = "SELECT * FROM bookTable WHERE isbn = ?";
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

    public static Book[] bookList() {
        String sql = "SELECT * FROM bookTable";
        List<Book> bookList = new ArrayList<>();
        
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
            	book.setAvailableCopies(rs.getInt("availableCopies"));
                bookList.add(book);
            }
            System.out.println("Successfully retrieved book list.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList.toArray(new Book[0]);
    }

    public static void updateBook(String isbn, Book book) {
        String sql = "UPDATE bookTable SET totalCopies = ?, availableCopies = ? WHERE isbn = ?";

        try (Connection conn = DBInitializer.connect(); 
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, book.getTotalCopies());
                pstmt.setInt(2, book.getAvailableCopies());
                pstmt.setString(3, isbn);
                pstmt.executeUpdate();
                System.out.println("Successfully updated book.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeBook(String isbn) {
        String sql = "DELETE FROM bookTable WHERE isbn = ?";
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

    /*===================================================================================================================================*/
    // add, remove, get, update borrowedBook table
    
    public static void addBorrowedBook(int memberID, Book book) {
        String sql = "INSERT INTO borrowedBook (status, dateIssued, deadline, returnedDate, "
        		+ "lateFine, lostFine, isbn, memberId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try(Connection conn = DBInitializer.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	LocalDate today = LocalDate.now();
        	
        	pstmt.setString(1, "pending");
            pstmt.setString(2, today.toString());
            pstmt.setString(3, today.plusWeeks(2).toString());
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
    
    
    public static void returnBorrowedBook(int memberID, BorrowedBook book, LocalDate returnedDate) {
    	String sql = "UPDATE borrowedBook SET status = ?, returnedDate = ?, lateFine = ?, lostFine = ? WHERE memberID = ? AND isbn = ?";
    	try (Connection conn = DBInitializer.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
    			BorrowedBook b1 = book;
    			b1.setReturnedDate(returnedDate);
    			b1.updateStatusAndFine();
    			
    			pstmt.setString(1, b1.getStatus());
    			pstmt.setString(2, returnedDate.toString());
    			pstmt.setDouble(3, b1.getLateFine());
                pstmt.setInt(5, memberID);
                pstmt.setString(6, book.getBook().get_ISBN());

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
    
    public static void removeBorrowedBook(BorrowedBook book) {
    	String sql = "DELETE FROM borrowedBook WHERE isbn = ?";
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
    
    public static BorrowedBook searchBorrowedBookByMember(int memberID) {
    	String sql = "SELECT * FROM borrowedBook WHERE memberId = ?";
        try (Connection conn = DBInitializer.connect(); 
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, memberID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
            	BorrowedBook borrowedBook = new BorrowedBook(
            			rs.getInt("memberId"),
            			getBook(rs.getString("isbn")),
                		LocalDate.parse(rs.getString("dateIssued"))
                		);
                return borrowedBook;
            } else {
                System.out.println("No borrowed book found under memberID: " + memberID);
            } 
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static BorrowedBook[] borrowedBookList() {
        String sql = "SELECT * FROM borrowedBook";
        List<BorrowedBook> borrowedBookList = new ArrayList<>();
        
        try (Connection conn = DBInitializer.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
            	BorrowedBook borrowedBook = new BorrowedBook(
            			rs.getInt("memberId"),
                		getBook(rs.getString("isbn")),
                		LocalDate.parse(rs.getString("dateIssued"))
                		);
            	borrowedBookList.add(borrowedBook);
            }
            System.out.println("Successfully retrieved borrowed book list.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrowedBookList.toArray(new BorrowedBook[0]);
    }
    
    public static double getLostFine(int memberID) {
    	String sql = "SELECT * FROM borrowedBook WHERE memberId = ?";
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
    
    public static double getLateFine(int memberID) {
    	String sql = "SELECT * FROM borrowedBook WHERE memberId = ?";
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
    
    public static void payFine(int memberID, double amount, int fineChoice) {
    	String sql; 
    	String fineType;
    	double fine;
    	if (fineChoice == 1) { // if fineChoice == 1, pay lateFine
    		sql = "UPDATE borrowedBook SET lateFine = ? WHERE memberId = ?";
    		fineType = "late fine";
    		fine = getLateFine(memberID);
    	} else { // pay lostFine
    		sql = "UPDATE borrowedBook SET lostFine = ? WHERE memberId = ?";
    		fineType = "lost fine";
    		fine = getLostFine(memberID);
    	}
    	
        try (Connection conn = DBInitializer.connect(); 
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	double balance = fine - amount;
        	if (balance >= 0.0) {
        		pstmt.setDouble(1, balance);
        		pstmt.setInt(2, memberID);
        	}
            
        	int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
            	System.out.println("New " + fineType + "'s balance for memberID: " + balance);
            } else {
                System.out.println("No " + fineType + " found for memberID: " + memberID);
            } 
        } catch(SQLException e) {
            e.printStackTrace();
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

package com.main.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.main.entity.Admin;
import com.main.entity.Book;
import com.main.entity.BorrowedBook;
import com.main.entity.Member;
import com.main.respository.DBInitializer;
import com.main.respository.LibraryDAO;
import com.main.services.AuthServices;
import com.main.services.LibraryServices;

public class TestDB {
    public static void main(String[] args) throws Exception {
        testAdminAndUser();
        testSearchFunction();
    }
    public static void testSearchFunction() {
        String searchKeyword = "Book 1"; // test with book title, you can change to author, ...
        System.out.println("Sort from the closet to the search keyword: " + searchKeyword);
        try {
            int i = 1;
            for (Book book : LibraryServices.searchBook(searchKeyword, null, null, null)) {
                System.out.print(i + ": ");
                book.printBookInfo();
                System.out.println();
                i++;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        /*
        Sort from the closet to the search keyword: Book 1
        WARNING: A restricted method in java.lang.System has been called
        WARNING: java.lang.System::load has been called by org.sqlite.SQLiteJDBCLoader in an unnamed module (file:/Users/Tom/.m2/repository/org/xerial/sqlite-jdbc/3.49.1.0/sqlite-jdbc-3.49.1.0.jar)
        WARNING: Use --enable-native-access=ALL-UNNAMED to avoid a warning for callers in this module
        WARNING: Restricted methods will be blocked in a future release unless native access is enabled

        User table created or already exist.
        bookTable table created or already exist.
        Connection established.
        Successfully retrieved book list.
        1: Book's title: Book 1
        Book's author: Person1
        Book's publisher: publisher1
        Book's ISBN: 2320321093
        Book's total copies: 30
        Book's available copies: 30

        2: Book's title: Book 2
        Book's author: Person2
        Book's publisher: publisher2
        Book's ISBN: 4309340320
        Book's total copies: 50
        Book's available copies: 50
        */
    }
    public static void testAdminAndUser() throws Exception {
        DBInitializer.initializeDB();
        System.out.println("Test addAdmin()");
        LibraryDAO.addAdmin(
            "trinhhungqt2004@gmail.com", 
            "NguyenHungSky123@", 
            "hung123", 
            "Trinh Dinh Nguyen Hung", 
            1000);
        LibraryDAO.addAdmin(
            "test1@gmail.com", 
            "pass1", 
            "adminname1", 
            "name1", 
            2000);
        LibraryDAO.addAdmin(
            "test2@gmail.com", 
            "pass2", 
            "adminname2", 
            "name2", 
            3000);
        System.out.println("Test getAdminList()");
        for (Admin admin : LibraryDAO.getAdminList()) {
            System.out.println("----------------------------");
            System.out.println(admin.toString());
            System.out.println("----------------------------");
        }
        System.out.println("Test getAdminByUsername()");
        System.out.println((LibraryDAO.getAdminByUsername("adminname2") != null) ? LibraryDAO.getAdminByUsername("adminname2").toString() : "");
        System.out.println("Test getAdminById()");
        System.out.println("get Admin at id = 1");
        System.out.println((LibraryDAO.getAdminById(1) != null) ? LibraryDAO.getAdminById(1).toString() : "");
        System.out.println("Test getAdminByEmail()");
        System.out.println("get Admin at email = trinhhungqt2004@gmail.com");
        System.out.println((LibraryDAO.getAdminByEmail("trinhhungqt2004@gmail.com") != null) ? LibraryDAO.getAdminByEmail("trinhhungqt2004@gmail.com").toString() : "");
        System.out.println("Test removeAdmin() by id");
        System.out.println("remove Admin by id = 2");
        LibraryDAO.removeAdmin(2);
        for (Admin admin : LibraryDAO.getAdminList()) {
            System.out.println("----------------------------");
            System.out.println(admin.toString());
            System.out.println("----------------------------");
        }
        System.out.println("Test removeAdmin() by email");
        System.out.println("remove Admin by email = test1@gmail.com");
        LibraryDAO.removeAdmin("test1@gmail.com");
        for (Admin admin : LibraryDAO.getAdminList()) {
            System.out.println("----------------------------");
            System.out.println(admin.toString());
            System.out.println("----------------------------");
        }
        System.out.println("Test updateAdmin()");
        Admin newadmin = new Admin();
        newadmin.setName("newAdmin");
        newadmin.setEmail("newAdminEmail");
        newadmin.setHashPassword(AuthServices.generateHashedPassword("newAdminPassword"));
        newadmin.setUsername("newAdminUsername");
        newadmin.setBalance(2300);
        LibraryDAO.updateAdmin("test2@gmail.com", newadmin);
        for (Admin admin : LibraryDAO.getAdminList()) {
            System.out.println("----------------------------");
            System.out.println(admin.toString());
            System.out.println("----------------------------");
        }
        //test member 
        System.out.println("Test addMember()");
        LibraryDAO.addMember(
            "member1@gmail.com", 
            "pass1", 
            "membername1", 
            "name1", 
            1000, "123456");
        LibraryDAO.addMember(
            "member2@gmail.com", 
            "pass2", 
            "membername2", 
            "name2", 
            2000, "12345");
        LibraryDAO.addMember(
            "member3@gmail.com", 
            "pass3", 
            "membername3", 
            "name3", 
            3000, "1234");
        System.out.println("Test getMemberList()");
        for (Member member : LibraryDAO.getMemberList()) {
            System.out.println("----------------------------");
            System.out.println(member.toString());
            System.out.println("----------------------------");
        }
        System.out.println("Test getMemberByUsername()");
        System.out.println((LibraryDAO.getMemberByUsername("membername2") != null) ? LibraryDAO.getMemberByUsername("membername2").toString() : "");
        System.out.println("Test getMemberById()");
        System.out.println("get Member at id = 1");
        System.out.println((LibraryDAO.getMemberById(1) != null) ? LibraryDAO.getMemberById(1).toString() : "");
        System.out.println("Test getMemberByEmail()");
        System.out.println("get Member at email = member3@gmail.com");
        System.out.println((LibraryDAO.getMemberByEmail("member3@gmail.com") != null) ? LibraryDAO.getMemberByEmail("member3@gmail.com").toString() : "");
        System.out.println("Test removeMember() by id");
        System.out.println("remove Member by id = 2");
        LibraryDAO.removeMember(2);
        for (Member member : LibraryDAO.getMemberList()) {
            System.out.println("----------------------------");
            System.out.println(member.toString());
            System.out.println("----------------------------");
        }
        System.out.println("Test removeMember() by email");
        System.out.println("remove Member by email = member3@gmail.com");
        LibraryDAO.removeMember("member3@gmail.com");
        for (Member member : LibraryDAO.getMemberList()) {
            System.out.println("----------------------------");
            System.out.println(member.toString());
            System.out.println("----------------------------");
        }

        // test Book
        System.out.println("Test addBook()");
        LibraryDAO.addBook(
            "2320321093", 
            "Book 1",
            "Person1", 
            "publisher1", 
            30, 10);
        LibraryDAO.addBook(
            "4309340320", 
            "Book 2",
            "Person2", 
            "publisher2", 
            50, 10);
        LibraryDAO.addBook(
            "0849323242", 
            "Book 3",
            "Person3", 
            "publisher3", 
            10, 10);
        // Sample data
        LibraryDAO.addBook("1234567890", "Java Basics", "John Doe", "Tech Press", 3, 30);
        LibraryDAO.addBook("9876543210", "Effective Java", "Joshua Bloch", "Pearson", 5, 40);
        LibraryDAO.addBook("5555555555", "Clean Code", "Robert Martin", "Prentice Hall", 4, 50);
        
        
        LibraryDAO.addBook("9781337275347", "Calculus", "Ron Larson", "Cengage Learning", 7, 70);
        LibraryDAO.addBook("9781484255865", "Jakarta EE Recipes: A Problem-Solution Approach", "Josh Juneau", "Apress", 4, 80);
        LibraryDAO.addBook("9781998114801", "Of Mice and Men", "John Steinbeck", "Pomodoro Books", 9, 90);
        LibraryDAO.addBook("9780241968581", "One Hundred Years of Solitude", "Gabriel Garcia Marquez", "Penguin", 5, 100);
        LibraryDAO.addBook("9780671880316", "Schindler's List", "Thomas Keneally", "Atria Books", 13, 40);
        		

        System.out.println();
        
        System.out.println("Test bookList()");
        for (Book book : LibraryDAO.bookList()) {
            System.out.println("----------------------------");
            book.printBookInfo();
            System.out.println("----------------------------");
        }

        System.out.println();
        
        System.out.println("Test getBook()");
        System.out.println("get Member at isbn = 2320321093");
        Book book = LibraryDAO.getBook("2320321093");
        if (book != null) {
        	book.printBookInfo();
        }
        
        System.out.println("get Member at isbn = 832");
        Book b3 = LibraryDAO.getBook("832");
        if (b3 != null) {
        	b3.printBookInfo();
        }
        
        System.out.println("get Member at isbn = 9780671880316");
        Book b4 = LibraryDAO.getBook("9780671880316");
        if (b4 != null) {
        	b4.printBookInfo();
        }
        
        System.out.println("get Member at isbn = 9781998114801");
        Book b5 = LibraryDAO.getBook("9781998114801");
        if (b5 != null) {
        	b5.printBookInfo();
        }
        
        System.out.println();
        
        System.out.println("Test updateBook()");
        System.out.println("update Book with isbn = 0849323242");
        Book b1 = new Book();
        b1.setTotalCopies(100);
        b1.setAvailableCopies(96);
        LibraryDAO.updateBook("0849323242", b1);
        LibraryDAO.getBook("0849323242").printBookInfo();
        
        System.out.println("update Book with isbn = 9780241968581");
        Book b6 = new Book();
        b6.setTotalCopies(99);
        b6.setAvailableCopies(39);
        LibraryDAO.updateBook("9780241968581", b6);
        LibraryDAO.getBook("9780241968581").printBookInfo();
        
        System.out.println();
        
        System.out.println("Test removeBook()");
        System.out.println("remove Book with isbn = 0849323242");
        System.out.println("remove Book with isbn = 9781337275347");
        LibraryDAO.removeBook("0849323242");
        LibraryDAO.removeBook("9781337275347");
        for (Book b2 : LibraryDAO.bookList()) {
            System.out.println("----------------------------");
            b2.printBookInfo();
            System.out.println("----------------------------");
        }
        
        System.out.println();
        
        System.out.println("=== Test BorrowedBook Functions ===");

        // 1. Add test member and book
        LibraryDAO.addMember("borrower1@gmail.com", "password1", "borrower1", "Borrower One", 1000, "LID001");
        Book b7 = LibraryDAO.getBook("1234567890"); // Java Basics, already added in your sample data
        Member member = LibraryDAO.getMemberByEmail("borrower1@gmail.com");

        if (b7 == null || member == null) {
            System.out.println("Cannot run test - missing book or member.");
            return;
        }

        // 2. Add a borrowed book record
        //LibraryDAO.addBorrowedBook(member.getId(), b7);

        // 3. Retrieve and print borrowed book
        BorrowedBook borrowedBook = LibraryDAO.searchBorrowedBookByMember(member.getId(), "1234567890");
        if (borrowedBook != null) {
            System.out.println("Borrowed book found:");
            System.out.println(borrowedBook);
        } else {
            System.out.println("Borrowed book not found.");
        }

        // 4. Update return status (simulate a late return)
        LocalDate returnedDate = LocalDate.now().plusWeeks(3); // 1 week late
        LibraryDAO.returnBorrowedBook(member.getId(), borrowedBook, returnedDate);

        // 5. Get updated fines
        double lateFine = LibraryDAO.getLateFine(member.getId());
        System.out.println("Late fine after return: $" + lateFine);

        // 6. Pay partial fine
        LibraryDAO.payFine(member.getId(), 0.25, 1); // pay $0.25 of late fine

        // 7. Update full fine to 0
        LibraryDAO.updateFine(member.getId(), 0.0, 1);

        // 8. List all borrowed books
        System.out.println("\nAll borrowed books:");
        for (BorrowedBook bb : LibraryDAO.borrowedBookList()) {
            System.out.println(bb);
        }

        // 9. Remove borrowed book
        LibraryDAO.removeBorrowedBook(borrowedBook);
        
        System.out.println();
        
        System.out.println("=== Test: On-Time Return ===");

        LibraryDAO.addMember("ontime@gmail.com", "pass", "ontime", "On Time", 500, "LID002");
        Book b8 = LibraryDAO.getBook("9876543210"); // Effective Java
        Member member1 = LibraryDAO.getMemberByEmail("ontime@gmail.com");

        if (b8 == null || member1 == null) return;

        //LibraryDAO.addBorrowedBook(member1.getId(), b8);

        BorrowedBook bb = LibraryDAO.searchBorrowedBookByMember(member1.getId(), "9876543210");
        if (bb != null) {
            // Return exactly on deadline
            LocalDate returnedDate1 = bb.getDeadline();
            LibraryDAO.returnBorrowedBook(member1.getId(), bb, returnedDate1);
            System.out.println("Returned on deadline. Late fine: $" + LibraryDAO.getLateFine(member1.getId()));
            LibraryDAO.removeBorrowedBook(bb);
        }
        
        System.out.println();
        
        System.out.println("=== Test: Late Return with Full Fine Payment ===");

        LibraryDAO.addMember("late@gmail.com", "pass", "lateguy", "Late Guy", 200, "LID003");
        Book b9 = LibraryDAO.getBook("5555555555"); // Clean Code
        Member member2 = LibraryDAO.getMemberByEmail("late@gmail.com");

        if (b9 == null || member2 == null) return;

        //LibraryDAO.addBorrowedBook(member2.getId(), b9);

        BorrowedBook bb1 = LibraryDAO.searchBorrowedBookByMember(member2.getId(), "5555555555");
        if (bb1 != null) {
            // Return 4 days late
            LocalDate returnedDate1 = bb1.getDeadline().plusDays(4);
            LibraryDAO.returnBorrowedBook(member2.getId(), bb1, returnedDate1);
            double fine = LibraryDAO.getLateFine(member2.getId());
            System.out.println("Returned 4 days late. Fine: $" + fine);
            LibraryDAO.payFine(member2.getId(), fine, 1); // Pay full fine
            LibraryDAO.removeBorrowedBook(bb1);
        }
        
        System.out.println();
        
        System.out.println("=== Test: Lost Book ===");

        LibraryDAO.addMember("lost@gmail.com", "pass", "lostcase", "Lost Case", 0, "LID004");
        Book b10 = LibraryDAO.getBook("2320321093"); // Book 1
        Member member3 = LibraryDAO.getMemberByEmail("lost@gmail.com");

        if (b10 == null || member3 == null) return;

        //LibraryDAO.addBorrowedBook(member3.getId(), b10);

        BorrowedBook bb2 = LibraryDAO.searchBorrowedBookByMember(member3.getId(), "2320321093");
        if (bb2 != null) {
            // Simulate loss: never returned, set status manually
        	bb2.setReturnedDate(LocalDate.now().plusWeeks(3));
        	bb2.setLostFine(25.0);
        	bb2.updateStatusAndFine(); // optional
            System.out.println("Book lost. Lost fine: $" + bb2.getLostFine());
            LibraryDAO.updateFine(member3.getId(), bb2.getLostFine(), 2); // Update lost fine
            LibraryDAO.removeBorrowedBook(bb2);
        }
        
        System.out.println();
        
    }
}

/*
Output:
WARNING: Using incubator modules: jdk.incubator.vector
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Test addMember()
Attempting to add member: member1@gmail.com
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Executing SQL: INSERT INTO users (email, password, username, name, type, status, balance, libraryID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
SQL Error adding member: [SQLITE_CONSTRAINT_UNIQUE] A UNIQUE constraint failed (UNIQUE constraint failed: users.libraryID)
org.sqlite.SQLiteException: [SQLITE_CONSTRAINT_UNIQUE] A UNIQUE constraint failed (UNIQUE constraint failed: users.libraryID)
	at org.sqlite.core.DB.newSQLException(DB.java:1179)
	at org.sqlite.core.DB.newSQLException(DB.java:1190)
	at org.sqlite.core.DB.execute(DB.java:985)
	at org.sqlite.core.DB.executeUpdate(DB.java:1054)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.lambda$executeLargeUpdate$2(JDBC3PreparedStatement.java:129)
	at org.sqlite.jdbc3.JDBC3Statement.withConnectionTimeout(JDBC3Statement.java:458)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.executeLargeUpdate(JDBC3PreparedStatement.java:124)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.executeUpdate(JDBC3PreparedStatement.java:105)
	at com.main/com.main.respository.LibraryDAO.addMember(LibraryDAO.java:234)
	at com.main/com.main.test.TestDB.testAdminAndUser(TestDB.java:132)
	at com.main/com.main.test.TestDB.main(TestDB.java:23)
Attempting to add member: member2@gmail.com
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Executing SQL: INSERT INTO users (email, password, username, name, type, status, balance, libraryID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
SQL Error adding member: [SQLITE_CONSTRAINT_UNIQUE] A UNIQUE constraint failed (UNIQUE constraint failed: users.libraryID)
org.sqlite.SQLiteException: [SQLITE_CONSTRAINT_UNIQUE] A UNIQUE constraint failed (UNIQUE constraint failed: users.libraryID)
	at org.sqlite.core.DB.newSQLException(DB.java:1179)
	at org.sqlite.core.DB.newSQLException(DB.java:1190)
	at org.sqlite.core.DB.execute(DB.java:985)
	at org.sqlite.core.DB.executeUpdate(DB.java:1054)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.lambda$executeLargeUpdate$2(JDBC3PreparedStatement.java:129)
	at org.sqlite.jdbc3.JDBC3Statement.withConnectionTimeout(JDBC3Statement.java:458)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.executeLargeUpdate(JDBC3PreparedStatement.java:124)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.executeUpdate(JDBC3PreparedStatement.java:105)
	at com.main/com.main.respository.LibraryDAO.addMember(LibraryDAO.java:234)
	at com.main/com.main.test.TestDB.testAdminAndUser(TestDB.java:138)
	at com.main/com.main.test.TestDB.main(TestDB.java:23)
Attempting to add member: member3@gmail.com
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Executing SQL: INSERT INTO users (email, password, username, name, type, status, balance, libraryID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
Rows affected: 1
Member added successfully
Test getMemberList()
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Retrieve member list sucessfully
----------------------------
User name: name2
User username: membername2
User password: $2a$10$A3r5mWrcQXRZzOa29lI06em6QruRE.IkmqReUYOIQX4epMp7pb.Z2
User email: member2@gmail.com
User type: member
User status: normal
User balance: 2000.0
User id: 3
----------------------------
----------------------------
User name: name1
User username: membername1
User password: $2a$10$LsSNPQL.9W7/wEWLmJ6bf.q8zfm1Nk09Uxiw/dRKRQ7FwlzRHuYBC
User email: member1@gmail.com
User type: member
User status: normal
User balance: 1000.0
User id: 5
----------------------------
----------------------------
User name: Borrower One
User username: borrower1
User password: $2a$10$cfLrnm2PcIsJeEF6WNWZAeREE2gI7fUnYMSS6xzsA5sD9e3/62dg2
User email: borrower1@gmail.com
User type: member
User status: normal
User balance: 1000.0
User id: 9
----------------------------
----------------------------
User name: On Time
User username: ontime
User password: $2a$10$wLaIlnSK3HVb4aAzTStab.WE6KwQb9ewGmSGcajvLjY/Pz4Fyopym
User email: ontime@gmail.com
User type: member
User status: normal
User balance: 500.0
User id: 10
----------------------------
----------------------------
User name: Late Guy
User username: lateguy
User password: $2a$10$OBIwUvQ.NHv6YKWjfAKyDO558iUd5VMa5TJ1MN/QWUXNo0OL9TX2i
User email: late@gmail.com
User type: member
User status: normal
User balance: 200.0
User id: 11
----------------------------
----------------------------
User name: Lost Case
User username: lostcase
User password: $2a$10$54KUW.O5KDUd5UP/Jdixxe9RlzRPNHNBdWkBhX/TklXpn/Rp24m3e
User email: lost@gmail.com
User type: member
User status: normal
User balance: 0.0
User id: 12
----------------------------
----------------------------
User name: name3
User username: membername3
User password: $2a$10$QeoENiqSXlbY8cXluygLeuUQKBNJf2JN5LzhY5..aR3dw4pcZ2jyS
User email: member3@gmail.com
User type: member
User status: normal
User balance: 3000.0
User id: 23
----------------------------
Test getMemberByUsername()
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
User name: name2
User username: membername2
User password: $2a$10$A3r5mWrcQXRZzOa29lI06em6QruRE.IkmqReUYOIQX4epMp7pb.Z2
User email: member2@gmail.com
User type: member
User status: normal
User balance: 2000.0
User id: 3
Test getMemberById()
get Member at id = 1
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
member not found

Test getMemberByEmail()
get Member at email = member3@gmail.com
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
User name: name3
User username: membername3
User password: $2a$10$QeoENiqSXlbY8cXluygLeuUQKBNJf2JN5LzhY5..aR3dw4pcZ2jyS
User email: member3@gmail.com
User type: member
User status: normal
User balance: 3000.0
User id: 23
Test removeMember() by id
remove Member by id = 2
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
No member found with id 2.
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Retrieve member list sucessfully
----------------------------
User name: name2
User username: membername2
User password: $2a$10$A3r5mWrcQXRZzOa29lI06em6QruRE.IkmqReUYOIQX4epMp7pb.Z2
User email: member2@gmail.com
User type: member
User status: normal
User balance: 2000.0
User id: 3
----------------------------
----------------------------
User name: name1
User username: membername1
User password: $2a$10$LsSNPQL.9W7/wEWLmJ6bf.q8zfm1Nk09Uxiw/dRKRQ7FwlzRHuYBC
User email: member1@gmail.com
User type: member
User status: normal
User balance: 1000.0
User id: 5
----------------------------
----------------------------
User name: Borrower One
User username: borrower1
User password: $2a$10$cfLrnm2PcIsJeEF6WNWZAeREE2gI7fUnYMSS6xzsA5sD9e3/62dg2
User email: borrower1@gmail.com
User type: member
User status: normal
User balance: 1000.0
User id: 9
----------------------------
----------------------------
User name: On Time
User username: ontime
User password: $2a$10$wLaIlnSK3HVb4aAzTStab.WE6KwQb9ewGmSGcajvLjY/Pz4Fyopym
User email: ontime@gmail.com
User type: member
User status: normal
User balance: 500.0
User id: 10
----------------------------
----------------------------
User name: Late Guy
User username: lateguy
User password: $2a$10$OBIwUvQ.NHv6YKWjfAKyDO558iUd5VMa5TJ1MN/QWUXNo0OL9TX2i
User email: late@gmail.com
User type: member
User status: normal
User balance: 200.0
User id: 11
----------------------------
----------------------------
User name: Lost Case
User username: lostcase
User password: $2a$10$54KUW.O5KDUd5UP/Jdixxe9RlzRPNHNBdWkBhX/TklXpn/Rp24m3e
User email: lost@gmail.com
User type: member
User status: normal
User balance: 0.0
User id: 12
----------------------------
----------------------------
User name: name3
User username: membername3
User password: $2a$10$QeoENiqSXlbY8cXluygLeuUQKBNJf2JN5LzhY5..aR3dw4pcZ2jyS
User email: member3@gmail.com
User type: member
User status: normal
User balance: 3000.0
User id: 23
----------------------------
Test removeMember() by email
remove Member by email = member3@gmail.com
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Member is removed
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Retrieve member list sucessfully
----------------------------
User name: name2
User username: membername2
User password: $2a$10$A3r5mWrcQXRZzOa29lI06em6QruRE.IkmqReUYOIQX4epMp7pb.Z2
User email: member2@gmail.com
User type: member
User status: normal
User balance: 2000.0
User id: 3
----------------------------
----------------------------
User name: name1
User username: membername1
User password: $2a$10$LsSNPQL.9W7/wEWLmJ6bf.q8zfm1Nk09Uxiw/dRKRQ7FwlzRHuYBC
User email: member1@gmail.com
User type: member
User status: normal
User balance: 1000.0
User id: 5
----------------------------
----------------------------
User name: Borrower One
User username: borrower1
User password: $2a$10$cfLrnm2PcIsJeEF6WNWZAeREE2gI7fUnYMSS6xzsA5sD9e3/62dg2
User email: borrower1@gmail.com
User type: member
User status: normal
User balance: 1000.0
User id: 9
----------------------------
----------------------------
User name: On Time
User username: ontime
User password: $2a$10$wLaIlnSK3HVb4aAzTStab.WE6KwQb9ewGmSGcajvLjY/Pz4Fyopym
User email: ontime@gmail.com
User type: member
User status: normal
User balance: 500.0
User id: 10
----------------------------
----------------------------
User name: Late Guy
User username: lateguy
User password: $2a$10$OBIwUvQ.NHv6YKWjfAKyDO558iUd5VMa5TJ1MN/QWUXNo0OL9TX2i
User email: late@gmail.com
User type: member
User status: normal
User balance: 200.0
User id: 11
----------------------------
----------------------------
User name: Lost Case
User username: lostcase
User password: $2a$10$54KUW.O5KDUd5UP/Jdixxe9RlzRPNHNBdWkBhX/TklXpn/Rp24m3e
User email: lost@gmail.com
User type: member
User status: normal
User balance: 0.0
User id: 12
----------------------------
Test addBook()
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
org.sqlite.SQLiteException: [SQLITE_CONSTRAINT_PRIMARYKEY] A PRIMARY KEY constraint failed (UNIQUE constraint failed: bookTable.isbn)
	at org.sqlite.core.DB.newSQLException(DB.java:1179)
	at org.sqlite.core.DB.newSQLException(DB.java:1190)
	at org.sqlite.core.DB.execute(DB.java:985)
	at org.sqlite.core.DB.executeUpdate(DB.java:1054)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.lambda$executeLargeUpdate$2(JDBC3PreparedStatement.java:129)
	at org.sqlite.jdbc3.JDBC3Statement.withConnectionTimeout(JDBC3Statement.java:458)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.executeLargeUpdate(JDBC3PreparedStatement.java:124)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.executeUpdate(JDBC3PreparedStatement.java:105)
	at com.main/com.main.respository.LibraryDAO.addBook(LibraryDAO.java:452)
	at com.main/com.main.test.TestDB.testAdminAndUser(TestDB.java:183)
	at com.main/com.main.test.TestDB.main(TestDB.java:23)
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
org.sqlite.SQLiteException: [SQLITE_CONSTRAINT_PRIMARYKEY] A PRIMARY KEY constraint failed (UNIQUE constraint failed: bookTable.isbn)
	at org.sqlite.core.DB.newSQLException(DB.java:1179)
	at org.sqlite.core.DB.newSQLException(DB.java:1190)
	at org.sqlite.core.DB.execute(DB.java:985)
	at org.sqlite.core.DB.executeUpdate(DB.java:1054)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.lambda$executeLargeUpdate$2(JDBC3PreparedStatement.java:129)
	at org.sqlite.jdbc3.JDBC3Statement.withConnectionTimeout(JDBC3Statement.java:458)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.executeLargeUpdate(JDBC3PreparedStatement.java:124)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.executeUpdate(JDBC3PreparedStatement.java:105)
	at com.main/com.main.respository.LibraryDAO.addBook(LibraryDAO.java:452)
	at com.main/com.main.test.TestDB.testAdminAndUser(TestDB.java:189)
	at com.main/com.main.test.TestDB.main(TestDB.java:23)
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Successfully added book.
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
org.sqlite.SQLiteException: [SQLITE_CONSTRAINT_PRIMARYKEY] A PRIMARY KEY constraint failed (UNIQUE constraint failed: bookTable.isbn)
	at org.sqlite.core.DB.newSQLException(DB.java:1179)
	at org.sqlite.core.DB.newSQLException(DB.java:1190)
	at org.sqlite.core.DB.execute(DB.java:985)
	at org.sqlite.core.DB.executeUpdate(DB.java:1054)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.lambda$executeLargeUpdate$2(JDBC3PreparedStatement.java:129)
	at org.sqlite.jdbc3.JDBC3Statement.withConnectionTimeout(JDBC3Statement.java:458)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.executeLargeUpdate(JDBC3PreparedStatement.java:124)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.executeUpdate(JDBC3PreparedStatement.java:105)
	at com.main/com.main.respository.LibraryDAO.addBook(LibraryDAO.java:452)
	at com.main/com.main.test.TestDB.testAdminAndUser(TestDB.java:202)
	at com.main/com.main.test.TestDB.main(TestDB.java:23)
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
org.sqlite.SQLiteException: [SQLITE_CONSTRAINT_PRIMARYKEY] A PRIMARY KEY constraint failed (UNIQUE constraint failed: bookTable.isbn)
	at org.sqlite.core.DB.newSQLException(DB.java:1179)
	at org.sqlite.core.DB.newSQLException(DB.java:1190)
	at org.sqlite.core.DB.execute(DB.java:985)
	at org.sqlite.core.DB.executeUpdate(DB.java:1054)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.lambda$executeLargeUpdate$2(JDBC3PreparedStatement.java:129)
	at org.sqlite.jdbc3.JDBC3Statement.withConnectionTimeout(JDBC3Statement.java:458)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.executeLargeUpdate(JDBC3PreparedStatement.java:124)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.executeUpdate(JDBC3PreparedStatement.java:105)
	at com.main/com.main.respository.LibraryDAO.addBook(LibraryDAO.java:452)
	at com.main/com.main.test.TestDB.testAdminAndUser(TestDB.java:203)
	at com.main/com.main.test.TestDB.main(TestDB.java:23)
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
org.sqlite.SQLiteException: [SQLITE_CONSTRAINT_PRIMARYKEY] A PRIMARY KEY constraint failed (UNIQUE constraint failed: bookTable.isbn)
	at org.sqlite.core.DB.newSQLException(DB.java:1179)
	at org.sqlite.core.DB.newSQLException(DB.java:1190)
	at org.sqlite.core.DB.execute(DB.java:985)
	at org.sqlite.core.DB.executeUpdate(DB.java:1054)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.lambda$executeLargeUpdate$2(JDBC3PreparedStatement.java:129)
	at org.sqlite.jdbc3.JDBC3Statement.withConnectionTimeout(JDBC3Statement.java:458)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.executeLargeUpdate(JDBC3PreparedStatement.java:124)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.executeUpdate(JDBC3PreparedStatement.java:105)
	at com.main/com.main.respository.LibraryDAO.addBook(LibraryDAO.java:452)
	at com.main/com.main.test.TestDB.testAdminAndUser(TestDB.java:204)
	at com.main/com.main.test.TestDB.main(TestDB.java:23)
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Successfully added book.
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
org.sqlite.SQLiteException: [SQLITE_CONSTRAINT_PRIMARYKEY] A PRIMARY KEY constraint failed (UNIQUE constraint failed: bookTable.isbn)
	at org.sqlite.core.DB.newSQLException(DB.java:1179)
	at org.sqlite.core.DB.newSQLException(DB.java:1190)
	at org.sqlite.core.DB.execute(DB.java:985)
	at org.sqlite.core.DB.executeUpdate(DB.java:1054)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.lambda$executeLargeUpdate$2(JDBC3PreparedStatement.java:129)
	at org.sqlite.jdbc3.JDBC3Statement.withConnectionTimeout(JDBC3Statement.java:458)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.executeLargeUpdate(JDBC3PreparedStatement.java:124)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.executeUpdate(JDBC3PreparedStatement.java:105)
	at com.main/com.main.respository.LibraryDAO.addBook(LibraryDAO.java:452)
	at com.main/com.main.test.TestDB.testAdminAndUser(TestDB.java:208)
	at com.main/com.main.test.TestDB.main(TestDB.java:23)
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
org.sqlite.SQLiteException: [SQLITE_CONSTRAINT_PRIMARYKEY] A PRIMARY KEY constraint failed (UNIQUE constraint failed: bookTable.isbn)
	at org.sqlite.core.DB.newSQLException(DB.java:1179)
	at org.sqlite.core.DB.newSQLException(DB.java:1190)
	at org.sqlite.core.DB.execute(DB.java:985)
	at org.sqlite.core.DB.executeUpdate(DB.java:1054)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.lambda$executeLargeUpdate$2(JDBC3PreparedStatement.java:129)
	at org.sqlite.jdbc3.JDBC3Statement.withConnectionTimeout(JDBC3Statement.java:458)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.executeLargeUpdate(JDBC3PreparedStatement.java:124)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.executeUpdate(JDBC3PreparedStatement.java:105)
	at com.main/com.main.respository.LibraryDAO.addBook(LibraryDAO.java:452)
	at com.main/com.main.test.TestDB.testAdminAndUser(TestDB.java:209)
	at com.main/com.main.test.TestDB.main(TestDB.java:23)
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
org.sqlite.SQLiteException: [SQLITE_CONSTRAINT_PRIMARYKEY] A PRIMARY KEY constraint failed (UNIQUE constraint failed: bookTable.isbn)
	at org.sqlite.core.DB.newSQLException(DB.java:1179)
	at org.sqlite.core.DB.newSQLException(DB.java:1190)
	at org.sqlite.core.DB.execute(DB.java:985)
	at org.sqlite.core.DB.executeUpdate(DB.java:1054)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.lambda$executeLargeUpdate$2(JDBC3PreparedStatement.java:129)
	at org.sqlite.jdbc3.JDBC3Statement.withConnectionTimeout(JDBC3Statement.java:458)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.executeLargeUpdate(JDBC3PreparedStatement.java:124)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.executeUpdate(JDBC3PreparedStatement.java:105)
	at com.main/com.main.respository.LibraryDAO.addBook(LibraryDAO.java:452)
	at com.main/com.main.test.TestDB.testAdminAndUser(TestDB.java:210)
	at com.main/com.main.test.TestDB.main(TestDB.java:23)
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
org.sqlite.SQLiteException: [SQLITE_CONSTRAINT_PRIMARYKEY] A PRIMARY KEY constraint failed (UNIQUE constraint failed: bookTable.isbn)
	at org.sqlite.core.DB.newSQLException(DB.java:1179)
	at org.sqlite.core.DB.newSQLException(DB.java:1190)
	at org.sqlite.core.DB.execute(DB.java:985)
	at org.sqlite.core.DB.executeUpdate(DB.java:1054)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.lambda$executeLargeUpdate$2(JDBC3PreparedStatement.java:129)
	at org.sqlite.jdbc3.JDBC3Statement.withConnectionTimeout(JDBC3Statement.java:458)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.executeLargeUpdate(JDBC3PreparedStatement.java:124)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.executeUpdate(JDBC3PreparedStatement.java:105)
	at com.main/com.main.respository.LibraryDAO.addBook(LibraryDAO.java:452)
	at com.main/com.main.test.TestDB.testAdminAndUser(TestDB.java:211)
	at com.main/com.main.test.TestDB.main(TestDB.java:23)

Test bookList()
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Successfully retrieved book list.
----------------------------
Book's title: Book 1
Book's author: Person1
Book's publisher: publisher1
Book's ISBN: 2320321093
Book's total copies: 30
Book's available copies: 30
----------------------------
----------------------------
Book's title: Book 2
Book's author: Person2
Book's publisher: publisher2
Book's ISBN: 4309340320
Book's total copies: 50
Book's available copies: 50
----------------------------
----------------------------
Book's title: Java Basics
Book's author: John Doe
Book's publisher: Tech Press
Book's ISBN: 1234567890
Book's total copies: 3
Book's available copies: 3
----------------------------
----------------------------
Book's title: Effective Java
Book's author: Joshua Bloch
Book's publisher: Pearson
Book's ISBN: 9876543210
Book's total copies: 5
Book's available copies: 5
----------------------------
----------------------------
Book's title: Clean Code
Book's author: Robert Martin
Book's publisher: Prentice Hall
Book's ISBN: 5555555555
Book's total copies: 4
Book's available copies: 4
----------------------------
----------------------------
Book's title: Jakarta EE Recipes: A Problem-Solution Approach
Book's author: Josh Juneau
Book's publisher: Apress
Book's ISBN: 9781484255865
Book's total copies: 4
Book's available copies: 4
----------------------------
----------------------------
Book's title: Of Mice and Men
Book's author: John Steinbeck
Book's publisher: Pomodoro Books
Book's ISBN: 9781998114801
Book's total copies: 9
Book's available copies: 9
----------------------------
----------------------------
Book's title: One Hundred Years of Solitude
Book's author: Gabriel Garcia Marquez
Book's publisher: Penguin
Book's ISBN: 9780241968581
Book's total copies: 99
Book's available copies: 39
----------------------------
----------------------------
Book's title: Schindler's List
Book's author: Thomas Keneally
Book's publisher: Atria Books
Book's ISBN: 9780671880316
Book's total copies: 13
Book's available copies: 13
----------------------------
----------------------------
Book's title: Book 3
Book's author: Person3
Book's publisher: publisher3
Book's ISBN: 0849323242
Book's total copies: 10
Book's available copies: 10
----------------------------
----------------------------
Book's title: Calculus
Book's author: Ron Larson
Book's publisher: Cengage Learning
Book's ISBN: 9781337275347
Book's total copies: 7
Book's available copies: 7
----------------------------

Test getBook()
get Member at isbn = 2320321093
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Book's title: Book 1
Book's author: Person1
Book's publisher: publisher1
Book's ISBN: 2320321093
Book's total copies: 30
Book's available copies: 30
get Member at isbn = 832
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Book not found.
get Member at isbn = 9780671880316
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Book's title: Schindler's List
Book's author: Thomas Keneally
Book's publisher: Atria Books
Book's ISBN: 9780671880316
Book's total copies: 13
Book's available copies: 13
get Member at isbn = 9781998114801
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Book's title: Of Mice and Men
Book's author: John Steinbeck
Book's publisher: Pomodoro Books
Book's ISBN: 9781998114801
Book's total copies: 9
Book's available copies: 9

Test updateBook()
update Book with isbn = 0849323242
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Successfully updated book.
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Book's title: Book 3
Book's author: Person3
Book's publisher: publisher3
Book's ISBN: 0849323242
Book's total copies: 100
Book's available copies: 96
update Book with isbn = 9780241968581
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Successfully updated book.
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Book's title: One Hundred Years of Solitude
Book's author: Gabriel Garcia Marquez
Book's publisher: Penguin
Book's ISBN: 9780241968581
Book's total copies: 99
Book's available copies: 39

Test removeBook()
remove Book with isbn = 0849323242
remove Book with isbn = 9781337275347
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Book is removed.
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Book is removed.
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Successfully retrieved book list.
----------------------------
Book's title: Book 1
Book's author: Person1
Book's publisher: publisher1
Book's ISBN: 2320321093
Book's total copies: 30
Book's available copies: 30
----------------------------
----------------------------
Book's title: Book 2
Book's author: Person2
Book's publisher: publisher2
Book's ISBN: 4309340320
Book's total copies: 50
Book's available copies: 50
----------------------------
----------------------------
Book's title: Java Basics
Book's author: John Doe
Book's publisher: Tech Press
Book's ISBN: 1234567890
Book's total copies: 3
Book's available copies: 3
----------------------------
----------------------------
Book's title: Effective Java
Book's author: Joshua Bloch
Book's publisher: Pearson
Book's ISBN: 9876543210
Book's total copies: 5
Book's available copies: 5
----------------------------
----------------------------
Book's title: Clean Code
Book's author: Robert Martin
Book's publisher: Prentice Hall
Book's ISBN: 5555555555
Book's total copies: 4
Book's available copies: 4
----------------------------
----------------------------
Book's title: Jakarta EE Recipes: A Problem-Solution Approach
Book's author: Josh Juneau
Book's publisher: Apress
Book's ISBN: 9781484255865
Book's total copies: 4
Book's available copies: 4
----------------------------
----------------------------
Book's title: Of Mice and Men
Book's author: John Steinbeck
Book's publisher: Pomodoro Books
Book's ISBN: 9781998114801
Book's total copies: 9
Book's available copies: 9
----------------------------
----------------------------
Book's title: One Hundred Years of Solitude
Book's author: Gabriel Garcia Marquez
Book's publisher: Penguin
Book's ISBN: 9780241968581
Book's total copies: 99
Book's available copies: 39
----------------------------
----------------------------
Book's title: Schindler's List
Book's author: Thomas Keneally
Book's publisher: Atria Books
Book's ISBN: 9780671880316
Book's total copies: 13
Book's available copies: 13
----------------------------

=== Test BorrowedBook Functions ===
Attempting to add member: borrower1@gmail.com
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Executing SQL: INSERT INTO users (email, password, username, name, type, status, balance, libraryID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
SQL Error adding member: [SQLITE_CONSTRAINT_UNIQUE] A UNIQUE constraint failed (UNIQUE constraint failed: users.libraryID)
org.sqlite.SQLiteException: [SQLITE_CONSTRAINT_UNIQUE] A UNIQUE constraint failed (UNIQUE constraint failed: users.libraryID)
	at org.sqlite.core.DB.newSQLException(DB.java:1179)
	at org.sqlite.core.DB.newSQLException(DB.java:1190)
	at org.sqlite.core.DB.execute(DB.java:985)
	at org.sqlite.core.DB.executeUpdate(DB.java:1054)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.lambda$executeLargeUpdate$2(JDBC3PreparedStatement.java:129)
	at org.sqlite.jdbc3.JDBC3Statement.withConnectionTimeout(JDBC3Statement.java:458)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.executeLargeUpdate(JDBC3PreparedStatement.java:124)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.executeUpdate(JDBC3PreparedStatement.java:105)
	at com.main/com.main.respository.LibraryDAO.addMember(LibraryDAO.java:234)
	at com.main/com.main.test.TestDB.testAdminAndUser(TestDB.java:285)
	at com.main/com.main.test.TestDB.main(TestDB.java:23)
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Successfully added borrowed book.
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Borrowed book found:
BorrowedBook [bookISBN=1234567890, memberID=9, borrowDate=2025-06-13, deadline=2025-06-27, returnedDate=null, status=pending, lateFine=0.0, lostFine=0.0]
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Borrowed Book is removed.
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Late fine after return: $3.5
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
New late fine's balance for memberID: 3.25
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Unsuccefully updated fine for memberID: 9

All borrowed books:
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Successfully retrieved borrowed book list.
BorrowedBook [bookISBN=1234567890, memberID=9, borrowDate=2025-06-13, deadline=2025-06-27, returnedDate=2025-07-04, status=pending, lateFine=3.25, lostFine=0.0]
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Borrowed Book is removed.

=== Test: On-Time Return ===
Attempting to add member: ontime@gmail.com
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Executing SQL: INSERT INTO users (email, password, username, name, type, status, balance, libraryID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
SQL Error adding member: [SQLITE_CONSTRAINT_UNIQUE] A UNIQUE constraint failed (UNIQUE constraint failed: users.libraryID)
org.sqlite.SQLiteException: [SQLITE_CONSTRAINT_UNIQUE] A UNIQUE constraint failed (UNIQUE constraint failed: users.libraryID)
	at org.sqlite.core.DB.newSQLException(DB.java:1179)
	at org.sqlite.core.DB.newSQLException(DB.java:1190)
	at org.sqlite.core.DB.execute(DB.java:985)
	at org.sqlite.core.DB.executeUpdate(DB.java:1054)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.lambda$executeLargeUpdate$2(JDBC3PreparedStatement.java:129)
	at org.sqlite.jdbc3.JDBC3Statement.withConnectionTimeout(JDBC3Statement.java:458)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.executeLargeUpdate(JDBC3PreparedStatement.java:124)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.executeUpdate(JDBC3PreparedStatement.java:105)
	at com.main/com.main.respository.LibraryDAO.addMember(LibraryDAO.java:234)
	at com.main/com.main.test.TestDB.testAdminAndUser(TestDB.java:333)
	at com.main/com.main.test.TestDB.main(TestDB.java:23)
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Successfully added borrowed book.
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Borrowed Book is removed.
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Returned on deadline. Late fine: $0.0
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Borrowed Book is removed.

=== Test: Late Return with Full Fine Payment ===
Attempting to add member: late@gmail.com
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Executing SQL: INSERT INTO users (email, password, username, name, type, status, balance, libraryID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
SQL Error adding member: [SQLITE_CONSTRAINT_UNIQUE] A UNIQUE constraint failed (UNIQUE constraint failed: users.libraryID)
org.sqlite.SQLiteException: [SQLITE_CONSTRAINT_UNIQUE] A UNIQUE constraint failed (UNIQUE constraint failed: users.libraryID)
	at org.sqlite.core.DB.newSQLException(DB.java:1179)
	at org.sqlite.core.DB.newSQLException(DB.java:1190)
	at org.sqlite.core.DB.execute(DB.java:985)
	at org.sqlite.core.DB.executeUpdate(DB.java:1054)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.lambda$executeLargeUpdate$2(JDBC3PreparedStatement.java:129)
	at org.sqlite.jdbc3.JDBC3Statement.withConnectionTimeout(JDBC3Statement.java:458)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.executeLargeUpdate(JDBC3PreparedStatement.java:124)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.executeUpdate(JDBC3PreparedStatement.java:105)
	at com.main/com.main.respository.LibraryDAO.addMember(LibraryDAO.java:234)
	at com.main/com.main.test.TestDB.testAdminAndUser(TestDB.java:354)
	at com.main/com.main.test.TestDB.main(TestDB.java:23)
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Successfully added borrowed book.
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Borrowed Book is removed.
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Returned 4 days late. Fine: $2.0
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
New late fine's balance for memberID: 0.0
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Borrowed Book is removed.

=== Test: Lost Book ===
Attempting to add member: lost@gmail.com
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Executing SQL: INSERT INTO users (email, password, username, name, type, status, balance, libraryID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
SQL Error adding member: [SQLITE_CONSTRAINT_UNIQUE] A UNIQUE constraint failed (UNIQUE constraint failed: users.libraryID)
org.sqlite.SQLiteException: [SQLITE_CONSTRAINT_UNIQUE] A UNIQUE constraint failed (UNIQUE constraint failed: users.libraryID)
	at org.sqlite.core.DB.newSQLException(DB.java:1179)
	at org.sqlite.core.DB.newSQLException(DB.java:1190)
	at org.sqlite.core.DB.execute(DB.java:985)
	at org.sqlite.core.DB.executeUpdate(DB.java:1054)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.lambda$executeLargeUpdate$2(JDBC3PreparedStatement.java:129)
	at org.sqlite.jdbc3.JDBC3Statement.withConnectionTimeout(JDBC3Statement.java:458)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.executeLargeUpdate(JDBC3PreparedStatement.java:124)
	at org.sqlite.jdbc3.JDBC3PreparedStatement.executeUpdate(JDBC3PreparedStatement.java:105)
	at com.main/com.main.respository.LibraryDAO.addMember(LibraryDAO.java:234)
	at com.main/com.main.test.TestDB.testAdminAndUser(TestDB.java:377)
	at com.main/com.main.test.TestDB.main(TestDB.java:23)
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Successfully added borrowed book.
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Book lost. Lost fine: $25.0
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Lost fine updated for memberID: 12
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Borrowed Book is removed.

Sort from the closet to the search keyword: Book 1
User table created or already exist.
bookTable table created or already exist.
borrowedBook table created or already exist.
Connection established.
Successfully retrieved book list.
book 1 book 1
book 2 book 1
java basics book 1
effective java book 1
clean code book 1
jakarta ee recipes: a problem-solution approach book 1
of mice and men book 1
one hundred years of solitude book 1
schindler's list book 1
1: Book's title: Book 1
Book's author: Person1
Book's publisher: publisher1
Book's ISBN: 2320321093
Book's total copies: 30
Book's available copies: 30

2: Book's title: Book 2
Book's author: Person2
Book's publisher: publisher2
Book's ISBN: 4309340320
Book's total copies: 50
Book's available copies: 50

3: Book's title: Clean Code
Book's author: Robert Martin
Book's publisher: Prentice Hall
Book's ISBN: 5555555555
Book's total copies: 4
Book's available copies: 4

4: Book's title: Java Basics
Book's author: John Doe
Book's publisher: Tech Press
Book's ISBN: 1234567890
Book's total copies: 3
Book's available copies: 3
 */

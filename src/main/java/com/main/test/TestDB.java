package com.main.test;

import com.main.entity.Admin;
import com.main.entity.Book;
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
        // System.out.println("Test addAdmin()");
        // LibraryDAO.addAdmin(
        //     "trinhhungqt2004@gmail.com", 
        //     "NguyenHungSky123@", 
        //     "hung123", 
        //     "Trinh Dinh Nguyen Hung", 
        //     1000);
        // LibraryDAO.addAdmin(
        //     "test1@gmail.com", 
        //     "pass1", 
        //     "adminname1", 
        //     "name1", 
        //     2000);
        // LibraryDAO.addAdmin(
        //     "test2@gmail.com", 
        //     "pass2", 
        //     "adminname2", 
        //     "name2", 
        //     3000);
        // System.out.println("Test getAdminList()");
        // for (Admin admin : LibraryDAO.getAdminList()) {
        //     System.out.println("----------------------------");
        //     System.out.println(admin.toString());
        //     System.out.println("----------------------------");
        // }
        // System.out.println("Test getAdminByUsername()");
        // System.out.println((LibraryDAO.getAdminByUsername("adminname2") != null) ? LibraryDAO.getAdminByUsername("adminname2").toString() : "");
        // System.out.println("Test getAdminById()");
        // System.out.println("get Admin at id = 1");
        // System.out.println((LibraryDAO.getAdminById(1) != null) ? LibraryDAO.getAdminById(1).toString() : "");
        // System.out.println("Test getAdminByEmail()");
        // System.out.println("get Admin at email = trinhhungqt2004@gmail.com");
        // System.out.println((LibraryDAO.getAdminByEmail("trinhhungqt2004@gmail.com") != null) ? LibraryDAO.getAdminByEmail("trinhhungqt2004@gmail.com").toString() : "");
        // System.out.println("Test removeAdmin() by id");
        // System.out.println("remove Admin by id = 2");
        // LibraryDAO.removeAdmin(2);
        // for (Admin admin : LibraryDAO.getAdminList()) {
        //     System.out.println("----------------------------");
        //     System.out.println(admin.toString());
        //     System.out.println("----------------------------");
        // }
        // System.out.println("Test removeAdmin() by email");
        // System.out.println("remove Admin by email = test1@gmail.com");
        // LibraryDAO.removeAdmin("test1@gmail.com");
        // for (Admin admin : LibraryDAO.getAdminList()) {
        //     System.out.println("----------------------------");
        //     System.out.println(admin.toString());
        //     System.out.println("----------------------------");
        // }
        // System.out.println("Test updateAdmin()");
        // Admin newadmin = new Admin();
        // newadmin.setName("newAdmin");
        // newadmin.setEmail("newAdminEmail");
        // newadmin.setHashPassword(AuthServices.generateHashedPassword("newAdminPassword"));
        // newadmin.setUsername("newAdminUsername");
        // newadmin.setBalance(2300);
        // LibraryDAO.updateAdmin("test2@gmail.com", newadmin);
        // for (Admin admin : LibraryDAO.getAdminList()) {
        //     System.out.println("----------------------------");
        //     System.out.println(admin.toString());
        //     System.out.println("----------------------------");
        // }
        // test member 
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
            30);
        LibraryDAO.addBook(
            "4309340320", 
            "Book 2",
            "Person2", 
            "publisher2", 
            50);
        LibraryDAO.addBook(
            "0849323242", 
            "Book 3",
            "Person3", 
            "publisher3", 
            10);
        // Sample data
        LibraryDAO.addBook("1234567890", "Java Basics", "John Doe", "Tech Press", 3);
        LibraryDAO.addBook("9876543210", "Effective Java", "Joshua Bloch", "Pearson", 5);
        LibraryDAO.addBook("5555555555", "Clean Code", "Robert Martin", "Prentice Hall", 4);

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
        
        System.out.println();
        
        System.out.println("Test updateBook()");
        System.out.println("update Book with isbn = 0849323242");
        Book b1 = new Book();
        b1.setTotalCopies(100);
        b1.setAvailableCopies(96);
        LibraryDAO.updateBook("0849323242", b1);
        LibraryDAO.getBook("0849323242").printBookInfo();
        
        System.out.println();
        
        System.out.println("Test removeBook()");
        System.out.println("remove Book with isbn = 0849323242");
        LibraryDAO.removeBook("0849323242");
        for (Book b2 : LibraryDAO.bookList()) {
            System.out.println("----------------------------");
            b2.printBookInfo();
            System.out.println("----------------------------");
        }
    }
}

/*
Output:
User table created or already exist.
bookTable table created or already exist.
Test addAdmin()
User table created or already exist.
bookTable table created or already exist.
Connection established.
Admin added successfully
User table created or already exist.
bookTable table created or already exist.
Connection established.
Admin added successfully
User table created or already exist.
bookTable table created or already exist.
Connection established.
Admin added successfully
Test getAdminList()
User table created or already exist.
bookTable table created or already exist.
Connection established.
Retrieve admin list sucessfully
----------------------------
User name: Trinh Dinh Nguyen Hung
User username: hung123
User password: NguyenHungSky123@
User email: trinhhungqt2004@gmail.com
User type: admin
User status: normal
User balance: 1000.0
User id: 1
----------------------------
----------------------------
User name: name1
User username: adminname1
User password: pass1
User email: test1@gmail.com
User type: admin
User status: normal
User balance: 2000.0
User id: 2
----------------------------
----------------------------
User name: name2
User username: adminname2
User password: pass2
User email: test2@gmail.com
User type: admin
User status: normal
User balance: 3000.0
User id: 3
----------------------------
Test getAdminByUsername()
User table created or already exist.
bookTable table created or already exist.
Connection established.
User table created or already exist.
bookTable table created or already exist.
Connection established.
User name: name2
User username: adminname2
User password: pass2
User email: test2@gmail.com
User type: admin
User status: normal
User balance: 3000.0
User id: 3
Test getAdminById()
get Admin at id = 1
User table created or already exist.
bookTable table created or already exist.
Connection established.
User table created or already exist.
bookTable table created or already exist.
Connection established.
User name: Trinh Dinh Nguyen Hung
User username: hung123
User password: NguyenHungSky123@
User email: trinhhungqt2004@gmail.com
User type: admin
User status: normal
User balance: 1000.0
User id: 1
Test getAdminByEmail()
get Admin at email = trinhhungqt2004@gmail.com
User table created or already exist.
bookTable table created or already exist.
Connection established.
User table created or already exist.
bookTable table created or already exist.
Connection established.
User name: Trinh Dinh Nguyen Hung
User username: hung123
User password: NguyenHungSky123@
User email: trinhhungqt2004@gmail.com
User type: admin
User status: normal
User balance: 1000.0
User id: 1
Test removeAdmin() by id
remove Admin by id = 2
User table created or already exist.
bookTable table created or already exist.
Connection established.
Admin is removed
User table created or already exist.
bookTable table created or already exist.
Connection established.
Retrieve admin list sucessfully
----------------------------
User name: Trinh Dinh Nguyen Hung
User username: hung123
User password: NguyenHungSky123@
User email: trinhhungqt2004@gmail.com
User type: admin
User status: normal
User balance: 1000.0
User id: 1
----------------------------
----------------------------
User name: name2
User username: adminname2
User password: pass2
User email: test2@gmail.com
User type: admin
User status: normal
User balance: 3000.0
User id: 3
----------------------------
Test removeAdmin() by email
remove Admin by email = test1@gmail.com
User table created or already exist.
bookTable table created or already exist.
Connection established.
No admin found with email test1@gmail.com.
User table created or already exist.
bookTable table created or already exist.
Connection established.
Retrieve admin list sucessfully
----------------------------
User name: Trinh Dinh Nguyen Hung
User username: hung123
User password: NguyenHungSky123@
User email: trinhhungqt2004@gmail.com
User type: admin
User status: normal
User balance: 1000.0
User id: 1
----------------------------
----------------------------
User name: name2
User username: adminname2
User password: pass2
User email: test2@gmail.com
User type: admin
User status: normal
User balance: 3000.0
User id: 3
----------------------------
Test updateAdmin()
User table created or already exist.
bookTable table created or already exist.
Connection established.
Update admin successfully
User table created or already exist.
bookTable table created or already exist.
Connection established.
Retrieve admin list sucessfully
----------------------------
User name: Trinh Dinh Nguyen Hung
User username: hung123
User password: NguyenHungSky123@
User email: trinhhungqt2004@gmail.com
User type: admin
User status: normal
User balance: 1000.0
User id: 1
----------------------------
----------------------------
User name: newAdmin
User username: newAdminUsername
User password: newAdminPassword
User email: newAdminEmail
User type: admin
User status: normal
User balance: 2300.0
User id: 3
----------------------------
Test addMember()
User table created or already exist.
bookTable table created or already exist.
Connection established.
Member added successfully
User table created or already exist.
bookTable table created or already exist.
Connection established.
Member added successfully
User table created or already exist.
bookTable table created or already exist.
Connection established.
Member added successfully
Test getMemberList()
User table created or already exist.
bookTable table created or already exist.
Connection established.
Retrieve meber list sucessfully
----------------------------
User name: name1
User username: membername1
User password: pass1
User email: member1@gmail.com
User type: member
User status: normal
User balance: 1000.0
User id: 4
----------------------------
----------------------------
User name: name2
User username: membername2
User password: pass2
User email: member2@gmail.com
User type: member
User status: normal
User balance: 2000.0
User id: 5
----------------------------
----------------------------
User name: name3
User username: membername3
User password: pass3
User email: member3@gmail.com
User type: member
User status: normal
User balance: 3000.0
User id: 6
----------------------------
Test getMemberByUsername()
User table created or already exist.
bookTable table created or already exist.
Connection established.
User table created or already exist.
bookTable table created or already exist.
Connection established.
User name: name2
User username: membername2
User password: pass2
User email: member2@gmail.com
User type: member
User status: normal
User balance: 2000.0
User id: 5
Test getMemberById()
get Member at id = 1
User table created or already exist.
bookTable table created or already exist.
Connection established.
member not found

Test getMemberByEmail()
get Member at email = member3@gmail.com
User table created or already exist.
bookTable table created or already exist.
Connection established.
User table created or already exist.
bookTable table created or already exist.
Connection established.
User name: name3
User username: membername3
User password: pass3
User email: member3@gmail.com
User type: member
User status: normal
User balance: 3000.0
User id: 6
Test removeMember() by id
remove Member by id = 2
User table created or already exist.
bookTable table created or already exist.
Connection established.
No member found with id 2.
User table created or already exist.
bookTable table created or already exist.
Connection established.
Retrieve meber list sucessfully
----------------------------
User name: name1
User username: membername1
User password: pass1
User email: member1@gmail.com
User type: member
User status: normal
User balance: 1000.0
User id: 4
----------------------------
----------------------------
User name: name2
User username: membername2
User password: pass2
User email: member2@gmail.com
User type: member
User status: normal
User balance: 2000.0
User id: 5
----------------------------
----------------------------
User name: name3
User username: membername3
User password: pass3
User email: member3@gmail.com
User type: member
User status: normal
User balance: 3000.0
User id: 6
----------------------------
Test removeMember() by email
remove Member by email = member3@gmail.com
User table created or already exist.
bookTable table created or already exist.
Connection established.
Member is removed
User table created or already exist.
bookTable table created or already exist.
Connection established.
Retrieve meber list sucessfully
----------------------------
User name: name1
User username: membername1
User password: pass1
User email: member1@gmail.com
User type: member
User status: normal
User balance: 1000.0
User id: 4
----------------------------
----------------------------
User name: name2
User username: membername2
User password: pass2
User email: member2@gmail.com
User type: member
User status: normal
User balance: 2000.0
User id: 5
----------------------------
Test addBook()
User table created or already exist.
bookTable table created or already exist.
Connection established.
Successfully added book.
User table created or already exist.
bookTable table created or already exist.
Connection established.
Successfully added book.
User table created or already exist.
bookTable table created or already exist.
Connection established.
Successfully added book.

Test bookList()
User table created or already exist.
bookTable table created or already exist.
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
Book's title: Book 3
Book's author: Person3
Book's publisher: publisher3
Book's ISBN: 0849323242
Book's total copies: 10
Book's available copies: 10
----------------------------

Test getBook()
get Member at isbn = 2320321093
User table created or already exist.
bookTable table created or already exist.
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
Connection established.
Book not found.

Test updateBook()
update Book with isbn = 0849323242
User table created or already exist.
bookTable table created or already exist.
Connection established.
Successfully updated book.
User table created or already exist.
bookTable table created or already exist.
Connection established.
Book's title: Book 3
Book's author: Person3
Book's publisher: publisher3
Book's ISBN: 0849323242
Book's total copies: 100
Book's available copies: 96

Test removeBook()
remove Book with isbn = 0849323242
User table created or already exist.
bookTable table created or already exist.
Connection established.
Book is removed.
User table created or already exist.
bookTable table created or already exist.
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
 */

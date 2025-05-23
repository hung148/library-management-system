package com.main.test;

import com.main.entity.Admin;
import com.main.entity.Member;
import com.main.respository.DBInitiailzer;
import com.main.respository.LibraryDAO;

public class TestDB {
    public static void main(String[] args) {
        testAdminAndUser();
    }

    public static void testAdminAndUser() {
        DBInitiailzer.initializeDB();
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
            "username1", 
            "name1", 
            2000);
        LibraryDAO.addAdmin(
            "test2@gmail.com", 
            "pass2", 
            "username2", 
            "name2", 
            3000);
        System.out.println("Test getAdminList()");
        for (Admin admin : LibraryDAO.getAdminList()) {
            System.out.println("----------------------------");
            System.out.println(admin.toString());
            System.out.println("----------------------------");
        }
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
        newadmin.setPassword("newAdminPassword");
        newadmin.setUsername("newAdminUsername");
        newadmin.setBalance(2300);
        LibraryDAO.updateAdmin("test2@gmail.com", newadmin);
        for (Admin admin : LibraryDAO.getAdminList()) {
            System.out.println("----------------------------");
            System.out.println(admin.toString());
            System.out.println("----------------------------");
        }
        // test member 
        System.out.println("Test addMember()");
        LibraryDAO.addMember(
            "member1@gmail.com", 
            "pass1", 
            "username1", 
            "name1", 
            1000);
        LibraryDAO.addMember(
            "member2@gmail.com", 
            "pass2", 
            "username2", 
            "name2", 
            2000);
        LibraryDAO.addMember(
            "member3@gmail.com", 
            "pass3", 
            "username3", 
            "name3", 
            3000);
        System.out.println("Test getMemberList()");
        for (Member member : LibraryDAO.getMemberList()) {
            System.out.println("----------------------------");
            System.out.println(member.toString());
            System.out.println("----------------------------");
        }
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
Test addAdmin()
User table created or already exist.
Connection established.
Admin added successfully
User table created or already exist.
Connection established.
Admin added successfully
User table created or already exist.
Connection established.
Admin added successfully
Test getAdminList()
User table created or already exist.
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
User username: username1
User password: pass1
User email: test1@gmail.com
User type: admin
User status: normal
User balance: 2000.0
User id: 2
----------------------------
----------------------------
User name: name2
User username: username2
User password: pass2
User email: test2@gmail.com
User type: admin
User status: normal
User balance: 3000.0
User id: 3
----------------------------
Test getAdminById()
get Admin at id = 1
User table created or already exist.
Connection established.
User table created or already exist.
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
Connection established.
User table created or already exist.
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
Connection established.
Admin is removed
User table created or already exist.
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
User username: username2
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
Connection established.
No admin found with email test1@gmail.com.
User table created or already exist.
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
User username: username2
User password: pass2
User email: test2@gmail.com
User type: admin
User status: normal
User balance: 3000.0
User id: 3
----------------------------
Test updateAdmin()
User table created or already exist.
Connection established.
Update admin successfully
User table created or already exist.
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
Connection established.
Member added successfully
User table created or already exist.
Connection established.
Member added successfully
User table created or already exist.
Connection established.
Member added successfully
Test getMemberList()
User table created or already exist.
Connection established.
Retrieve meber list sucessfully
----------------------------
User name: name1
User username: username1
User password: pass1
User email: member1@gmail.com
User type: member
User status: normal
User balance: 1000.0
User id: 4
----------------------------
----------------------------
User name: name2
User username: username2
User password: pass2
User email: member2@gmail.com
User type: member
User status: normal
User balance: 2000.0
User id: 5
----------------------------
----------------------------
User name: name3
User username: username3
User password: pass3
User email: member3@gmail.com
User type: member
User status: normal
User balance: 3000.0
User id: 6
----------------------------
Test getMemberById()
get Member at id = 1
User table created or already exist.
Connection established.
member not found

Test getMemberByEmail()
get Member at email = member3@gmail.com
User table created or already exist.
Connection established.
User table created or already exist.
Connection established.
User name: name3
User username: username3
User password: pass3
User email: member3@gmail.com
User type: member
User status: normal
User balance: 3000.0
User id: 6
Test removeMember() by id
remove Member by id = 2
User table created or already exist.
Connection established.
No member found with id 2.
User table created or already exist.
Connection established.
Retrieve meber list sucessfully
----------------------------
User name: name1
User username: username1
User password: pass1
User email: member1@gmail.com
User type: member
User status: normal
User balance: 1000.0
User id: 4
----------------------------
----------------------------
User name: name2
User username: username2
User password: pass2
User email: member2@gmail.com
User type: member
User status: normal
User balance: 2000.0
User id: 5
----------------------------
----------------------------
User name: name3
User username: username3
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
Connection established.
Member is removed
User table created or already exist.
Connection established.
Retrieve meber list sucessfully
----------------------------
User name: name1
User username: username1
User password: pass1
User email: member1@gmail.com
User type: member
User status: normal
User balance: 1000.0
User id: 4
----------------------------
----------------------------
User name: name2
User username: username2
User password: pass2
User email: member2@gmail.com
User type: member
User status: normal
User balance: 2000.0
User id: 5
----------------------------
 */

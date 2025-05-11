package com.example.model;

public class BorrowedBook {
    private int id;
    private String book_isbn; // reference to isbn of books table
    private int member_id; // reference to id of users table
    private String borrow_date;
    private String deadline;
    private String actualReturnDate;
    private String status;
    private double fineLate;
    private double fineLost; // different fine for each borrow book depend on its value
    // borrow date
    // deadline 
    // actual return date 
    // status pending, returned, pending late, returned late, lost(pay fine)

    public BorrowedBook(int id, String book_isbn, int member_id, String borrow_date, String deadline, String actualReturnDate, String status, double fineLate, double fineLost) {
        this.id = id;
        this.book_isbn = book_isbn.trim();
        this.member_id = member_id;
        this.borrow_date = borrow_date;
        this.deadline = deadline;
        this.actualReturnDate = actualReturnDate;
        this.status = status;
        this.fineLate = fineLate;
        this.fineLost = fineLost;
    }

    public String getBook_isbn() {
        return book_isbn;
    }

    public void setBook_isbn(String book_isbn) {
        this.book_isbn = book_isbn.trim();
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBorrow_date() {
        return borrow_date;
    }

    public void setBorrow_date(String borrow_date) {
        this.borrow_date = borrow_date;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getActualReturnDate() {
        return actualReturnDate;
    }

    public void setActualReturnDate(String actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getFineLate() {
        return fineLate;
    }

    public void setFineLate(double fineLate) {
        this.fineLate = fineLate;
    }

    public double getFineLost() {
        return fineLost;
    }

    public void setFineLost(double fineLost) {
        this.fineLost = fineLost;
    }
    
}

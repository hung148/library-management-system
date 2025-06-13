package com.main.entity;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BorrowedBook {
	private final double LATE_FINE = 0.50;
	
    private int memberID;
    private Book book;
    private LocalDate borrowDate;
    private LocalDate deadline;
    private LocalDate returnedDate;
    private String status;
    private double lateFine;
    private double lostFine;
    

    public BorrowedBook(int memberID, Book book, LocalDate borrowDate) {
		super();
		this.memberID = memberID;
		this.book = book;
		this.borrowDate = borrowDate;
		this.deadline = borrowDate.plusWeeks(2);
		this.status = "pending";
		lateFine = 0.0;
		lostFine = 0.0;
	}



	public Book getBook() {
		return book;
	}

	

	public void setBook(Book book) {
		this.book = book;
	}



	public String getStatus() {
		return status;
	}



	public void updateStatusAndFine() {
	    if (returnedDate == null) return;

	    if (returnedDate.isAfter(deadline)) {
	        this.status = "late";
	        int daysLate = (int) ChronoUnit.DAYS.between(deadline, returnedDate);
	        this.lateFine = daysLate * LATE_FINE;
	    } else {
	        this.status = "returned";
	        this.lateFine = 0.0;
	    }
	}



	public int getMemberID() {
		return memberID;
	}



	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}



	public LocalDate getBorrowDate() {
		return borrowDate;
	}



	public void setBorrowDate(LocalDate borrowDate) {
		this.borrowDate = borrowDate;
	}



	public LocalDate getDeadline() {
		return deadline;
	}



	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}



	public LocalDate getReturnedDate() {
		return returnedDate;
	}



	public void setReturnedDate(LocalDate returnedDate) {
		this.returnedDate = returnedDate;
	}



	public double getLateFine() {
		return lateFine;
	}



	public double getLostFine() {
		return lostFine;
	}



	public void setLostFine(double lostFine) {
		this.lostFine = lostFine;
	}


	
    @Override
	public String toString() {
		return "BorrowedBook [bookISBN=" + book.get_ISBN() + ", memberID=" + memberID + ", borrowDate="
				+ borrowDate + ", deadline=" + deadline + ", returnedDate=" + returnedDate + ", status=" + status
				+ ", lateFine=" + lateFine + ", lostFine=" + lostFine + "]";
	}
}

package com.main.entity;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.main.respository.LibraryDAO;

public class BorrowedBook {
	private static final double LATE_FINE = 0.50; // 50 cents ?
	
    private int memberID;
    private Book book;
    private LocalDate borrowDate;
    private LocalDate deadline;
    private LocalDate returnedDate;
    private String status;
    private double lateFine;
    private double lostFine;
	private boolean isPayFine = false;
	private boolean isReturnedAfterLate = false;
	private boolean isPayFineAfterLost = false;
	private boolean isReturnedAfterLost = false;
	private int id;
	
	

    public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public boolean isPayFineAfterLost() {
		return isPayFineAfterLost;
	}



	public void setPayFineAfterLost(boolean isPayFineAfterLost) {
		this.isPayFineAfterLost = isPayFineAfterLost;
	}



	public boolean isReturnedAfterLost() {
		return isReturnedAfterLost;
	}



	public void setReturnedAfterLost(boolean isReturnedAfterLost) {
		this.isReturnedAfterLost = isReturnedAfterLost;
	}



	public boolean isPayFine() {
		return isPayFine;
	}



	public void setPayFine(boolean isPayFine) {
		this.isPayFine = isPayFine;
	}



	public boolean isReturnedAfterLate() {
		return isReturnedAfterLate;
	}



	public void setReturnedAfterLate(boolean isReturnedAfterLate) {
		this.isReturnedAfterLate = isReturnedAfterLate;
	}



	public BorrowedBook(int memberID, Book book, LocalDate borrowDate, LocalDate deadline) {
		super();
		this.memberID = memberID;
		this.book = book;
		this.borrowDate = borrowDate;
		this.deadline = deadline;
		this.status = "pending";
		lateFine = 0.0;
		lostFine = book.getValue(); // book value
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
		LocalDate today = LocalDate.now();
		System.out.println(today.toString());
	    if (returnedDate == null)  {
			long daysLate = ChronoUnit.DAYS.between(deadline, today);
			System.out.println(daysLate);
			if (daysLate > 0 && daysLate <= 14) {
				// Mark as late
				LibraryDAO.updateBorrowedBookStatus(this, "late");
				this.status = "late";
				this.lateFine = daysLate * LATE_FINE;
				LibraryDAO.updateFine(memberID, this.lateFine, 1);
			} else if (daysLate > 14) {
				// Mark as lost
				System.out.println(daysLate);
				LibraryDAO.updateBorrowedBookStatus(this, "lost");
				this.status = "lost";
				LibraryDAO.updateFine(memberID, this.lostFine, 0);
			} else {
				LibraryDAO.updateBorrowedBookStatus(this, "pending");
				this.status = "pending";
				LibraryDAO.updateBorrowedBookIsPayFine(this, false);
				LibraryDAO.updateBorrowedBookIsPayFineAfterLost(this, false);
				LibraryDAO.updateBorrowedBookIsReturned(this, false);
				LibraryDAO.updateBorrowedBookIsReturnedAfterLost(this, false);
			}
		} else {
			if (returnedDate.isAfter(deadline)) {
				long dayAfter = ChronoUnit.DAYS.between(deadline, returnedDate); 
				if (dayAfter > 14) {
					this.status = "lost";
				} else {
					this.status = "late";
					int daysLate = (int) ChronoUnit.DAYS.between(deadline, returnedDate);
					this.lateFine = daysLate * LATE_FINE;
				}
			} else {
				this.status = "returned";
				this.lateFine = 0.0;
			}
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
	
	public void setLateFine(double lateFine) {
		this.lateFine = lateFine;
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

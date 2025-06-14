package com.main.entity;

public class Book {
    private String _ISBN = "";
    private String _title = "";
    private String _author = "";
    private String _publisher = "";
    private int _totalCopies = 0;
    private int _availableCopies = 0;
    
    public Book() {
    	this(null, null, null, null, 0);
    }
    
    public Book(String ISBN, String title, String author, String publisher, int totalCopies) {
    	_ISBN = ISBN;
    	_title = title;
    	_author = author;
    	_publisher = publisher;
    	setTotalCopies(totalCopies);
    	setAvailableCopies(totalCopies);
    }
    
    // Getters
    // getter method signature needs to be in the format get + PropertyName in order to use PropertyValueFactory
    public String get_ISBN() { return _ISBN; }
    public String get_title() { return _title; }
    public String get_author() { return _author; }
    public String get_publisher() { return _publisher; }
    public int getTotalCopies() { return _totalCopies; }
    public int getAvailableCopies() { return _availableCopies;}
    // Setters
    public void set_ISBN(String _ISBN) {
        this._ISBN = _ISBN;
    }
    public void set_title(String _title) {
        this._title = _title;
    }
    public void set_author(String _author) {
        this._author = _author;
    }
    public void set_publisher(String _publisher) {
        this._publisher = _publisher;
    }
    public void setTotalCopies(int totalCopies ) {
    	_totalCopies = totalCopies;
    }
    
    public void setAvailableCopies(int availableCopies ) {
    	_availableCopies = availableCopies;
    }


    public void printBookInfo() {
    	System.out.println("Book's title: " + _title);
    	System.out.println("Book's author: " + _author);
    	System.out.println("Book's publisher: " + _publisher);
    	System.out.println("Book's ISBN: " + _ISBN);
    	System.out.println("Book's total copies: " + _totalCopies);
    	System.out.println("Book's available copies: " + _availableCopies);
    }



    @Override
    public String toString() {
        return String.format("%s by %s (ISBN: %s)",
                get_title(), get_author(), get_ISBN());
    }
}

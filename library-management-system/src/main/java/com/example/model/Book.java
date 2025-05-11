package com.example.model;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private int totalCopies;
    private int availableCopies;

    public Book(String isbn, String title, String author, String publisher, int totalCopies, int availableCopies) {
        this.isbn = isbn.trim();
        this.title = title.trim();
        this.author = author.trim();
        this.publisher = publisher.trim();
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }


    // getter and setter 
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            return;
        } 
        this.isbn = isbn.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return;
        }
        this.title = title.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            return;
        }
        this.author = author.trim();
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        if (publisher == null || publisher.trim().isEmpty()) {
            return;
        }
        this.publisher = publisher;
    }

    public int getTotalCopies() {

        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        if (totalCopies < 0) {
            return;
        }
        this.totalCopies = totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        if (availableCopies < 0) {
            return;
        }
        this.availableCopies = availableCopies;
    } 

    @Override
    public String toString() {
        String bookInfo = ("Book title: " + title + "\n" + 
                           "Book ISBN: " + isbn + "\n" + 
                           "Book author: " + author + "\n" +
                           "Book publisher: " + publisher + "\n" + 
                           "Total copices: " + totalCopies + "\n" + 
                           "Avialable copies: " + availableCopies + "\n");

        return bookInfo;
    }
}

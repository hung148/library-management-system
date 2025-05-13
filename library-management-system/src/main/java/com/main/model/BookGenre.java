package com.main.model;

public class BookGenre {
    private String bookISBN;
    private int genre_id;

    public BookGenre(String bookISBN, int genre_id) {
        this.bookISBN = bookISBN.trim();
        this.genre_id = genre_id;
    }

    public String getBookISBN() {
        return bookISBN;
    }

    public void setBookISBN(String bookISBN) {
        this.bookISBN = bookISBN;
    }

    public int getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }

    
}

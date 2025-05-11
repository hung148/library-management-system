package com.example.model;

public class Genre {
    private int genreId;
    private String name;

    public Genre(String name) {
        this.name = name;
    }
    
    public Genre(int genreId, String name) {
        this.name = name.toLowerCase().trim();
        this.genreId = genreId;
    }

    public int getGenreId() {
        return genreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toLowerCase().trim();
    }

    
}

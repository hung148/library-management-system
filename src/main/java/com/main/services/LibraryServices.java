package com.main.services;

import com.main.entity.Admin;
import com.main.entity.Book;
import com.main.entity.BorrowedBook;
import com.main.entity.Member;
import com.main.respository.LibraryDAO;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.text.similarity.LevenshteinDistance;
public class LibraryServices {
    // search book, member function
    public static Book[] searchBook(String bookTitle, String bookISBN, String author, String publisher) throws Exception {
        Book[] bookList = LibraryDAO.bookList();
        int maxResult = 10;
        Map<Book, Integer> scored = new HashMap<>();
        LevenshteinDistance distance = new LevenshteinDistance(10);
        if (bookTitle != null) {
            searchBookHelper(distance, bookTitle, bookList, maxResult, "get_title", scored);
        } if (bookISBN != null) {
            searchBookHelper(distance, bookISBN, bookList, maxResult, "get_ISBN", scored);
        } if (author != null) {
            searchBookHelper(distance, author, bookList, maxResult, "get_author", scored);
        } if (publisher != null) {
            searchBookHelper(distance, author, bookList, maxResult, "get_publisher", scored);
        }

        // Sort by similarity
        List<Map.Entry<Book, Integer>> sorted = new ArrayList<>(scored.entrySet());
        sorted.sort(Comparator.comparingInt((Map.Entry<Book, Integer> e) -> e.getValue()));

        // Limit to top 10
        List<Book> results = new ArrayList<>();
        for (int i = 0; i < Math.min(maxResult, sorted.size()); i++) {
            results.add(sorted.get(i).getKey());
        }
        return results.toArray(new Book[0]);
    }

    private static void searchBookHelper(
        LevenshteinDistance distance, String keywords, Book[] bookList, 
        int maxResult, String methodName, Map<Book, Integer> scored) throws Exception {
        Method methodToCall = Book.class.getMethod(methodName);;
        for (Book book : bookList) {
            String compare = (String) methodToCall.invoke(book);
            System.out.println(compare.toLowerCase() + " " + keywords.toLowerCase());
            int score = distance.apply(keywords.toLowerCase(), compare.toLowerCase());

            if (keywords.toLowerCase().equals(compare.toLowerCase())) {
                int tempscore = 0;
                if (tempscore < score) {
                    score = tempscore;
                }
            } else if (compare.toLowerCase().startsWith(keywords.toLowerCase())) {
                int tempscore = 1;
                if (tempscore < score) {
                    score = tempscore;
                }
            } else if (compare.toLowerCase().contains(keywords.toLowerCase())) {
                int tempscore = 2;
                if (tempscore < score) {
                    score = tempscore;
                } 
            } else {
                int count = 0;
                for (int i = 0; i < keywords.toLowerCase().length(); i++) {
                    for (int j = 0; j < compare.toLowerCase().length(); j++) {
                        if (keywords.toLowerCase().charAt(i) == compare.toLowerCase().charAt(j)) {
                           count++;
                        }
                    }
                }
                if (count >= keywords.length()) {
                    int tempscore = 3;
                    if (tempscore < score) {
                        score = tempscore;
                    }  
                }
            }
            // Keep best score only
            if (score != -1) {
                scored.merge(book, score, Math::min); // When a new score is computed for a book, update it only if it’s better
            }
                
        }
    }
    
    // only admin can do this
    public static Member searchMember(Admin admin, Member member) {
        return null;
    }
    // member search borrowed book function
    
}

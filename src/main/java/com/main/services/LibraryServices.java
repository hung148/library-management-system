package com.main.services;

import com.main.entity.Admin;
import com.main.entity.Book;
import com.main.entity.BorrowedBook;
import com.main.entity.Member;
import com.main.respository.LibraryDAO;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.text.similarity.LevenshteinDistance;
public class LibraryServices {
    // search book, member function
    public static Book[] searchBook(String bookTitle, String bookISBN, String author, String publisher) throws Exception {
        Book[] bookList = LibraryDAO.bookList();
        int maxResult = 10;
        LevenshteinDistance distance = new LevenshteinDistance(null);
        if (bookTitle != null) {
            return searchBookHelper(distance, bookTitle, bookList, maxResult, "get_title");
        } else if (bookISBN != null) {
            return searchBookHelper(distance, bookISBN, bookList, maxResult, "get_ISBN");
        } else if (author != null) {
            return searchBookHelper(distance, author, bookList, maxResult, "get_author");
        } else if (publisher != null) {
            return searchBookHelper(distance, author, bookList, maxResult, "get_publisher");
        }
        return null;
    }

    private static Book[] searchBookHelper(
        LevenshteinDistance distance, String keywords, Book[] bookList, 
        int maxResult, String methodName) throws Exception {
        List<Map.Entry<Book, Integer>> scored = new ArrayList<>();
        Method methodToCall = Book.class.getMethod(methodName);;
        for (Book book : bookList) {
            String compare = (String) methodToCall.invoke(book);
            int score = distance.apply(keywords.toLowerCase(), compare.toLowerCase());
            scored.add(Map.entry(book, score));
        }
        // Sort by similarity (lower distance = more similar)
        scored.sort(Comparator.comparingInt(Map.Entry::getValue));

        // Limit to top 10 
        List<Book> results = new ArrayList<>();
        for (int i = 0; i < Math.min(maxResult, scored.size()); i++) {
            results.add(scored.get(i).getKey());
        }
        return results.toArray(new Book[0]);
    }
    
    // only admin can do this
    public static Member searchMember(Admin admin, Member member) {
        return null;
    }
    // member search borrowed book function
    
}

package com.example.maven_demo.manager;

import com.example.maven_demo.models.Book;
import com.example.maven_demo.models.User;

import java.util.ArrayList;

public interface BookManager {
    Book save(Book book, User user);

    ArrayList<Book> getByUser(User user);
}

package com.example.maven_demo.manager.impl;

import com.example.maven_demo.manager.BookManager;
import com.example.maven_demo.manager.UserManager;
import com.example.maven_demo.models.Book;
import com.example.maven_demo.models.User;


import java.sql.*;
import java.util.ArrayList;

import com.example.maven_demo.models.UserBookJoin;
import com.example.maven_demo.provider.DBConnectionProvider;
import lombok.SneakyThrows;

public class BookManagerImpl implements BookManager {

    private final DBConnectionProvider provider = DBConnectionProvider.getInstance();

    private final UserManager userManager = new UserManagerImpl();

    @SneakyThrows
    @Override
    public Book save(Book book, User user) {
        System.out.println(book);
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO books (bookName, createdAt, author) VALUES (?,?,?)";

        try {

            preparedStatement = provider.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, book.getName());
            preparedStatement.setDate(2, (Date) book.getCreatedAt());
            preparedStatement.setInt(3, user.getId());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating book failed, no ID obtained.");
                }
            }
            System.out.println("Book added!");
        }

        catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }

        return book;
    }

    @SneakyThrows
    @Override
    public ArrayList<Book> getByUser(User user) {
        ArrayList<Book> res = new ArrayList<>();
        String query = "SELECT * FROM books WHERE books.author = ?";

        PreparedStatement statement = provider.getConnection().prepareStatement(query);
        statement.setInt(1, user.getId());
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Book book = Book.builder()
                    .name(rs.getString("bookName"))
                    .createdAt(rs.getDate("createdAt"))
                    .author(user)
                    .authorId(rs.getInt("author"))
                    .build();
            res.add(book);
        }
        return res;
    }
    @SneakyThrows
    @Override
    public ArrayList<UserBookJoin> getAllBooks() {
        ArrayList<UserBookJoin> res = new ArrayList<>();
        String query = "SELECT name,surname,bookName FROM (users u INNER JOIN books b ON u.id = b.author) ;";

        PreparedStatement statement = provider.getConnection().prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            UserBookJoin book = UserBookJoin.builder()
                    .name(rs.getString("name"))
                    .surname(rs.getString("surname"))
                    .bookName(rs.getString("bookName"))
                    .build();
            res.add(book);
        }
        return res;
    }
}

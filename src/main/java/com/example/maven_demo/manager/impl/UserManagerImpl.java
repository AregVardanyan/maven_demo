package com.example.maven_demo.manager.impl;

import com.example.maven_demo.manager.UserManager;
import com.example.maven_demo.models.User;
import com.example.maven_demo.models.enums.Gender;
import com.example.maven_demo.provider.DBConnectionProvider;
import lombok.SneakyThrows;

import java.sql.*;

public class UserManagerImpl implements UserManager {

    private final DBConnectionProvider provider = DBConnectionProvider.getInstance();

    @Override
    @SneakyThrows
    public boolean existByEmail(String email) {
        String query = "SELECT EXISTS(SELECT 1 FROM users WHERE email = ?)";
        PreparedStatement stmt = provider.getConnection().prepareStatement(query);
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        return rs.next() && rs.getBoolean(1);
    }

    @Override
    @SneakyThrows
    public User save(User user) {
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO users (name, surname, email, password, age, gender) VALUES (?,?,?,?,?,?)";
        try {

            preparedStatement = provider.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setInt(5, user.getAge());
            preparedStatement.setString(6, user.getGender().name());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        return user;
    }

    @Override
    @SneakyThrows
    public User getByEmailAndPassword(String email, String password) {
        String query = "SELECT * FROM users u where u.email = ? and u.password = ?";
        PreparedStatement statement = provider.getConnection().prepareStatement(query);
        statement.setString(1, email);
        statement.setString(2, password);
        ResultSet rs = statement.executeQuery();
        boolean firstUserExist = rs.next();
        if (firstUserExist) {
            return User.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .surname(rs.getString("surname"))
                    .email(email)
                    .password(password)
                    .gender(Gender.valueOf(rs.getString("gender")))
                    .age(rs.getInt("age"))
                    .build();
        } else {
            return null;
        }
    }

    @Override
    @SneakyThrows
    public User getById(int id) {
        String query = "SELECT * FROM users u where u.id = ?";
        PreparedStatement statement = provider.getConnection().prepareStatement(query);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        boolean firstUserExist = rs.next();
        if (firstUserExist) {
            return User.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .surname(rs.getString("surname"))
                    .email(rs.getString("email"))
                    .password(rs.getString("password"))
                    .gender(Gender.valueOf(rs.getString("gender")))
                    .age(rs.getInt("age"))
                    .build();
        } else {
            return null;
        }
    }

    @Override
    public void update(User user) {
        String query = "UPDATE users SET name = ?, surname = ?, age = ?, gender=? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = provider.getConnection().prepareStatement(query);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setString(4, user.getGender().name());
            preparedStatement.setInt(5, user.getId());
            preparedStatement.executeUpdate();
            System.out.println("Record is updated in the books table!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

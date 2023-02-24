package com.example.maven_demo.manager.impl;

import com.example.maven_demo.manager.PacientManager;
import com.example.maven_demo.models.Doctor;
import com.example.maven_demo.models.Pacient;
import com.example.maven_demo.models.enums.Gender;
import com.example.maven_demo.provider.DBConnectionProvider;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PacientManagerImpl implements PacientManager {

    private final DBConnectionProvider provider = DBConnectionProvider.getInstance();
    @SneakyThrows
    @Override
    public Pacient save(Pacient pacient) {
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO pacients (name, surname, email, password, gender, age) VALUES (?,?,?,?)";
        try {

            preparedStatement = provider.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, pacient.getName());
            preparedStatement.setString(2, pacient.getSurname());
            preparedStatement.setString(3, pacient.getEmail());
            preparedStatement.setString(4, pacient.getPassword());
            preparedStatement.setString(4, pacient.getGender().name());
            preparedStatement.setInt(4, pacient.getAge());

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pacient.setId(generatedKeys.getInt(1));
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
        return pacient;
    }

    @SneakyThrows
    @Override
    public boolean existByEmail(String email) {
        String query = "SELECT EXISTS(SELECT 1 FROM pacients WHERE email = ?)";
        PreparedStatement stmt = provider.getConnection().prepareStatement(query);
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        return rs.next() && rs.getBoolean(1);
    }

    @SneakyThrows
    @Override
    public Pacient getByEmailAndPassword(String email, String password) {
        String query = "SELECT * FROM pacinets d where d.email = ? and d.password = ?";
        PreparedStatement statement = provider.getConnection().prepareStatement(query);
        statement.setString(1, email);
        statement.setString(2, password);
        ResultSet rs = statement.executeQuery();
        boolean firstUserExist = rs.next();
        if (firstUserExist) {
            return Pacient.builder()
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

    @SneakyThrows
    @Override
    public Pacient getById(int id) {
        String query = "SELECT * FROM doctors d where d.id = ?";
        PreparedStatement statement = provider.getConnection().prepareStatement(query);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        boolean firstUserExist = rs.next();
        if (firstUserExist) {
            return Pacient.builder()
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
}

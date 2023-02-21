package com.example.maven_demo;

import lombok.SneakyThrows;

import java.sql.*;

public class Main {


    public static void main(String[] args) {
        Application application =  new Application();
        application.start();
    }

//    @SneakyThrows
//    public static void main(String[] args) {
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        Connection connection =  DriverManager.getConnection("jdbc:mysql://localhost:3306/test_db", "root", "devm");
//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery("select * from users");
//        while (resultSet.next()){
//            int id = resultSet.getInt("id");
//            String name = resultSet.getString("name");
//            String surname = resultSet.getString("surname");
//            System.out.println(String.format("Id = %s, name = %s, surname=%s",id, name, surname ));
//        }
//
//        System.out.println("********************* ");
//        int id = 1;
//        String name = "Poxos";
//
//        PreparedStatement preparedStatement = connection.prepareStatement("select * from users where id = ? and name = ?");
//        preparedStatement.setInt(1, id);
//        preparedStatement.setString(2, name);
//        ResultSet rs = preparedStatement.executeQuery();
//        while (rs.next()){
//            int id1 = rs.getInt("id");
//            Date birthDate = rs.getDate("birth_date");
//            System.out.println(String.format("id = %s, birthDate = %s", id1, birthDate));
//
//        }
//
//
//    }
}

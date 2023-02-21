package com.example.maven_demo.provider;

import com.mysql.cj.jdbc.Driver;
import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnectionProvider {


    private  static  volatile DBConnectionProvider instance = new DBConnectionProvider();

    private String driverName;
    private String dbUrl;
    private String username;
    private String password;

    private Connection connection;

    @SneakyThrows
    private DBConnectionProvider(){
        loadProperties();
        Class.forName(driverName);
    }

    public static synchronized DBConnectionProvider getInstance() {
        return instance;
    }


    @SneakyThrows
    public Connection getConnection(){
        if(connection==null || connection.isClosed()){
            connection = DriverManager.getConnection(dbUrl, username, password);
        }
        return connection;
    }

    @SneakyThrows
    private void loadProperties() {
        Properties properties = new Properties();
        properties.load(new FileInputStream(
                "C:\\Users\\aregv\\OneDrive\\Desktop\\maven_demo\\src\\main\\resources\\application.properties"));
        driverName = properties.getProperty("db.driver.class.name");
        dbUrl = properties.getProperty("db.url");
        username = properties.getProperty("db.username");
        password = properties.getProperty("db.password");
    }

}
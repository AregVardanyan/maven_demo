package com.example.maven_demo;

import com.example.maven_demo.manager.DoctorManager;
import com.example.maven_demo.manager.impl.DoctorManagerImpl;
import com.example.maven_demo.models.Doctor;
import lombok.SneakyThrows;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;

public class Main {


    public static void main(String[] args) {
        Application app = new Application();
        app.start();

    }
}

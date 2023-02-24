package com.example.maven_demo.manager.impl;

import com.example.maven_demo.manager.DoctorManager;
import com.example.maven_demo.models.Doctor;
import com.example.maven_demo.models.TimeInterval;
import com.example.maven_demo.models.enums.WorkStatus;
import com.example.maven_demo.provider.DBConnectionProvider;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DoctorManagerImpl implements DoctorManager {

    private final DBConnectionProvider provider = DBConnectionProvider.getInstance();

    @SneakyThrows
    @Override
    public boolean existByEmail(String email) {
        String query = "SELECT EXISTS(SELECT 1 FROM doctors WHERE email = ?)";
        PreparedStatement stmt = provider.getConnection().prepareStatement(query);
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        return rs.next() && rs.getBoolean(1);
    }

    @SneakyThrows
    @Override
    public Doctor save(Doctor doctor) {
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO doctors (name, surname, email, password) VALUES (?,?,?,?)";
        try {

            preparedStatement = provider.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, doctor.getName());
            preparedStatement.setString(2, doctor.getSurname());
            preparedStatement.setString(3, doctor.getEmail());
            preparedStatement.setString(4, doctor.getPassword());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    doctor.setId(generatedKeys.getInt(1));
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
        return doctor;
    }

    @SneakyThrows
    @Override
    public Doctor getByEmailAndPassword(String email, String password) {
        String query = "SELECT * FROM doctors d where d.email = ? and d.password = ?";
        PreparedStatement statement = provider.getConnection().prepareStatement(query);
        statement.setString(1, email);
        statement.setString(2, password);
        ResultSet rs = statement.executeQuery();
        boolean firstUserExist = rs.next();
        if (firstUserExist) {
            return Doctor.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .surname(rs.getString("surname"))
                    .email(email)
                    .password(password)
                    .build();
        } else {
            return null;
        }
    }

    @SneakyThrows
    @Override
    public Doctor getById(int id) {
        String query = "SELECT * FROM doctors d where d.id = ?";
        PreparedStatement statement = provider.getConnection().prepareStatement(query);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        boolean firstUserExist = rs.next();
        if (firstUserExist) {
            return Doctor.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .surname(rs.getString("surname"))
                    .email(rs.getString("email"))
                    .password(rs.getString("password"))
                    .build();
        } else {
            return null;
        }
    }


    @SneakyThrows
    @Override
    public List<TimeInterval> timeIntervalsByDoctor(int doctorId) {
        List<TimeInterval> res = new ArrayList<>();
        String query = "SELECT * FROM time_intervals t where t.doctor_id = ?";
        PreparedStatement statement = provider.getConnection().prepareStatement(query);
        statement.setInt(1, doctorId);
        ResultSet rs = statement.executeQuery();
        while (rs.next()){
            res.add(
                    TimeInterval.builder()
                            .id(rs.getInt("id"))
                            .end(rs.getTime("end"))
                            .start(rs.getTime("start"))
                            .doctorId(rs.getInt("doctor_id"))
                            .workStatus(WorkStatus.valueOf(rs.getString("work_status"))).build()
            );
        }
        return res;
    }

    @SneakyThrows
    @Override
    public void setRestWorkTime(TimeInterval timeInterval) {
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO users (doctor_id, start, end, status) VALUES (?,?,?,?,?,?)";
        try {

            preparedStatement = provider.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, timeInterval.getDoctorId());
            preparedStatement.setTime(2, timeInterval.getStart());
            preparedStatement.setTime(3, timeInterval.getStart());
            preparedStatement.setString(4, timeInterval.getWorkStatus().name());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    timeInterval.setId(generatedKeys.getInt(1));
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
    }

    public void endWorkDay() {
        clearDemands();
        clearWorkTimeIntervals();
    }

    @SneakyThrows
    @Override
    public void clearDemands() {
        String query = "DELETE FROM demands";
        PreparedStatement stmt = provider.getConnection().prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

    }

    @SneakyThrows
    @Override
    public void clearWorkTimeIntervals() {
        String query = "DELETE FROM time_intervals t WHERE status = ?";
        PreparedStatement stmt = provider.getConnection().prepareStatement(query);
        stmt.setString(1,WorkStatus.WORK.name());
        ResultSet rs = stmt.executeQuery();
    }

    @SneakyThrows
    @Override
    public ArrayList<Doctor> getAllDoctors() {
        ArrayList<Doctor> res = new ArrayList<>();
        String query = "SELECT name,surname FROM doctors";

        PreparedStatement statement = provider.getConnection().prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Doctor doctor = Doctor.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .surname(rs.getString("surname"))
                    .email(rs.getString("email"))
                    .password(rs.getString("password"))
                    .build();
        }
        return res;
    }


}

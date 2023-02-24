package com.example.maven_demo.manager.impl;

import com.example.maven_demo.manager.DemandManager;
import com.example.maven_demo.manager.DoctorManager;
import com.example.maven_demo.models.Demand;
import com.example.maven_demo.models.Doctor;
import com.example.maven_demo.models.TimeInterval;
import com.example.maven_demo.models.enums.DemandStatus;
import com.example.maven_demo.models.enums.WorkStatus;
import com.example.maven_demo.provider.DBConnectionProvider;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DemandManagerImpl implements DemandManager {

    private final DBConnectionProvider provider = DBConnectionProvider.getInstance();

    private final DoctorManager demandManager = new DoctorManagerImpl();
    @SneakyThrows
    @Override
    public Demand getById(int id) {
        String query = "SELECT * FROM demands d where d.id = ?";
        PreparedStatement statement = provider.getConnection().prepareStatement(query);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        boolean firstUserExist = rs.next();
        if (firstUserExist) {
            return   Demand.builder()
                    .message(rs.getString("message"))
                    .end(rs.getTime("end"))
                    .start(rs.getTime("start"))
                    .doctorId(rs.getInt("doctor_id"))
                    .pacientId(rs.getInt("pacient_id"))
                    .demandStatus(DemandStatus.valueOf(rs.getString("demand_status"))).build();
        } else {
            return null;
        }
    }

    @SneakyThrows
    @Override
    public void answerDemand(int demandId, DemandStatus status) {
        Demand demand = getById(demandId);
        String query = "UPDATE demands SET status = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = provider.getConnection().prepareStatement(query);
            preparedStatement.setString(1, status.name());
            preparedStatement.setInt(2, demandId);
            preparedStatement.executeUpdate();
            System.out.println("Record is updated in the books table!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if(status.equals(DemandStatus.ACCEPT)){
            TimeInterval timeInterval = TimeInterval.builder()
                    .doctorId(demand.getDoctorId())
                    .start(demand.getStart())
                    .end(demand.getEnd())
                    .workStatus(WorkStatus.WORK).build();
            demandManager.setRestWorkTime(timeInterval);
        }
    }



    @SneakyThrows
    @Override
    public List<Demand> demandsByDoctor(int doctorId) {
        List<Demand> res = new ArrayList<>();
        String query = "SELECT * FROM demands t where t.doctor_id = ?";
        PreparedStatement statement = provider.getConnection().prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while (rs.next()){
            res.add(
                    Demand.builder()
                            .id(rs.getInt("id"))
                            .message(rs.getString("message"))
                            .end(rs.getTime("end"))
                            .start(rs.getTime("start"))
                            .doctorId(rs.getInt("doctor_id"))
                            .pacientId(rs.getInt("pacient_id"))
                            .demandStatus(DemandStatus.valueOf(rs.getString("demand_status"))).build()
            );
        }
        return res;
    }

    @SneakyThrows
    @Override
    public List<Demand> demandsByPacient(int pacientId) {
        List<Demand> res = new ArrayList<>();
        String query = "SELECT * FROM demands t where t.pacient_id = ?";
        PreparedStatement statement = provider.getConnection().prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while (rs.next()){
            res.add(
                    Demand.builder()
                            .id(rs.getInt("id"))
                            .message(rs.getString("message"))
                            .end(rs.getTime("end"))
                            .start(rs.getTime("start"))
                            .doctorId(rs.getInt("doctor_id"))
                            .pacientId(rs.getInt("pacient_id"))
                            .demandStatus(DemandStatus.valueOf(rs.getString("demand_status"))).build()
            );
        }
        return res;
    }

    @SneakyThrows
    @Override
    public Demand save(Demand demand) {
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO demands (message, pacient_id, doctor_id, start, end, status) VALUES (?,?,?,?,?,?)";
        try {

            preparedStatement = provider.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, demand.getMessage());
            preparedStatement.setInt(2, demand.getPacientId());
            preparedStatement.setInt(3, demand.getDoctorId());
            preparedStatement.setTime(4, demand.getStart());
            preparedStatement.setTime(5, demand.getStart());
            preparedStatement.setString(6, demand.getDemandStatus().name());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    demand.setId(generatedKeys.getInt(1));
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
        return demand;
    }

}

package com.example.maven_demo.manager;

import com.example.maven_demo.models.Demand;
import com.example.maven_demo.models.Doctor;
import com.example.maven_demo.models.enums.DemandStatus;

import java.util.List;

public interface DemandManager {

    Demand getById(int id);

    void answerDemand(int demandId, DemandStatus status);

    List<Demand> demandsByDoctor(int doctorId);

    List<Demand> demandsByPacient(int pacientId);

    public Demand save(Demand demand);
}

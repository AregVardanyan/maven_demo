package com.example.maven_demo.manager;

import com.example.maven_demo.models.Doctor;
import com.example.maven_demo.models.TimeInterval;

import java.util.ArrayList;
import java.util.List;

public interface DoctorManager {

    boolean existByEmail(String email);

    Doctor save(Doctor doctor);

    Doctor getByEmailAndPassword(String email,
                                 String password);

    Doctor getById(int id);

    List<TimeInterval> timeIntervalsByDoctor(int doctorId);

    public void setRestWorkTime(TimeInterval timeInterval);

    public void endWorkDay();

    public void clearDemands();

    public void clearWorkTimeIntervals();

    public ArrayList<Doctor> getAllDoctors();
}

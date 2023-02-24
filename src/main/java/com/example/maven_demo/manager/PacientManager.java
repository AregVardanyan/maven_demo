package com.example.maven_demo.manager;

import com.example.maven_demo.models.Doctor;
import com.example.maven_demo.models.Pacient;

public interface PacientManager {
    public Pacient save(Pacient pacient);

    boolean existByEmail(String email);


    Pacient getByEmailAndPassword(String email,
                                 String password);

    Pacient getById(int id);
}

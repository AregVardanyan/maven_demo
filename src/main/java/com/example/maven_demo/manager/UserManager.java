package com.example.maven_demo.manager;

import com.example.maven_demo.models.Doctor;

public interface UserManager {

    boolean existByEmail(String email);

    Doctor save(Doctor user);

    Doctor getByEmailAndPassword(String email,
                                 String password);

    Doctor getById(int id);

    void update(Doctor user);
}

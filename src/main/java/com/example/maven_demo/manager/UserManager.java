package com.example.maven_demo.manager;

import com.example.maven_demo.models.User;

public interface UserManager {

    boolean existByEmail(String email);

    User save(User user);

    User getByEmailAndPassword(String email,
                               String password);

    User getById(int id);

    void update(User user);
}

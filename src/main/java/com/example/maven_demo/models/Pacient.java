package com.example.maven_demo.models;

import com.example.maven_demo.models.enums.Gender;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class Pacient extends Base{

    private String name;
    private String surname;

    private String email;
    private String password;

    private Gender gender;
    private int age;

}


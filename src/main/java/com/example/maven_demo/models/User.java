package com.example.maven_demo.models;


import com.example.maven_demo.models.enums.Gender;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class User extends Base {


    private String name;
    private String surname;

    private String email;
    private String password;
    private Integer age;
    private Gender gender;



    public boolean isTeenage(){
        return this.age<18;
    }

}
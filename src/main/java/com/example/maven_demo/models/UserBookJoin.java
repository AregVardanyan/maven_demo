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
public class UserBookJoin extends Base {


    private String name;
    private String surname;

    private String bookName;


}

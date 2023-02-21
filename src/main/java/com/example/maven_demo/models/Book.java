package com.example.maven_demo.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class Book extends Base{

    private String name;
    private Date   createdAt;
    private User   author;
    private int    authorId;

}


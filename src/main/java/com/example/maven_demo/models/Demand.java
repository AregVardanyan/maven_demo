package com.example.maven_demo.models;

import com.example.maven_demo.models.enums.DemandStatus;
import com.example.maven_demo.models.enums.WorkStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Time;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class Demand extends Base{

    private String message;

    private Time start;
    private Time end;

    private int doctorId;
    private int pacientId;

    private DemandStatus demandStatus;
}

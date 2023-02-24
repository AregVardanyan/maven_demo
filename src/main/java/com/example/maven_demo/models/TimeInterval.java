package com.example.maven_demo.models;

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
public class TimeInterval extends Base{
    private Time start;
    private Time end;

    private int doctorId;

    private WorkStatus workStatus;

}

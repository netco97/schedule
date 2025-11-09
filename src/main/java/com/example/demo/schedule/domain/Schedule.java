package com.example.demo.schedule.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    private Long id;  // BIGINT

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate workDate;  // DATE

    @DateTimeFormat(pattern="HH:mm")
    private LocalTime startTime; // TIME

    @DateTimeFormat(pattern="HH:mm")
    private LocalTime endTime;   // TIME
}

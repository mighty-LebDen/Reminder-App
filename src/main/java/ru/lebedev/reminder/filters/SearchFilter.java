package ru.lebedev.reminder.filters;


import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;

public record SearchFilter(

        String title,

        String description,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        @DateTimeFormat(pattern = "HH:mm:ss")
        LocalTime time
) {}

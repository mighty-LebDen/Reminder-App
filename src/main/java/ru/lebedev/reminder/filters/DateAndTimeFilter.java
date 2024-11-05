package ru.lebedev.reminder.filters;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;

public record DateAndTimeFilter(
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate dateBefore,
        @DateTimeFormat(pattern = "yyyy-MM-dd")

        LocalDate dateAfter,
        @DateTimeFormat(pattern = "HH:mm:ss")
        LocalTime timeBefore,

        @DateTimeFormat(pattern = "HH:mm:ss")
        LocalTime timeAfter


) {}

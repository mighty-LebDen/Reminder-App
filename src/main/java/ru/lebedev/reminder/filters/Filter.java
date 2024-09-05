package ru.lebedev.reminder.filters;

import java.time.LocalDate;
import java.time.LocalTime;

public record Filter(LocalDate date,
                     LocalTime time) {}

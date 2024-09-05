package ru.lebedev.remainder.filters;

import java.time.LocalDate;
import java.time.LocalTime;

public record Filter(LocalDate date,
                     LocalTime time) {}

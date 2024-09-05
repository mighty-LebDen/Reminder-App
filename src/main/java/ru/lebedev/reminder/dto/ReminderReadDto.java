package ru.lebedev.remainder.dto;

import java.time.LocalDate;


public record ReminderReadDto(Long id,
                              String title,
                              String description,
                              LocalDate remind) {}

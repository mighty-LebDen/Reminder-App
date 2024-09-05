package ru.lebedev.reminder.dto;

import java.time.LocalDate;


public record ReminderReadDto(Long id,
                              String title,
                              String description,
                              LocalDate remind) {}

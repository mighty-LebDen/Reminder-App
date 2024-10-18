package ru.lebedev.reminder.filters;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReminderSearchFilter(String title,
                                   String description,
                                   LocalDate date){
//                                   LocalTime time) {
}

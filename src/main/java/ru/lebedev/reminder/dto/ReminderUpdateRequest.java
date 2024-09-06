package ru.lebedev.reminder.dto;

public record ReminderUpdateRequest(Long id,
                                    ReminderCreateDto dto) {
}

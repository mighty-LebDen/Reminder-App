package ru.lebedev.reminder.dto;

public record UserReadDto(
        Long id,
        String username,
        String telegram,
        Long chatId
) {}

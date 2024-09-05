package ru.lebedev.reminder.dto;

public record UserCreateEditDto(String username,
                                String password,
                                String telegramUsername) {}

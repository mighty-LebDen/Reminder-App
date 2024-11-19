package ru.lebedev.reminder.dto;

import jakarta.validation.constraints.Email;
import lombok.Builder;

@Builder
public record UserCreateEditDto(
        @Email
        String username,
        String password,
        String telegram,
        Long chatId
) {}

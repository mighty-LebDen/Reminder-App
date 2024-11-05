package ru.lebedev.reminder.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

public record ReminderCreateEditDto(
        @NotNull
        @NotBlank
        String title,

        @NotNull
        @NotBlank
        String description,
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @Future
        LocalDateTime remind,
        @Positive
        Long userId
) {}

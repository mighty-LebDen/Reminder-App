package ru.lebedev.reminder.sender;

import lombok.Builder;

@Builder
public record Message(
        String email,
        String title,
        String description
) {}

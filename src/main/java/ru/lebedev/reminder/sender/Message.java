package ru.lebedev.reminder.sender;

import lombok.Builder;

@Builder
public record Message(
        String username,
        String title,
        String description
) {}

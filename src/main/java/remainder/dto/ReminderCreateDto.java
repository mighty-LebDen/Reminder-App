package remainder.dto;

import java.time.LocalDate;

public record ReminderCreateDto(String title,
                                String description,
                                LocalDate remind,
                                Long userId
                                ) {}

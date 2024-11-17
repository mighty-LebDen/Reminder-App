package ru.lebedev.reminder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.experimental.UtilityClass;
import ru.lebedev.reminder.database.entity.Reminder;
import ru.lebedev.reminder.database.entity.SentStatus;
import ru.lebedev.reminder.database.entity.User;
import ru.lebedev.reminder.dto.ReminderCreateEditDto;
import ru.lebedev.reminder.dto.ReminderReadDto;
import ru.lebedev.reminder.dto.UserReadDto;
import ru.lebedev.reminder.filters.DateTimeFilter;
import ru.lebedev.reminder.filters.SearchFilter;
import ru.lebedev.reminder.sender.Message;

@UtilityClass
public class TestData {

    public static final String BAD_USERNAME = "user-1@test.test";
    public static final String TELEGRAM_USERNAME = "user1";
    public static final String USERNAME = "user1@test.test";
    public static final String BAD_TELEGRAM_USERNAME = "user-1";
    public static final User TEST_USER = User.builder()
                                             .id(1L)
                                             .username(USERNAME)
                                             .telegram(TELEGRAM_USERNAME)
                                             .chatId(1001L)
                                             .password("password123")
                                             .build();
    public static final UserReadDto USER_READ_DTO = new UserReadDto(
            1L,
            USERNAME,
            TELEGRAM_USERNAME,
            1001L
    );
    public static final Reminder REMINDER = Reminder.builder()
                                                    .id(1L)
                                                    .title("Meeting")
                                                    .description("Team meeting in conference room")
                                                    .remind(LocalDateTime.of(
                                                            2024, 11, 11,
                                                            9, 0, 0
                                                    ))
                                                    .user(TEST_USER)
                                                    .telegramSentStatus(SentStatus.NOT_SENT)
                                                    .emailSentStatus(SentStatus.NOT_SENT)
                                                    .build();
    public static final Reminder REMINDER_WITHOUT_ID = Reminder.builder()
                                                               .title("Meeting")
                                                               .description(
                                                                       "Team meeting in conference room")
                                                               .remind(LocalDateTime.of(
                                                                       2024, 11, 11,
                                                                       9, 0, 0
                                                               ))
                                                               .user(TEST_USER)
                                                               .telegramSentStatus(SentStatus.NOT_SENT)
                                                               .emailSentStatus(SentStatus.NOT_SENT)
                                                               .build();

    public static final ReminderReadDto REMINDER_READ_DTO = new ReminderReadDto(
            1L,
            "Meeting",
            "Team meeting in conference room",
            LocalDateTime.of(
                    2024, 11, 11,
                    9, 0, 0
            ),
            USER_READ_DTO
    );
    public static final ReminderCreateEditDto REMINDER_CREATE_EDIT_DTO = new ReminderCreateEditDto(
            "Meeting",
            "Team meeting in conference room",
            LocalDateTime.now().plusDays(1L),
            USER_READ_DTO.id()
    );

    public static final ReminderCreateEditDto REMINDER_EDIT_DTO = new ReminderCreateEditDto(
            "test",
            "test",
            LocalDateTime.now().plusDays(1L),
            USER_READ_DTO.id()
    );

    public static final Message EXPECTED_EMAIL_MESSAGE =
            Message.builder()
                   .username(USER_READ_DTO.username())
                   .title(REMINDER_READ_DTO.title())
                   .description(REMINDER_READ_DTO.description())
                   .build();
    public static final Message EXPECTED_TELEGRAM_MESSAGE =
            Message.builder()
                   .username(USER_READ_DTO.chatId().toString())
                   .title(REMINDER_READ_DTO.title())
                   .description(REMINDER_READ_DTO.description())
                   .build();

    public static final SearchFilter TITLE_SEARCH_FILTER = new SearchFilter(
            "Meeting",
            null,
            null,
            null
    );
    public static final SearchFilter FULL_SEARCH_FILTER = new SearchFilter(
            "Meeting",
            "Team",
            LocalDate.of(2024, 11, 10),
            LocalTime.of(9, 0)
    );

    public static final SearchFilter DATE_SEARCH_FILTER = new SearchFilter(
            null,
            null,
            LocalDate.of(2024, 11, 10),
            null
    );

    public static final DateTimeFilter DATE_TIME_FILTER = new DateTimeFilter(
            LocalDate.of(2024, 11, 10),
            LocalDate.of(2024, 11, 12),
            LocalTime.of(10, 0, 0),
            LocalTime.of(17, 0, 0)
    );

}

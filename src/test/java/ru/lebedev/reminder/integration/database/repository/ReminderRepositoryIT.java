
package ru.lebedev.reminder.integration.database.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.lebedev.reminder.TestData.*;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.lebedev.reminder.database.entity.SentStatus;
import ru.lebedev.reminder.database.repository.ReminderRepository;
import ru.lebedev.reminder.integration.IntegrationTestBase;

@RequiredArgsConstructor
class ReminderRepositoryIT extends IntegrationTestBase {

    private final ReminderRepository reminderRepository;

    @Test
    void findReminderToEmailSent() {
        var remindersToSent = reminderRepository.findReminderToEmailSent(LocalDateTime.of(
                2024, 11, 12,
                0, 0, 0
        ));
        assertThat(remindersToSent)
                .hasSize(3);

    }

    @Test
    void findAll() {
        var allReminders = reminderRepository.findAll();
        assertThat(allReminders).hasSize(10);
    }

    @Test
    void findById() {
        var reminder = reminderRepository.findById(1L);
        assertThat(reminder).isPresent();
        reminder.ifPresent(reminder1 -> {
            assertThat(reminder1.getId()).isEqualTo(REMINDER.getId());
            assertThat(reminder1.getTitle()).isEqualTo(REMINDER.getTitle());
            assertThat(reminder1.getDescription()).isEqualTo(REMINDER.getDescription());
            assertThat(reminder1.getRemind()).isEqualTo(REMINDER.getRemind());
            assertThat(reminder1.getEmailSentStatus()).isEqualTo(REMINDER.getEmailSentStatus());
        });
    }

    @Test
    void findReminderToTelegramSent() {
        var remindersToSent = reminderRepository.findReminderToTelegramSent(LocalDateTime.of(
                2024, 11, 12,
                0, 0, 0
        ));
        assertThat(remindersToSent)
                .hasSize(3);

    }

    @Test
    void updateEmailSentStatus() {
        var reminder = reminderRepository.findById(REMINDER.getId()).orElse(null);
        assertThat(reminder).isNotNull();
        var statusBeforeUpdate = reminder.getEmailSentStatus();
        assertThat(statusBeforeUpdate).isSameAs(SentStatus.NOT_SENT);
        reminderRepository.updateEmailSentStatus(reminder.getId(), SentStatus.SENT);
        var reminderAfterUpdate = reminderRepository.findById(REMINDER.getId()).orElse(null);
        assertThat(reminderAfterUpdate).isNotNull();
        var statusAfterUpdate = reminderAfterUpdate.getEmailSentStatus();
        assertThat(statusAfterUpdate)
                .isSameAs(SentStatus.SENT)
                .isNotSameAs(statusBeforeUpdate);

    }

    @Test
    void updateTelegramSentStatus() {
        var reminder = reminderRepository.findById(REMINDER.getId()).orElse(null);
        assertThat(reminder).isNotNull();
        var statusBeforeUpdate = reminder.getTelegramSentStatus();
        assertThat(statusBeforeUpdate).isSameAs(SentStatus.NOT_SENT);
        reminderRepository.updateTelegramSentStatus(reminder.getId(), SentStatus.SENT);
        var reminderAfterUpdate = reminderRepository.findById(REMINDER.getId()).orElse(null);
        assertThat(reminderAfterUpdate).isNotNull();
        var statusAfterUpdate = reminderAfterUpdate.getTelegramSentStatus();
        assertThat(statusAfterUpdate)
                .isSameAs(SentStatus.SENT)
                .isNotSameAs(statusBeforeUpdate);

    }

    @Test
    void findAllBySearchFilter() {
        var reminders = reminderRepository.findAllBySearchFilter(TITLE_SEARCH_FILTER);
        assertThat(reminders).hasSize(1);
        var findReminder = reminders.get(0);
        assertThat(findReminder.getId()).isEqualTo(REMINDER.getId());
        assertThat(findReminder.getTitle()).isEqualTo(REMINDER.getTitle());
        assertThat(findReminder.getDescription()).isEqualTo(REMINDER.getDescription());
        assertThat(findReminder.getRemind()).isEqualTo(REMINDER.getRemind());

        reminders = reminderRepository.findAllBySearchFilter(FULL_SEARCH_FILTER);
        assertThat(reminders).hasSize(1);
        findReminder = reminders.get(0);
        assertThat(findReminder.getId()).isEqualTo(REMINDER.getId());
        assertThat(findReminder.getTitle()).isEqualTo(REMINDER.getTitle());
        assertThat(findReminder.getDescription()).isEqualTo(REMINDER.getDescription());
        assertThat(findReminder.getRemind()).isEqualTo(REMINDER.getRemind());

        reminders = reminderRepository.findAllBySearchFilter(DATE_SEARCH_FILTER);
        assertThat(reminders).hasSize(10);

    }

    @Test
    void findAllByDateTimeFilter() {
        var reminders = reminderRepository.findAllByDateTimeFilter(DATE_TIME_FILTER);
        assertThat(reminders).hasSize(3);
        var reminder = reminders.stream()
                                .filter(r -> r.getId().equals(1L))
                                .findFirst();
        assertThat(reminder).isPresent();
        reminder.ifPresent(r -> {
            assertThat(r.getId()).isEqualTo(REMINDER.getId());
            assertThat(r.getTitle()).isEqualTo(REMINDER.getTitle());
            assertThat(r.getDescription()).isEqualTo(REMINDER.getDescription());
            assertThat(r.getRemind()).isEqualTo(REMINDER.getRemind());
        });
    }

}






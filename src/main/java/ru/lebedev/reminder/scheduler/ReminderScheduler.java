package ru.lebedev.reminder.scheduler;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.lebedev.reminder.sender.EmailSenderService;
import ru.lebedev.reminder.service.impl.ReminderService;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReminderScheduler {

    private final EmailSenderService emailSenderService;
    private final ReminderService reminderService;

    @Scheduled(fixedDelayString = "${scheduling.interval}")
    public void checkReminders() throws MailException {
        LocalDateTime now = LocalDateTime.now();
        var remindsToSend = reminderService.findReminderByRemindBefore(now);
        remindsToSend.forEach(emailSenderService::sendSimpleEmail);
    }

}

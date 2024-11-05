package ru.lebedev.reminder.listner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.lebedev.reminder.service.impl.ReminderService;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailSentListener {

    private final ReminderService reminderService;

    @EventListener
    public void onEmailSent(EmailSentEvent event) {
        reminderService.updateStatus(event.getReminder().id(), event.getStatus());
        log.info("message sent status, {}", event.getStatus());
    }

}


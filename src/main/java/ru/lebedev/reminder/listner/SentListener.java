package ru.lebedev.reminder.listner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.lebedev.reminder.service.impl.ReminderService;

@Component
@RequiredArgsConstructor
@Slf4j
public class SentListener {

    private final ReminderService reminderService;

    @EventListener(condition = "#p0.type.name() == 'EMAIL'")
    public void onEmailSent(SentEvent event) {
        reminderService.updateEmailSentStatus(event.getReminderId(), event.getStatus());
        log.info("Email message sent status, {}", event.getStatus());
    }

    @EventListener(condition = "#p0.type.name() == 'TELEGRAM'")
    public void onTelegramSent(SentEvent event) {
        reminderService.updateTelegramSentStatus(event.getReminderId(), event.getStatus());
        log.info("Telegram message sent status, {}", event.getStatus());
    }

}


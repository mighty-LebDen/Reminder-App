package ru.lebedev.reminder.scheduler;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.lebedev.reminder.mapper.MessageMapper;
import ru.lebedev.reminder.sender.EmailSenderService;
import ru.lebedev.reminder.service.impl.ReminderService;
import ru.lebedev.reminder.telegram.TelegramBot;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReminderScheduler {

    private final EmailSenderService emailSenderService;
    private final TelegramBot telegramBot;
    private final ReminderService reminderService;
    private final MessageMapper messageMapper = Mappers.getMapper(MessageMapper.class);

    @Scheduled(fixedDelayString = "${scheduling.interval}")
    public void sendReminderToEmail() {
        LocalDateTime now = LocalDateTime.now();
        var remindsToEmailSend = reminderService.findReminderToEmailSent(now);
        remindsToEmailSend.forEach(reminder -> emailSenderService.sendSimpleEmail(
                messageMapper.toEmailMessage(reminder),
                reminder.id()
        ));
    }

    @Scheduled(fixedDelayString = "${scheduling.interval}")
    public void sendReminderToTelegram() {
        LocalDateTime now = LocalDateTime.now();
        var reminderToTelegramSent = reminderService.findReminderToTelegramSent(now);
        reminderToTelegramSent
                .forEach(reminder -> telegramBot.sendMessage(
                        messageMapper.toTelegramMessage(reminder),
                        reminder.id()
                ));
    }

}

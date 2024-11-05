package ru.lebedev.reminder.sender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.lebedev.reminder.database.entity.SentStatus;
import ru.lebedev.reminder.dto.ReminderReadDto;
import ru.lebedev.reminder.listner.EmailSentEvent;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderService {

    private final JavaMailSender mailSender;

    private final ApplicationEventPublisher eventPublisher;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendSimpleEmail(ReminderReadDto reminder) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        var messageToSend = createMessage(reminder);
        message.setTo(messageToSend.email());
        message.setSubject(messageToSend.title());
        message.setText(messageToSend.description());
        message.setFrom(sender);
        log.info(sender);
        try {
            mailSender.send(message);
            eventPublisher.publishEvent(new EmailSentEvent(this, reminder, SentStatus.SENT));
        } catch (MailException exception) {
            eventPublisher.publishEvent(new EmailSentEvent(this, reminder, SentStatus.FAILED));
            log.error("smth wrong in sending mail");
        }

    }

    private Message createMessage(ReminderReadDto reminder) {
        return Message.builder()
                      .title(reminder.title())
                      .description(reminder.description())
                      .email(reminder.userReadDto().username())
                      .build();
    }

}


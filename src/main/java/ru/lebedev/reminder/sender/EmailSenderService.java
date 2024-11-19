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
import ru.lebedev.reminder.listner.SentEvent;
import ru.lebedev.reminder.listner.SentType;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderService {

    private final JavaMailSender mailSender;

    private final ApplicationEventPublisher eventPublisher;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendSimpleEmail(Message messageToSend, Long reminderId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(messageToSend.username());
        message.setSubject(messageToSend.title());
        message.setText(messageToSend.description());
        message.setFrom(sender);
        log.info(sender);
        try {
            mailSender.send(message);
            eventPublisher.publishEvent(new SentEvent(
                    this,
                    reminderId,
                    SentStatus.SENT,
                    SentType.EMAIL
            ));
        } catch (MailException exception) {
            eventPublisher.publishEvent(new SentEvent(
                    this,
                    reminderId,
                    SentStatus.FAILED,
                    SentType.EMAIL
            ));
            log.error("smth wrong in sending mail");
        }

    }

}


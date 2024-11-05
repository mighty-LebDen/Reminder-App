package ru.lebedev.reminder.listner;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import ru.lebedev.reminder.database.entity.SentStatus;
import ru.lebedev.reminder.dto.ReminderReadDto;

@Getter
public class EmailSentEvent extends ApplicationEvent {

    private final ReminderReadDto reminder;
    private final SentStatus status;

    public EmailSentEvent(Object source, ReminderReadDto reminder, SentStatus status) {
        super(source);
        this.reminder = reminder;
        this.status = status;
    }

}

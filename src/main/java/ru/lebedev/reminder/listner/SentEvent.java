package ru.lebedev.reminder.listner;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import ru.lebedev.reminder.database.entity.SentStatus;

@Getter
public class SentEvent extends ApplicationEvent {

    private final Long reminderId;
    private final SentStatus status;
    private final SentType type;

    public SentEvent(Object source, Long reminderId, SentStatus status, SentType type) {
        super(source);
        this.reminderId = reminderId;
        this.status = status;
        this.type = type;
    }

}

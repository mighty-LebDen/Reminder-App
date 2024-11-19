package ru.lebedev.reminder.unit.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.lebedev.reminder.TestData.*;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.lebedev.reminder.dto.ReminderReadDto;
import ru.lebedev.reminder.mapper.MessageMapper;

class MessageMapperTest {

    MessageMapper messageMapper = Mappers.getMapper(MessageMapper.class);
    ReminderReadDto reminderReadDto = REMINDER_READ_DTO;

    @Test
    void toTelegramMessage() {
        var resultMessage = messageMapper.toTelegramMessage(reminderReadDto);
        assertThat(resultMessage.username()).isEqualTo(EXPECTED_TELEGRAM_MESSAGE.username());
        assertThat(resultMessage.title()).isEqualTo(EXPECTED_TELEGRAM_MESSAGE.title());
        assertThat(resultMessage.description()).isEqualTo(EXPECTED_TELEGRAM_MESSAGE.description());
    }

    @Test
    void toEmailMessage() {
        var resultMessage = messageMapper.toEmailMessage(reminderReadDto);
        assertThat(resultMessage.username()).isEqualTo(EXPECTED_EMAIL_MESSAGE.username());
        assertThat(resultMessage.title()).isEqualTo(EXPECTED_EMAIL_MESSAGE.title());
        assertThat(resultMessage.description()).isEqualTo(EXPECTED_EMAIL_MESSAGE.description());
    }

}

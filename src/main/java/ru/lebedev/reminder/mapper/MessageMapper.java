package ru.lebedev.reminder.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.lebedev.reminder.dto.ReminderReadDto;
import ru.lebedev.reminder.sender.Message;

@Mapper
public interface MessageMapper {

    @Mapping(target = "username", source = "userReadDto.chatId")
    Message toTelegramMessage(ReminderReadDto dto);

    @Mapping(target = "username", source = "userReadDto.username")
    Message toEmailMessage(ReminderReadDto dto);

}

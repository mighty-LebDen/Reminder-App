package ru.lebedev.reminder.telegram;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.lebedev.reminder.database.entity.SentStatus;
import ru.lebedev.reminder.listner.SentEvent;
import ru.lebedev.reminder.listner.SentType;
import ru.lebedev.reminder.sender.Message;
import ru.lebedev.reminder.service.UserService;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final String botUsername;
    private final UserService userServiceImpl;

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    public TelegramBot(
            @Value("${telegram.bot.token}")
            String botToken,
            @Value("${telegram.bot.username}") String botUsername,
            UserService userServiceImpl,
            ApplicationEventPublisher eventPublisher) {
        super(botToken);
        this.botUsername = botUsername;
        this.userServiceImpl = userServiceImpl;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            var userName = update.getMessage().getFrom().getUserName();
            log.info(chatId.toString());
            log.info(update.getMessage().getFrom().toString());
            if (messageText.equals("/start")) {
                var mayBeUser = Optional.ofNullable(userServiceImpl.findByTelegram(userName));
                mayBeUser.ifPresent(userReadDto -> {
                    userServiceImpl.addChatId(userReadDto.id(), chatId);
                    var welcomeMessage = createMessage(
                            BotAnswer.WELCOME,
                            BotAnswer.USER_REGISTRATION,
                            chatId.toString()
                    );
                    sendDefaultMessage(welcomeMessage);
                });
                if (mayBeUser.isEmpty()) {
                    var errorMessage = createMessage(
                            "",
                            BotAnswer.USER_NOT_FOUND,
                            chatId.toString()
                    );
                    sendDefaultMessage(errorMessage);
                }
            } else {
                var unknownCommandMessage = createMessage(
                        BotAnswer.ERROR,
                        BotAnswer.UNKNOWN_COMMAND,
                        chatId.toString()
                );
                sendDefaultMessage(unknownCommandMessage);
            }
        }
    }

    public void sendMessage(Message messageToSend, Long reminderId) {
        SendMessage message = new SendMessage();
        message.setChatId(messageToSend.username());
        message.setText(messageToSend.title() + "\n" + messageToSend.description());
        try {
            execute(message);
            eventPublisher.publishEvent(new SentEvent(
                    this,
                    reminderId,
                    SentStatus.SENT,
                    SentType.TELEGRAM
            ));
        } catch (TelegramApiException ex) {
            eventPublisher.publishEvent(new SentEvent(
                    this,
                    reminderId,
                    SentStatus.FAILED,
                    SentType.TELEGRAM
            ));
            log.error("smth wrong in sending telegram message, {}", ex.getMessage());
        }
    }

    public void sendDefaultMessage(Message messageToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(messageToSend.username());
        message.setText(messageToSend.title() + "\n" + messageToSend.description());
        try {
            execute(message);
        } catch (TelegramApiException ex) {
            log.error("smth wrong in sending default telegram message, ex: {}", ex.getMessage());
        }

    }

    private Message createMessage(String title, String description, String username) {
        return Message.builder()
                      .title(title)
                      .description(description)
                      .username(username)
                      .build();
    }

}
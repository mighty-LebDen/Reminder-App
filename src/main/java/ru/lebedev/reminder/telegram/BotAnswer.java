package ru.lebedev.reminder.telegram;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BotAnswer {

    public static final String USER_REGISTRATION = "Не упустите ваши напоминания!";
    public static final String USER_NOT_FOUND = "Пользователь с таким username: %s не найден.";
    public static final String WELCOME = "Добро пожаловать!";
    public static final String ERROR = "Извините возникала ошибка!";
    public static final String UNKNOWN_COMMAND = "Неизвестная команда";

}

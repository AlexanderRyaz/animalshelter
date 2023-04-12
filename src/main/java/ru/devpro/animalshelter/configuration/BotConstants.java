package ru.devpro.animalshelter.configuration;

import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;

public class BotConstants {
    public static final String INITIAL_MSG =            "/start";
    public static final String DOGS_MSG =               "/dog";
    public static final String PROBLEM_OCCURS_MSG =     "Я Вас не понял.";
    public static final String GREETING_MSG =           "Здравствуйте!\n" +
                                                        "Я могу Вас проинформировать о нашем приюте для животных!\n" +
                                                        "Наберите /dog и я расскажу Вам о наших замечательных песиках.";

    public static final String DOGS_INFO_MSG =          "В нашем приюте есть и пародистые собаки и дворняги ;)";
    public static final String ERROR_MSG =              "Что-то пошло не так...";

    public static final String GO_BACK_CMD =            "В начало.";
    public static final String DOGS_CMD =               "Взять собаку";
    public static final String SHELTER_INFO_CMD =       "Информация о приюте";
    public static final String CATS_CMD =               "Взять кошку";

    public static final ReplyKeyboardMarkup WELCOME_KEYBOARD = new ReplyKeyboardMarkup(
            new String[]{DOGS_CMD, SHELTER_INFO_CMD},
            new String[]{CATS_CMD});

    public static final ReplyKeyboardMarkup CONSULTING_KEYBOARD =new ReplyKeyboardMarkup(
            new KeyboardButton(GO_BACK_CMD)
    );
}

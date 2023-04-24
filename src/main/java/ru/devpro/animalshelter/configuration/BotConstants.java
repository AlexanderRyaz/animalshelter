package ru.devpro.animalshelter.configuration;

import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;

public class BotConstants {
    public static final String INITIAL_MSG = "/start";
    public static final String PROBLEM_OCCURS_MSG = "Я Вас не понял.";
    public static final String GREETING_MSG =           "Здравствуйте!\n" +
                                                        "Я могу Вас проинформировать о нашем приюте для животных!\n" +
                                                        "Выберете в меню что вас интересует.";

    public static final String CATS_INFO_MSG = "У нас очень много кошек, самых разных";
    public static final String DOGS_INFO_MSG = "В нашем приюте есть и пародистые собаки и дворняги ;)";
    public static final String ERROR_MSG = "Что-то пошло не так...";
    public static final String VOLUNTEER_CALL_MSG = "Волонтер скоро с вами свяжется, подождите пожалуйста!";
    public static final String SHELTER_CONTACTS_MSG = "Здесь указан адрес приюта.";
    public static final String SHELTER_CONTACTS_CMD = "Здесь контакты приюта";
    public static final String USER_CONTACTS_MSG = "Введите телефон и имя в формате: \"+7-999-123-45-67 Имя\".";
    public static final String REJECT_REASONS_MSG = "отклонено";
    public static final String SEND_REPORT_MSG = "В отчете вы должны указать: 1) рацион животного, 2) самочувствие животного, 3) поведение животного.\n" +
            "Пример: 1) ест корм с курицей 2) чувствует себя замечательно 3) перестал грызть кресло";
    public static final String SHELTER_INFO_MSG = """
            Мы частный приют для животных "добрые руки".
            Существуем за счет пожертвований и помощи волонтеров.
            Мы всегда будем рады если вы захотите взять наших милых питомцев!
            """;

    public static final String USER_CONTACTS_CMD = "Оставить контакты";
    public static final String GO_BACK_CMD =            "В начало.";
    public static final String DOGS_CMD =               "Взять собаку";
    public static final String CATS_CMD =               "Взять кошку";
    public static final String SHELTER_INFO_CMD =       "Информация о приюте";
    public static final String SEND_REPORT_CMD = "Отправить отчет о питомце";
    public static final String VOLUNTEER_CALL_CMD = "Позвать волонтера";
    public static final String REJECT_REASONS_CMD = "Вам могут отказать по причине:";


    public static final ReplyKeyboardMarkup WELCOME_KEYBOARD = new ReplyKeyboardMarkup(
            new String[]{DOGS_CMD, CATS_CMD},
            new String[]{SEND_REPORT_CMD},
            new String[]{VOLUNTEER_CALL_CMD, SHELTER_INFO_CMD});

    public static final ReplyKeyboardMarkup CONSULTING_KEYBOARD = new ReplyKeyboardMarkup(
            new String[]{USER_CONTACTS_CMD, VOLUNTEER_CALL_CMD},
            new String[]{GO_BACK_CMD});

    public static final ReplyKeyboardMarkup SHELTER_KEYBOARD = new ReplyKeyboardMarkup(
            new String[]{USER_CONTACTS_CMD, GO_BACK_CMD});
}

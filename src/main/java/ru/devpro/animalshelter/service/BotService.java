package ru.devpro.animalshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import ru.devpro.animalshelter.core.dialog.DialogInterface;
import ru.devpro.animalshelter.core.dto.DialogDto;
import ru.devpro.animalshelter.core.exception.IntervalDateIncorrectException;

import java.util.Map;

@Service
public class BotService {
    private final Logger logger = LoggerFactory.getLogger(BotService.class);
    private final TelegramBot telegramBot;
    private final Map<String, DialogInterface> supportedDialogs;

    public BotService(TelegramBot bot, Map<String, DialogInterface> supportedDialogs) {
        this.telegramBot = bot;
        this.supportedDialogs = supportedDialogs;
    }


    /**
     *
     * Exception try-catch {@link IntervalDateIncorrectException}
     * if {@link IntervalDateIncorrectException}
     * a message comes out {@link ru.devpro.animalshelter.configuration.BotConstants}
     * {@code  ERROR_MSG} - "Что-то пошло не так..."
     * @param update
     *
     */
    public void process(Update update) {
        if (update == null || update.message().document() != null) {
            logger.debug("Получен документ или null update");
            return;
        }
        for (DialogInterface dialog : supportedDialogs.values()) {
            Message incomeMessage = update.message();
            if (update.message() == null) {
                logger.debug("СрфеШв = {}; null messege", incomeMessage.chat().id());
                return;
            }

            DialogDto dto = new DialogDto(incomeMessage.chat().id(), update.message().from().firstName(), incomeMessage.text());
            if (dialog.isSupport(dto) && dialog.process(dto)) {
                sendResponse(dto.chatId(), dialog.getMessage(dto.chatId()), dialog.getKeyboard());
                return;
            } else {
                logger.debug("ChatID={}; Пустой текс", incomeMessage.chat().id());
            }
        }
    }


    public void sendResponse(Long chatId, String message, ReplyKeyboardMarkup keyboard) {
        SendMessage preparedMessage = new SendMessage(chatId, message);
        if (keyboard != null) preparedMessage.replyMarkup(keyboard);
        telegramBot.execute(preparedMessage);
    }
}

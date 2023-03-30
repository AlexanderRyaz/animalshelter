package ru.devpro.animalshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.devpro.animalshelter.core.dialog.DialogInterface;
import ru.devpro.animalshelter.core.dto.DialogDto;
import ru.devpro.animalshelter.core.exception.IntervalDateIncorrectException;
import ru.devpro.animalshelter.core.repository.ShelterRepository;

import java.util.Map;

@Service
public class BotService {
    private final TelegramBot telegramBot;
    private final Map<String, DialogInterface> supportedDialogs;

    @Autowired
    private ShelterRepository shelterRepository;

    public BotService(TelegramBot bot, Map<String, DialogInterface> supportedDialogs) {
        this.telegramBot = bot;
        this.supportedDialogs = supportedDialogs;
    }

    /**
     *
     * Exception try-catch {@link IntervalDateIncorrectException}
     * if {@link IntervalDateIncorrectException} a message comes out {@link ru.devpro.animalshelter.configuration.BotConstants} {@code  ERROR_MSG} - "Что-то пошло не так..."
     * @param update
     *
     */
    public void process(Update update) {
        try {
            for (DialogInterface dialog : supportedDialogs.values()) {
                if (update.message() == null || update.message().text() == null) {
                    return;
                }
                Message incomeMessage = update.message();
                DialogDto dto = new DialogDto(incomeMessage.chat().id(), incomeMessage.text());
                if (dialog.isSupport(dto) && dialog.process(dto)) {
                    sendResponse(dto.chatId(), dialog.getMessage());
                    return;
                }
            }
        } catch (IntervalDateIncorrectException exception) {
            sendResponse(update.message().chat().id(), exception.getMessage());
        }
    }

    public void sendResponse(Long chatId, String message) {
        SendMessage preparedMessage = new SendMessage(chatId, message);
        telegramBot.execute(preparedMessage);
    }
}

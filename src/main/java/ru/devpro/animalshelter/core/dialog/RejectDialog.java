package ru.devpro.animalshelter.core.dialog;

import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import org.springframework.stereotype.Component;
import ru.devpro.animalshelter.core.dto.DialogDto;

import static ru.devpro.animalshelter.configuration.BotConstants.*;

@Component
public class RejectDialog implements DialogInterface{

    @Override
    public boolean isSupport(DialogDto dialogDto) {
        return dialogDto.message().equals(REJECT_REASONS_CMD);
    }

    @Override
    public boolean process(DialogDto dialogDto) {
        return true;
    }

    /**
     * Диалог "Отказа"
     *
     * @return Сообщение пользователю, как String
     */
    @Override
    public String getMessage(Long chatId) {
        return REJECT_REASONS_MSG;
    }

    @Override
    public ReplyKeyboardMarkup getKeyboard() {
        return CONSULTING_KEYBOARD;
    }
}

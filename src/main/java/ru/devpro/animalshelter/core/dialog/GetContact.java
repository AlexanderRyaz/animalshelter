package ru.devpro.animalshelter.core.dialog;

import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import org.springframework.stereotype.Component;
import ru.devpro.animalshelter.core.dto.DialogDto;

import static ru.devpro.animalshelter.configuration.BotConstants.*;

@Component
public class GetContact implements DialogInterface{

    @Override
    public boolean isSupport(DialogDto dialogDto) {
        return dialogDto.message().equals(USER_CONTACTS_CMD);
    }

    @Override
    public boolean process(DialogDto dialogDto) {
        return true;
    }

    @Override
    public String getMessage(Long chatId) {
        return USER_CONTACTS_MSG;
    }

    @Override
    public ReplyKeyboardMarkup getKeyboard() {
        return SHELTER_KEYBOARD;
    }
}

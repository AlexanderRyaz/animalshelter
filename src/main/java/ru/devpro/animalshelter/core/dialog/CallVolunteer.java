package ru.devpro.animalshelter.core.dialog;

import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import org.springframework.stereotype.Component;
import ru.devpro.animalshelter.core.dto.DialogDto;

import static ru.devpro.animalshelter.configuration.BotConstants.*;

@Component
public class CallVolunteer implements DialogInterface{

    @Override
    public boolean isSupport(DialogDto dialogDto) {
        return dialogDto.message().equals(VOLUNTEER_CALL_CMD);
    }

    @Override
    public boolean process(DialogDto dialogDto) {
        return true;
    }

    @Override
    public String getMessage(Long chatId) {
        return VOLUNTEER_CALL_MSG;
    }

    @Override
    public ReplyKeyboardMarkup getKeyboard() {
        return WELCOME_KEYBOARD;
    }
}


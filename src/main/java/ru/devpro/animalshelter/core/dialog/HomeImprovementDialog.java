package ru.devpro.animalshelter.core.dialog;

import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import org.springframework.stereotype.Component;
import ru.devpro.animalshelter.core.dto.DialogDto;

import static ru.devpro.animalshelter.configuration.BotConstants.*;

@Component
public class HomeImprovementDialog implements DialogInterface{

    @Override
    public boolean isSupport(DialogDto dialogDto) {
        return dialogDto.message().equals(HOME_IMPROVEMENT_CMD);
    }

    @Override
    public boolean process(DialogDto dialogDto) {
        return true;
    }

    @Override
    public String getMessage(Long chatId) {
        return HOME_IMPROVEMENT_MSG;
    }

    @Override
    public ReplyKeyboardMarkup getKeyboard() {
        return CONSULTING_KEYBOARD;
    }
}

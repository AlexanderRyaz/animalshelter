package ru.devpro.animalshelter.core.dialog;

import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import org.springframework.stereotype.Component;
import ru.devpro.animalshelter.core.dto.DialogDto;
import ru.devpro.animalshelter.service.UserService;

import static ru.devpro.animalshelter.configuration.BotConstants.*;

@Component
public class StartDialog implements DialogInterface{

    private final UserService userService;
    private DialogDto dialog;

    public StartDialog(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isSupport(DialogDto dialogDto) {
        return dialogDto.message().equals(INITIAL_MSG) || dialogDto.message().equals(GO_BACK_CMD);
    }

    @Override
    public boolean process(DialogDto dialogDto) {
        dialog = dialogDto;
        return true;
    }

    @Override
    public String getMessage() {
        //сделать распознавание на нового пользователя через id диалога?
            return GREETING_MSG;

    }

    @Override
    public ReplyKeyboardMarkup getKeyboard() {
        return WELCOME_KEYBOARD;
    }
}

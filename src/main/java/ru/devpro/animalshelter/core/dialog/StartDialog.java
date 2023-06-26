package ru.devpro.animalshelter.core.dialog;

import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import org.springframework.stereotype.Component;
import ru.devpro.animalshelter.core.dto.DialogDto;
import ru.devpro.animalshelter.core.repository.UserRepository;
import ru.devpro.animalshelter.service.UserService;

import static ru.devpro.animalshelter.configuration.BotConstants.*;

@Component
public class StartDialog implements DialogInterface{

    private final UserRepository userRepository;
    private final UserService userService;
    private DialogDto dialog;

    public StartDialog(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
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
    public String getMessage(Long chatId) {
        if (userRepository.findByChatId(chatId) == null) {

            userService.createUser(dialog);
            return GREETING_MSG; //приветственное сообщение новому пользователю
        } else {
            return "Привет! Чем я вам могу помочь?"; // сообщение пользователю который уже есть в бд
        }

    }

    @Override
    public ReplyKeyboardMarkup getKeyboard() {
        return WELCOME_KEYBOARD;
    }
}

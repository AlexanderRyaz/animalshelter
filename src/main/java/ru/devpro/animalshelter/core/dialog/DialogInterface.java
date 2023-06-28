package ru.devpro.animalshelter.core.dialog;


import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import ru.devpro.animalshelter.core.dto.DialogDto;

public interface DialogInterface {
    boolean isSupport(DialogDto dialogDto);
    boolean process(DialogDto dialogDto);
    String getMessage(Long chatId);
    ReplyKeyboardMarkup getKeyboard();
}

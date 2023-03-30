package ru.devpro.animalshelter.core.dialog;

import org.springframework.stereotype.Component;
import ru.devpro.animalshelter.core.dto.DialogDto;

import static ru.devpro.animalshelter.configuration.BotConstants.GREETING_MSG;
import static ru.devpro.animalshelter.configuration.BotConstants.INITIAL_MSG;

@Component
public class StartDialog implements DialogInterface{
    @Override
    public boolean isSupport(DialogDto dialogDto) {
        return dialogDto.message().equals(INITIAL_MSG);
    }

    @Override
    public boolean process(DialogDto dialogDto) {
        return true;
    }

    @Override
    public String getMessage() {
        return GREETING_MSG;
    }
}

package ru.devpro.animalshelter.core.dialog;

import org.springframework.stereotype.Component;
import ru.devpro.animalshelter.core.dto.DialogDto;

import static ru.devpro.animalshelter.configuration.BotConstants.DOGS_INFO_MSG;
import static ru.devpro.animalshelter.configuration.BotConstants.DOGS_MSG;

@Component
public class DogsDialog implements DialogInterface{

    @Override
    public boolean isSupport(DialogDto dialogDto) {
        return dialogDto.message().equals(DOGS_MSG);
    }

    @Override
    public boolean process(DialogDto dialogDto) {
        return true;
    }

    @Override
    public String getMessage() {
        return DOGS_INFO_MSG;
    }
}

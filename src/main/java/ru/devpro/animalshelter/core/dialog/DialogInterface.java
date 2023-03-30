package ru.devpro.animalshelter.core.dialog;


import ru.devpro.animalshelter.core.dto.DialogDto;

public interface DialogInterface {
    boolean isSupport(DialogDto dialogDto);
    boolean process(DialogDto dialogDto);
    String getMessage();
}

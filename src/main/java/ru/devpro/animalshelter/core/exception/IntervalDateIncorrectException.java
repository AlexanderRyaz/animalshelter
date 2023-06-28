package ru.devpro.animalshelter.core.exception;

import static ru.devpro.animalshelter.configuration.BotConstants.ERROR_MSG;

public class IntervalDateIncorrectException extends RuntimeException{
    public IntervalDateIncorrectException() {
        super(ERROR_MSG);
    }
}

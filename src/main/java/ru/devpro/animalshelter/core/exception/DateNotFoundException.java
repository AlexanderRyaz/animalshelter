package ru.devpro.animalshelter.core.exception;

public class DateNotFoundException extends RuntimeException{
    private final long id;
    public DateNotFoundException(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }
}

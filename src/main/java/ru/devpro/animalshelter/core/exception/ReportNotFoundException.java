package ru.devpro.animalshelter.core.exception;

public class ReportNotFoundException extends RuntimeException{
    public ReportNotFoundException() {
        super("Отчет не найден");
    }
}

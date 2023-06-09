package ru.devpro.animalshelter.core.dto;

import java.util.Objects;

public record DialogDto(Long chatId, String name, String message) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DialogDto dialogDto)) return false;
        return Objects.equals(chatId, dialogDto.chatId) && Objects.equals(message, dialogDto.message);
    }

}

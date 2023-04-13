package ru.devpro.animalshelter.core.record;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRecord {
    private Long id;
    private String userName;
    private Long chatId;
    private Boolean isVolunteer;

    public UserRecord(String userName, Long chatId, Boolean isVolunteer) {
        this.userName = userName;
        this.chatId = chatId;
        this.isVolunteer = isVolunteer;
    }
}

package ru.devpro.animalshelter.core.model;

import lombok.Getter;
import lombok.Setter;
import ru.devpro.animalshelter.core.entity.PhotoEntity;
import ru.devpro.animalshelter.core.entity.UserEntity;

import java.time.LocalDateTime;

@Getter
@Setter
public class ContainReport {
    private LocalDateTime dateTime;
    private String animalName;
    private String ration;
    private String health;
    private String behavior;
    private PhotoEntity photoEntity;
    private UserEntity userEntity;
}

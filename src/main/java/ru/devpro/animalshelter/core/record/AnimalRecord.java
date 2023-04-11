package ru.devpro.animalshelter.core.record;

import lombok.Getter;
import lombok.Setter;
import ru.devpro.animalshelter.core.model.AnimalType;

@Getter
@Setter
public class AnimalRecord {
    private long animalId;
    private AnimalType animalType;
    private String animalName;
}

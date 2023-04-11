package ru.devpro.animalshelter.core.record;

import org.springframework.stereotype.Component;
import ru.devpro.animalshelter.core.entity.AnimalEntity;

@Component
public class MapperRecord {
    public AnimalEntity toEntity(AnimalRecord animalRecord) {
        AnimalEntity animal = new AnimalEntity();
        animal.setAnimalType(animalRecord.getAnimalType());
        animal.setAnimalName(animalRecord.getAnimalName());
        return animal;
    }

    public AnimalRecord toRecord(AnimalEntity animal) {
        AnimalRecord animalRecord = new AnimalRecord();
        animalRecord.setAnimalId(animal.getId());
        animalRecord.setAnimalType(animal.getAnimalType());
        animalRecord.setAnimalName(animal.getAnimalName());
        return null;
    }
}

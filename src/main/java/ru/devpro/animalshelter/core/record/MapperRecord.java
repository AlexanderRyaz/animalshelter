package ru.devpro.animalshelter.core.record;

import org.springframework.stereotype.Component;
import ru.devpro.animalshelter.core.entity.AnimalEntity;
import ru.devpro.animalshelter.core.entity.UserEntity;

@Component
public class MapperRecord {

    public AnimalEntity toEntity(AnimalRecord animalRecord) {
        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setAnimalType(animalRecord.getAnimalType());
        animalEntity.setAnimalName(animalRecord.getAnimalName());
        return animalEntity;
    }

    public AnimalRecord toRecord(AnimalEntity animal) {
        AnimalRecord animalRecord = new AnimalRecord();
        animalRecord.setAnimalId(animal.getId());
        animalRecord.setAnimalType(animal.getAnimalType());
        animalRecord.setAnimalName(animal.getAnimalName());
        return animalRecord;
    }

    public UserEntity toEntity(UserRecord userRecord) {
        return new UserEntity(userRecord.getUserName(), userRecord.getChatId(), userRecord.getIsVolunteer());
    }

    public UserRecord toRecord(UserEntity userEntity) {
        if (userEntity == null) return null;
        UserRecord userRecord = new UserRecord(userEntity.getUserName(), userEntity.getChatId(), userEntity.getIsVolunteer());
        userRecord.setId(userEntity.getId());
        userRecord.setUserName(userEntity.getUserName());
        userRecord.setIsVolunteer(userEntity.getIsVolunteer());
        return userRecord;
    }
}

package ru.devpro.animalshelter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.devpro.animalshelter.core.entity.AnimalEntity;
import ru.devpro.animalshelter.core.exception.AnimalNotFoundException;
import ru.devpro.animalshelter.core.record.AnimalRecord;
import ru.devpro.animalshelter.core.record.MapperRecord;
import ru.devpro.animalshelter.core.repository.AnimalRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AnimalService {
    private final Logger logger = LoggerFactory.getLogger(AnimalService.class);
    private final AnimalRepository animalRepository;
    private final MapperRecord mapperRecord;

    public AnimalService(AnimalRepository animalRepository, MapperRecord mapperRecord) {
        this.animalRepository = animalRepository;
        this.mapperRecord = mapperRecord;

    }

    /**
     * Метод создает животное
     *
     * @param animalRecord - животное
     * @return возвращает созданое животное
     */
    public AnimalRecord createAnimal(AnimalRecord animalRecord) {
        logger.info("Вызов метода createAnimal");
        AnimalEntity animalEntity = mapperRecord.toEntity(animalRecord);
        return mapperRecord.toRecord(animalRepository.save(animalEntity));
    }

    /**
     * метод нахождения животного по id
     *
     * @param id - животного
     * @return возвращает найденное животное
     */
    public AnimalRecord findAnimalById(Long id) {
        logger.info("Метод поиска животного по id");
        return mapperRecord.toRecord(animalRepository.findById(id).orElseThrow(() -> {
                    logger.error("Не обнаруженно животное с id = {}", id);
                    return new AnimalNotFoundException(id);
                }));
    }

    /**
     * метод измкнения параметров животного
     *
     * @param id - id животного
     * @param animalRecord - животное
     * @return вощвращает животное
     */
    public AnimalRecord editAnimal(Long id, AnimalRecord animalRecord) {
        logger.info("Вызов метода редактирования editAnimal");
        AnimalEntity oldAnimal = animalRepository.findById(id).orElseThrow(() -> {
            logger.error("Не обнаруженно животное с id = {}", id);
            return new AnimalNotFoundException(id);
        });
        oldAnimal.setAnimalName(animalRecord.getAnimalName());
        oldAnimal.setAnimalType(animalRecord.getAnimalType());
        return mapperRecord.toRecord(animalRepository.save(oldAnimal));
    }

    /**
     * метод удалениия по id из БД
     *
     * @param id - животного
     * @return возвращает животное которое было удалено
     */
    public AnimalRecord deleteAnimal(Long id) {
        logger.info("Вызов метода deleteAnimal(Long id)");
        AnimalEntity animalEntity = animalRepository.findById(id).orElseThrow(() -> {
                    logger.error("Не обнаруженно животное с id = {}", id);
                    return new AnimalNotFoundException(id);
                });
        animalRepository.delete(animalEntity);
        return mapperRecord.toRecord(animalEntity);
    }

    /**
     * метод нахождения всех животных в БД
     *
     * @return возвращает всех животных
     */
    public Collection<AnimalRecord> getAllAnimals() {
        logger.info("Вызов метода getAllAnimals()");

        return animalRepository.findAll().stream().map(mapperRecord::toRecord).collect(Collectors.toList());
    }
}

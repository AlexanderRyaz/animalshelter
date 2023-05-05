package ru.devpro.animalshelter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.devpro.animalshelter.core.entity.AnimalEntity;
import ru.devpro.animalshelter.core.repository.AnimalRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class AnimalService {
    private final Logger logger = LoggerFactory.getLogger(AnimalService.class);
    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;

    }

    /**
     * Метод создает животное
     *
     * @param animalEntity - животное
     * @return возвращает созданое животное
     */
    public AnimalEntity createAnimal(AnimalEntity animalEntity) {
        logger.info("Вызов метода createAnimal");

        return animalRepository.save(animalEntity);
    }

    /**
     * метод нахождения животного по id
     *
     * @param id - животного
     * @return возвращает найденное животное
     */
    public Optional<AnimalEntity> findAnimalById(long id) {
        logger.info("Метод поиска животного по id");
        return animalRepository.findById(id);
    }

    /**
     * метод изменения параметров животного
     *
     * @param id - id животного
     *
     * @return вощвращает животное
     */
    public AnimalEntity editAnimal(long id, AnimalEntity animalEntity) {
        logger.info("Вызов метода редактирования editAnimal");
        Optional<AnimalEntity> byId = animalRepository.findById(id);
        if (byId.isPresent()) {
            return animalRepository.save(animalEntity);
        }
        return null;
    }

    /**
     * метод удалениия по id из БД
     *
     * @param id - животного
     */
    public void deleteAnimal(long id) {
        logger.info("Вызов метода deleteAnimal(long id)");

        animalRepository.deleteById(id);
    }

    /**
     * метод нахождения всех животных в БД
     *
     * @return возвращает всех животных
     */
    public Collection<AnimalEntity> getAllAnimals() {
        logger.info("Вызов метода getAllAnimals()");

        return animalRepository.findAll();
    }
}

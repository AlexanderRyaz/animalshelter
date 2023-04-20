package ru.devpro.animalshelter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.devpro.animalshelter.core.entity.ShelterEntity;
import ru.devpro.animalshelter.core.exception.AnimalNotFoundException;
import ru.devpro.animalshelter.core.repository.ShelterRepository;

import java.util.Collection;

@Service
public class ShelterService {

    private final ShelterRepository shelterRepository;

    private final Logger logger = LoggerFactory.getLogger(ShelterService.class);

    public ShelterService(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    public ShelterEntity createShelter(ShelterEntity shelterEntity) {
        logger.info("Вызов метода создания приюта");
        return shelterRepository.save(shelterEntity);
    }

    public ShelterEntity findShelterById(long id) {
        logger.info("Вызов метода поиска приюта по id");
        return shelterRepository.findById(id);
    }

    public ShelterEntity editShelter(long l, ShelterEntity shelterEntity) {
        logger.info("Вызов метода редактирования приюта");
        ShelterEntity byId = shelterRepository.findById(l);
        if (byId!=null) {
            return shelterRepository.save(shelterEntity);
        }
        throw new AnimalNotFoundException(l);
    }

    public void deleteShelter(long id) {
        logger.info("Вызов метода удаления приюта");
        shelterRepository.deleteById(id);
    }

    public Collection<ShelterEntity> getAllShelters() {
        logger.info("Вызов метода getAllShelters");
        return shelterRepository.findAll();
    }
}

package ru.devpro.animalshelter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.devpro.animalshelter.core.entity.ShelterEntity;
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
        if (shelterRepository.findById(shelterEntity.getId()).isPresent()) {
            return shelterRepository.save(shelterEntity);
        }
        return null;
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

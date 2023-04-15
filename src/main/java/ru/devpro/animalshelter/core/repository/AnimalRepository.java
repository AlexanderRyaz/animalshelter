package ru.devpro.animalshelter.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.devpro.animalshelter.core.entity.AnimalEntity;
import ru.devpro.animalshelter.core.entity.UserEntity;

@Repository
public interface AnimalRepository extends JpaRepository<AnimalEntity, Long> {
    AnimalEntity findById(long id);
}


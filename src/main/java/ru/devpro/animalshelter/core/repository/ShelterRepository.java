package ru.devpro.animalshelter.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.devpro.animalshelter.core.entity.ShelterEntity;

@Repository
public interface ShelterRepository extends JpaRepository<ShelterEntity, Long> {

    ShelterEntity findById(long id);
}

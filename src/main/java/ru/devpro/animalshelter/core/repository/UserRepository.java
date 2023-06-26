package ru.devpro.animalshelter.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.devpro.animalshelter.core.entity.UserEntity;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity getUserEntitiesByChatId(Long chatId);

    List<UserEntity> findAllByIsVolunteerIsTrue();

    UserEntity findByChatId(Long chatId);

}

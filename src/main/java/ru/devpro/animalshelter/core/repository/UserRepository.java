package ru.devpro.animalshelter.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.devpro.animalshelter.core.entity.UserEntity;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findById(long id);
//    Optional<UserEntity> findUserEntityChatId(Long chatId);
}

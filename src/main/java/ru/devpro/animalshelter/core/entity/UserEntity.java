package ru.devpro.animalshelter.core.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "is_volunteer")
    private Boolean isVolunteer;

    public UserEntity(Long chatId) {
        this.chatId = chatId;
    }

    public UserEntity(String userName, Long chatId, Boolean isVolunteer) {
        this.userName = userName;
        this.chatId = chatId;
        this.isVolunteer = isVolunteer;
    }

    public UserEntity() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(userName, that.userName) && Objects.equals(chatId, that.chatId) && Objects.equals(isVolunteer, that.isVolunteer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, chatId, isVolunteer);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", chatId=" + chatId +
                ", isVolunteer=" + isVolunteer +
                '}';
    }
}
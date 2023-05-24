package ru.devpro.animalshelter.core.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.devpro.animalshelter.core.model.AnimalType;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "animals")
public class AnimalEntity {

    /**
     * Генерация уникального id
     * Поле с именем
     * Поле тип животного: Кот, собака
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "animal_type")
    private AnimalType animalType;

    @Column(name = "animal_name")
    private String animalName;

    @OneToOne
    @JoinColumn(name = "Id")
    private UserEntity userEntity;

    public AnimalType getAnimalType() {
        return animalType;
    }

    public String getAnimalName() {
        return animalName;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }
}

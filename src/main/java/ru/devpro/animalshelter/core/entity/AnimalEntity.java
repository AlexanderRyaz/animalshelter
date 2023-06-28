package ru.devpro.animalshelter.core.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.devpro.animalshelter.core.model.AnimalType;

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

//    nullable = false, unique = true
    @Column(name = "animal_type")
    private AnimalType animalType;

    @Column(name = "animal_name")
    private String animalName;

    @Override
    public String toString() {
        return "AnimalEntity{" +
                "id=" + id +
                ", animalType=" + animalType +
                ", animalName='" + animalName + '\'' +
                '}';
    }

        public AnimalEntity(Long id, AnimalType animalType, String animalName) {
        this.id = id;
        this.animalType = animalType;
        this.animalName = animalName;
    }

    public AnimalEntity() {

    }
}

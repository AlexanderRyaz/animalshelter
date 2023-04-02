package ru.devpro.animalshelter.core.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "animal")
public class AnimalEntity {

    /**
     * Генерация уникального id
     * Поле с именем
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "animal_name")
    private String animalName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnimalEntity that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(animalName, that.animalName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, animalName);
    }

    @Override
    public String toString() {
        return "AnimalEntity{" +
                "id=" + id +
                ", animalName='" + animalName + '\'' +
                '}';
    }
}

package ru.devpro.animalshelter.core.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "shelters")
public class ShelterEntity {
    /**
     * Генерация уникального ID
     * Название приюта
     * Адресс приюта
     * Часы работы приюта
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @Column(name = "opening_hours")
    private String openingHours;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShelterEntity that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(address, that.address) && Objects.equals(openingHours, that.openingHours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, openingHours);
    }

    @Override
    public String toString() {
        return "ShelterEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", openingHours='" + openingHours + '\'' +
                '}';
    }
}

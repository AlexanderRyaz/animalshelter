package ru.devpro.animalshelter.core.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "photo")
public class PhotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "filePath")
    private String filePath;

    @Column(name = "fileSize")
    private long fileSize;

    @Column(name = "fileMediaType")
    private String fileMediaType;

    @Column(name = "fileData")
    private byte[] fileData;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhotoEntity that = (PhotoEntity) o;
        return fileSize == that.fileSize && Objects.equals(id, that.id) && Objects.equals(filePath, that.filePath) && Objects.equals(fileMediaType, that.fileMediaType) && Arrays.equals(fileData, that.fileData);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, filePath, fileSize, fileMediaType);
        result = 31 * result + Arrays.hashCode(fileData);
        return result;
    }

    @Override
    public String toString() {
        return "PhotoEntity{" +
                "id=" + id +
                ", filePath='" + filePath + '\'' +
                ", fileSize=" + fileSize +
                ", fileMediaType='" + fileMediaType + '\'' +
                ", fileData=" + Arrays.toString(fileData) +
                '}';
    }
}

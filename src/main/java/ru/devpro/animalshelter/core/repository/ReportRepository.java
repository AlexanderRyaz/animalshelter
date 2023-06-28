package ru.devpro.animalshelter.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.devpro.animalshelter.core.entity.ReportEntity;

@Repository
public interface ReportRepository extends JpaRepository<ReportEntity, Long> {

}

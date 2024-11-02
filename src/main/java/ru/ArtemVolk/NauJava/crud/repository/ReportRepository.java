package ru.ArtemVolk.NauJava.crud.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.ArtemVolk.NauJava.entity.Report;

import java.util.Optional;

@RepositoryRestResource(path = "report")
public interface ReportRepository extends CrudRepository<Report, Long> {
    Optional<Report> findById(Long reportId);
}

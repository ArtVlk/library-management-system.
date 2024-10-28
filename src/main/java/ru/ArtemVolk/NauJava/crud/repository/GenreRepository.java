package ru.ArtemVolk.NauJava.crud.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.ArtemVolk.NauJava.entity.Genre;

@RepositoryRestResource(path = "genres")
public interface GenreRepository extends CrudRepository<Genre, Long> {
}

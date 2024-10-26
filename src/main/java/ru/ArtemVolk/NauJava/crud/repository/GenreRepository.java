package ru.ArtemVolk.NauJava.crud.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ArtemVolk.NauJava.entity.Genre;

public interface GenreRepository extends CrudRepository<Genre, Long> {
}

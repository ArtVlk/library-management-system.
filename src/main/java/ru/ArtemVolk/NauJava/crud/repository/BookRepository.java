package ru.ArtemVolk.NauJava.crud.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.ArtemVolk.NauJava.entity.Book;

@RepositoryRestResource(path = "books")
public interface BookRepository extends CrudRepository<Book, Long> {
}
